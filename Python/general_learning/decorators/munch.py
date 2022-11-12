from functools import wraps

def munch(start, end):
    ''' Munches away at strings. I.e. replaces characters with an 'x' given a certain range.
        Works with functions that have an arbitrary number of arguments. '''

    def do_the_munch(func):
        
        @wraps(func)
        def wrapper(*args, **kwargs):
            to_be_munched = func(*args, **kwargs)
            munches = 'x' * (end - start)

            return f'{to_be_munched[:start] + munches + to_be_munched[end:]}'

        return wrapper

    return do_the_munch

@munch(1, 6)
def fib():
    return 'Fibonacci'

print(fib())