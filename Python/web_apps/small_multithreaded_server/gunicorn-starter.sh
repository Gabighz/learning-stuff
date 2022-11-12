#!/bin/sh
gunicorn -w 12 -t 10 -b 0.0.0.0:5001 app:app