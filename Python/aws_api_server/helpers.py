import boto3, time
from . import constants
from werkzeug.utils import secure_filename

s3 = boto3.client(
    "s3",
    aws_access_key_id = constants.ACCESS_KEY,
    aws_secret_access_key = constants.SECRET_KEY
)

sns = boto3.client(
    'sns',
    aws_access_key_id = constants.ACCESS_KEY,
    aws_secret_access_key = constants.SECRET_KEY,
    region_name = 'us-east-1'
)

sqs = boto3.client(
    'sqs',
    aws_access_key_id = constants.ACCESS_KEY,
    aws_secret_access_key = constants.SECRET_KEY,
    region_name = 'us-east-1'
)

lambda_client = boto3.client(
    'lambda',
    aws_access_key_id = constants.ACCESS_KEY,
    aws_secret_access_key = constants.SECRET_KEY,
    region_name = 'us-east-1'
)

def upload_file_to_s3(file, acl="public-read"):
    filename = secure_filename(file.filename)
    try:
        s3.upload_fileobj(
            file,
            constants.AWS_BUCKET_NAME,
            file.filename,
            ExtraArgs={
                "ACL": acl,
                "ContentType": file.content_type
            }
        )

    except Exception as e:
        print("Something Happened: ", e)
        return e

    # after upload file to s3 bucket, return filename of the uploaded file
    return f'uploaded {file.filename} to S3'

def delete_file_from_s3(filename):

    filename = secure_filename(filename)
    try:
        s3.delete_object(Bucket = constants.AWS_BUCKET_NAME, Key = filename)
    except Exception as e:
        print("Something Happened: ", e)
        return e

    # after upload file to s3 bucket, return filename of the uploaded file
    return f'deleted {filename} from S3'

def sqs_to_sns():
    while True:
        print('Polling SQS for messages')
        response = sqs.receive_message(
            QueueUrl = constants.QUEUE_URL,
            WaitTimeSeconds = 2,
            MaxNumberOfMessages = 2
        )

        if 'Messages' in response:
            print('Message(s) received! Sending to SNS topic')
            messages = []
            for message in response['Messages']:
                messages.append(message['Body'])
                
                sqs.delete_message(
                    QueueUrl = constants.QUEUE_URL,
                    ReceiptHandle = message['ReceiptHandle']
                )
            
            sns.publish(
                    TopicArn = constants.TOPIC_ARN,
                    Message = '\n'.join(message for message in messages)
                )