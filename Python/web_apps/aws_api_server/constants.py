import os
from dotenv import load_dotenv

load_dotenv()

ACCESS_KEY = os.getenv('AWS_ACCESS_KEY')
SECRET_KEY = os.getenv('AWS_SECRET_ACCESS_KEY')
AWS_BUCKET_NAME = os.getenv("AWS_BUCKET_NAME")
TOPIC_ARN = os.getenv("TOPIC_ARN")
LAMBDA_ARN = os.getenv("LAMBDA_ARN")
QUEUE_URL = os.getenv("QUEUE_URL")
PG_USER = os.getenv('PG_USERNAME')
PG_PASSWORD = os.getenv('PG_PASSWORD')
PG_HOST = os.getenv('PG_HOST')
PG_PORT = os.getenv('PG_PORT')
PG_DATABASE = os.getenv('PG_DATABASE')

CONF = f"postgresql://{PG_USER}:{PG_PASSWORD}@{PG_HOST}:{PG_PORT}/{PG_DATABASE}"