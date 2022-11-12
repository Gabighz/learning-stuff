## Steps for dev without docker:
    virtualenv -p python3 small_multithreaded_server 
    cd small_multithreaded_server 
    source bin/activate
    pip install -r requirements.txt
    export FLASK_APP=app.py
    export FLASK_APP=development
    flask run
    manually test with curl http://127.0.0.1:5000/api/smart/800

## Steps for dev with docker:
    docker-compose -f docker-compose-dev.yml up --build 
    manually test with curl http://0.0.0.0:5001/api/smart/800

## Steps for prod:
    docker-compose up --build

#### Apache Bench could be used for simple load testing.
    ab -n 500 -c 50 http://0.0.0.0:5001/api/smart
