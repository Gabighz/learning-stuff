import flask, requests, json
from concurrent.futures import ThreadPoolExecutor

app = flask.Flask(__name__)

VALIDATOR_URL = 'http://validator:5001/users/validation'

@app.route('/users', methods = ['POST'])
def needs_the_validator():

    users_data = flask.request.json['users']
    try:
        with ThreadPoolExecutor() as executor:
            from_validator = executor.map(request_validate_user, users_data)

            # Retaining from_validator as an Iterator and using a generator expression here
            # in case the data received is quite large.
            validated_data = (data for data in from_validator if not isinstance(data, str))

    except Exception as e:
        app.logger.exception(e)
        return str(e)

    # A generator function to stream JSON data
    def generate_json():
        yield '['
        first = True
        for data in validated_data:
            if not first:
                yield ','
            else:
                first = False
            yield json.dumps(data)
        yield ']'

    # A streaming JSON response using the generator function.
    # NOTE: Not sure if this whole approach would be more memory efficient.
    # Hoping that the stored data from from_validator and validated_data would be released after every yield in both.
    response = flask.Response(generate_json(), content_type='application/json')

    return response

def request_validate_user(user_data):
    res = requests.post(VALIDATOR_URL, json=user_data)

    if res.status_code == 404:
        return "404 Not Found"
    elif res.status_code == 200:
        return res.json()
    elif res.status_code == 204:
        return "204 No Content"
    else:
        return f"Error: {res.status_code}"
    
if __name__ == '__main__':
    app.run(port = 5000, debug = True)
