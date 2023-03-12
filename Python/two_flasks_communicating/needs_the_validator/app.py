import flask, requests

app = flask.Flask(__name__)

VALIDATOR_URL = 'http://localhost:5001/users/validation'

@app.route('/users', methods = ['POST'])
def view_or_some_service():

    user_data = flask.request.json
    try:
        res = requests.post(VALIDATOR_URL, json = user_data)
        if res.status_code == 404:
            return "404 Not Found"
        elif res.status_code == 204:
            return "204 No Content"
    except Exception as e:
        return e 

    app.logger.info(res.text)
    dictFromServer = res.json()
    return dictFromServer

if __name__ == '__main__':
    app.run(debug = True)
