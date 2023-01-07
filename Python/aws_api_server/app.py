#!/usr/bin/env python3
import flask, random, time, threading
from . import constants
from flask import request, Response, jsonify
from ec2_metadata import ec2_metadata
from flask_sqlalchemy import SQLAlchemy
from .helpers import (
    s3, sns, sqs, lambda_client,
    upload_file_to_s3, delete_file_from_s3, sqs_to_sns,
)
import datetime

sqs_listener_thread = threading.Thread(target = sqs_to_sns, name = "SQS Listener", daemon = True)
sqs_listener_thread.start()

app = flask.Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = constants.CONF
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.secret_key = 'secret'
db = SQLAlchemy(app)

query = '''create table if not exists image(
        id               serial PRIMARY KEY,
        name             VARCHAR(20),
        image_size       BIGINT,
        file_extension   VARCHAR(40) ,
        updated_at       TIMESTAMP       DEFAULT NOW()
    );'''

try:
    db.engine.execute(query)
except:
    # Useful if we just want to debug the SNS/SQS functionality
    print('!!!!\nNo DB connection.\n!!!!')

# Model Class for Postgres integration
class Image(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(200), nullable=False)
    image_size = db.Column(db.String(200), nullable=False)
    file_extension = db.Column(db.String(4))
    updated_at = db.Column(db.Date, nullable=False)

    def __init__(self, name, image_size, file_extension, updated_at):
        self.name = name
        self.image_size = image_size
        self.file_extension = file_extension
        self.updated_at = updated_at

@app.route('/api/ec2-metadata')
def get_ec2_metadata():
    return f'{ec2_metadata.region}, {ec2_metadata.availability_zone}'

@app.route('/images/upload', methods=['POST'])
def image_upload():
    file_name = request.files['file'].filename
    name, file_extension = file_name.split('.')
    image_size = request.content_length
    updated_at = datetime.datetime.today()
    
    entry = Image(name, image_size, file_extension, updated_at)
    try:
        db.session.add(entry)
        db.session.commit()
        print(upload_file_to_s3(request.files['file']))
    except:
        pass

    sqs_response = sqs.send_message(
        QueueUrl = constants.QUEUE_URL,
        MessageBody = f'An image was uploaded \n Details: {file_name, image_size} bytes \n Can be downloaded at /images/download',
        # Below parameters are for a FIFO queue
        MessageGroupId = 'flask_app_for_learning',
        MessageDeduplicationId = str(time.time())
    )
    
    return jsonify(f'metadata sent to RDS and the image uploaded to S3; message sent to SQS; SQS response: {sqs_response}')

@app.route('/images/delete', methods=['POST'])
def images_delete():
    request_data = request.get_json()
    full_name = request_data['name']
    name_without_extension = full_name.split('.')[0]

    db.session.query(Image).filter(Image.name == name_without_extension).delete()
    db.session.commit()

    return delete_file_from_s3(request_data['name']) + ' and its metadata from RDS'

@app.route('/images/get-metadata', methods=['GET'])
def images_get_metadata():
    images = Image.query.all()
    metadata = []

    for image in images:
        metadata.append({'id': image.id, 'name': image.name, 'size': image.image_size, 'extension': image.file_extension, 
                    'updated_at': image.updated_at})

    return jsonify(metadata)

@app.route('/images/download', methods=['GET'])
def images_download():
    image_name = request.get_json()['name']
    file = s3.get_object(Bucket = constants.AWS_BUCKET_NAME, Key = image_name)

    return Response(
        file['Body'].read(),
        mimetype='image/jpeg',
        headers={"Content-Disposition": f"attachment;filename={image_name}"}
    )

@app.route('/images/random-metadata', methods=['GET'])
def images_get_random_metadata():
    image = random.choice(Image.query.all())
    return jsonify({'id': image.id, 'name': image.name, 'size': image.image_size, 'extension': image.file_extension, 
                    'updated_at': image.updated_at})

@app.route('/notifications/subscriptions', methods = ['POST', 'DELETE'])
def sns_subscriptions():
    email_endpoint = request.get_json()['email']
    
    if request.method == 'POST':
        response = sns.subscribe(
            TopicArn = constants.TOPIC_ARN,
            Protocol = 'email',
            Endpoint = email_endpoint,
        )

        return jsonify(response)

    if request.method == 'DELETE':
        arn_to_unsub = ''

        current_subs = sns.list_subscriptions_by_topic(
            TopicArn = constants.TOPIC_ARN
        )['Subscriptions']

        for sub in current_subs:
            if sub['Endpoint'] == email_endpoint:
                arn_to_unsub = sub['SubscriptionArn']

        response = sns.unsubscribe(
            SubscriptionArn = arn_to_unsub
        )

        return jsonify(response)

@app.route('/notifications/send')
def lambdaized_sqs_to_sns():
    ''' We invoke a Lambda that contains a slightly modified verion of the
        sqs_to_sns function from the helpers module. The slight modification is that the Lambda
        has no 'while True' loop. '''

    response = lambda_client.invoke(
        FunctionName = constants.LAMBDA_ARN,
        Payload = '{ "detail-type": "invocation from web app" }'
    )['Payload'].read().decode('utf-8')

    return jsonify(response)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)
