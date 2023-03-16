FROM python:3.11.0b3-alpine

WORKDIR /app

COPY needs_the_validator/app.py shared/requirements.txt shared/gunicorn-starter.sh /app/

RUN pip install --no-cache-dir -r requirements.txt \
    && chmod +x gunicorn-starter.sh

ENTRYPOINT ["./gunicorn-starter.sh"]