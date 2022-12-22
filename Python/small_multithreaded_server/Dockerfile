FROM python:3.11.0b3-alpine
WORKDIR 'app'
COPY . .
RUN pip install -r  requirements.txt
ENTRYPOINT ["./gunicorn-starter.sh"]