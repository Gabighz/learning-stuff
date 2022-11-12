#!/usr/bin/env python3
import threading, queue, sys, requests, time, flask

app = flask.Flask(__name__)

WORK_API_ADDRESS = 'https://SOME-URL.com/api/work'
UNSUCCESSFUL_ERR = 'UNSUCCESSFUL'
TIMEOUT_ERR = 'TIMEOUT'

@app.get('/api/smart')
def runner_default_timeout():
    # Run with a timeout of 800ms
    return runner(800)

@app.get('/api/smart/<timeout>')
def runner(timeout):
    timeout = int(timeout)
    results = queue.Queue()
    counter = 0

    rest_started_already = False
    first_request = get_work(results)
    second_request = get_work(results)
    third_request = get_work(results)

    first_request.start()

    start, counter = time.perf_counter_ns(), 0
    while first_request.is_alive() or first_request.response == UNSUCCESSFUL_ERR:
        counter = (time.perf_counter_ns() - start) // 1_000_000

        if (counter > 300 or first_request.response == UNSUCCESSFUL_ERR) and not rest_started_already:
            rest_started_already = True
            second_request.start()
            third_request.start()

        if counter > timeout:
            if results:
                break
            else:
                return TIMEOUT_ERR
    
    if app.debug:
        print(first_request.is_alive(), first_request.response)
        print(second_request.is_alive(), second_request.response)
        print(third_request.is_alive(), third_request.response)
        for thread in threading.enumerate():
            print(thread.name)

    remaining_timeout_in_seconds = (timeout - counter) / 1000
    
    try:
        return results.get(block=True, timeout=remaining_timeout_in_seconds)
    except:
        if app.debug:
            print(list(results.queue))
        return TIMEOUT_ERR

class get_work(threading.Thread):
    def __init__(self, results):
        threading.Thread.__init__(self)
        self.response = None
        self.results = results
    def run(self):
        try:
            # Having this timeout here will also ensure that a thread won't live
            # longer than a second. Using events/semaphores/flags/futures etc. seemed
            # not suitable for this task to me.
            response = requests.get(WORK_API_ADDRESS, timeout=1)
            self.response = response.json()
            self.results.put(self.response)
        except:
            self.response = UNSUCCESSFUL_ERR