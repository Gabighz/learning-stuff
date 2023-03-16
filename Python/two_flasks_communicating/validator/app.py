import flask

app = flask.Flask(__name__)

@app.route('/users/validation', methods = ['POST'])
def validator():
    user_data = flask.request.json

    if validate_payload(user_data):
        return user_data, 200
    else:
        return "Invalid user data", 400

def validate_payload(payload: dict) -> bool:
    name = payload['name']
    age = payload['age']

    if len(name) < 3 or len(name) > 30:
        return False
    
    if not name.isalpha():
        return False
    
    if not isinstance(age, int):
        return False
    
    if age < 18:
        return False
    
    return True

if __name__ == '__main__':
    app.run(host = 'validator', port = 5001, debug = True)
