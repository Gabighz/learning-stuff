# REST API Server for AWS

A Flask-based REST API server that interacts with AWS services, including EC2, S3, RDS, SQS, SNS, and Lambda. This server allows users to upload, download, and manage images, as well as subscribe to email notifications for image-related events.

## Prerequisites

- Python 3.6+
- Flask
- PostgreSQL
- AWS CLI configured with appropriate credentials
- AWS SAM CLI

## Deployment
### Deploying to an EC2 instance

Package this project then upload it to an S3 bucket with, for example:

    tar --exclude-vcs -czvf web_app.tar.gz .

Deploying to an EC2 instance

    # On your EC2 instance
    # Download the packaged web app from your S3 bucket
    aws s3 cp s3://your-web-app-bucket/web_app.tar.gz .

    # Extract the package
    tar -xf web_app.tar.gz

    # Install virtualenv and create a virtual environment
    pip3 install virtualenv
    virtualenv -p python3 venv
    source venv/bin/activate

    # Install the required packages
    pip3 install -r requirements.txt

    # Set up the Flask app as a systemd service
    sudo cp flaskapp.service /etc/systemd/system/

    # Install and configure Nginx
    sudo amazon-linux-extras install nginx1
    sudo cp app.conf /etc/nginx/conf.d/

    # Start and enable the Flask app service
    sudo systemctl start flaskapp
    sudo systemctl enable flaskapp

    # Start and enable Nginx
    sudo systemctl start nginx
    sudo systemctl enable nginx

### Deploying the SQS-to-SNS listener Lambda
Deploy the Lambda function using the AWS SAM CLI:

    sam deploy --guided -t sam_template.yml

## Usage
The following API endpoints are available:

- /api/ec2-metadata: Returns the EC2 instance's region and availability zone.
- /images/upload: Uploads an image to S3 and stores its metadata in RDS.
- /images/delete: Deletes an image from S3 and removes its metadata from RDS.
- /images/get-metadata: Retrieves metadata for all images stored in RDS.
- /images/download: Downloads an image from S3.
- /images/random-metadata: Retrieves metadata for a randomly-selected image from RDS.
- /notifications/subscriptions: Subscribes or unsubscribes an email address to/from SNS notifications.
- /notifications/send: Invokes a Lambda function that processes SQS messages and sends them to an SNS topic.
