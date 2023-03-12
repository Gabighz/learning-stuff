import flask, json, requests

app = flask.Flask(__name__)

@app.route('/users/validation', methods = ['POST'])
def validator():
    user_data = flask.request.json
    is_data_ok = validate_payload(user_data)
    return user_data

def validate_payload(payload: dict) -> bool:
    print(payload.items())

if __name__ == '__main__':
    app.run(port = 5001, debug = True)
    
