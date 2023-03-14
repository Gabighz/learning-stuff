Package this project:

    tar --exclude='.git*' -czvf web_app.tar.gz .

To consume it on e.g. an EC2 instance:

    aws s3 cp s3://gabriel-ghiuzan-web-app-bucket/web_app.tar.gz .
    tar -xf web_app.tar.gz
    pip3 install virtualenv
    virtualenv -p python3 venv
    source venv/bin/activate
    pip3 install -r requirements.txt
    sudo cp flaskapp.service /etc/systemd/system/
    sudo amazon-linux-extras install nginx1
    sudo cp app.conf /etc/nginx/conf.d/
    sudo systemctl start flaskapp
    sudo systemctl enable flaskapp
    sudo systemctl start nginx
    sudo systemctl enable nginx

To deploy the SQS-to-SNS listener Lambda:

    sam deploy --guided -t sam_template.yml

TO BE IMPROVED
