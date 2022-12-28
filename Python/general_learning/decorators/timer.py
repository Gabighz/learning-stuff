from time import perf_counter
from functools import wraps, cache

def timer(func):

    @wraps(func)
    def wrapper(*args, **kwargs):
        start = perf_counter()
        result = func(*args, **kwargs)
        end = perf_counter()

        duration = end - start
        arg = str(*args)

        print(f'{func.__name__}({arg}) = {result} -> {duration:.8f}s')
        return result

    return wrapper

@cache
@timer # we want to time only the function itself; if we were to use @cache first, we'd time cache as well
def fib(n):
    '''Return the nth value from the Fiboanacci sequence'''
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

fib(20)
