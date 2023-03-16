#!/bin/sh

if [ "$APP_NAME" = "needs_the_validator" ]; then
    PORT=5000
elif [ "$APP_NAME" = "validator" ]; then
    PORT=5001
else
    echo "Error: Unknown app name"
    exit 1
fi

exec gunicorn --bind 0.0.0.0:$PORT --workers 3 --threads 2 app:app