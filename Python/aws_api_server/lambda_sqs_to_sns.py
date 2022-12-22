import json, boto3, time

TOPIC_ARN = 'arn:aws:sns:us-east-1:028282298960:task9-uploads-notification-topic'
QUEUE_URL = 'https://sqs.us-east-1.amazonaws.com/028282298960/task9-uploads-notification-queue.fifo'

sns = boto3.client(
    'sns',
    region_name = 'us-east-1'
)

sqs = boto3.client(
    'sqs',
    region_name = 'us-east-1'
)

def lambda_handler(event, context):
    
    print(event)
    sqs_to_sns()
    
    return {
        'statusCode': 200,
        'body': json.dumps('SQS to SNS Lambda completed!')
    }

def sqs_to_sns():
    response = sqs.receive_message(
        QueueUrl = QUEUE_URL,
        WaitTimeSeconds = 2,
        MaxNumberOfMessages = 2
    )

    if 'Messages' in response:
        print('Message(s) received! Sending to SNS topic')
        messages = []
        for message in response['Messages']:
            messages.append(message['Body'])
            
            sqs.delete_message(
                QueueUrl = QUEUE_URL,
                ReceiptHandle = message['ReceiptHandle']
            )
        
        sns.publish(
                TopicArn = TOPIC_ARN,
                Message = '\n'.join(message for message in messages)
            )