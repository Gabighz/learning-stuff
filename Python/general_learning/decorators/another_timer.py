def timer(fn):
    from time import perf_counter
    from functools import wraps

    @wraps(fn)
    def inner(*args, **kwargs):
        start = perf_counter()
        result = fn(*args, **kwargs)
        end = perf_counter()
        elapsed = end - start

        args_ = [str(a) for a in args]
        kwargs_ = [f'{k}={v}' for (k, v) in kwargs.items()]
        all_args = ','.join(args_ + kwargs_)
        print(f'{fn.__name__}({all_args}) took {elapsed:.6f}s to run.')

        return result

    return inner

@timer
def fib(n):
    '''Return the nth value from the Fiboanacci sequence'''
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

fib(20)