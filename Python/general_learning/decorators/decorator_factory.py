# While a decorator is equivalent to `my_func = dec(my_func)`,
# a decorator factory is equivalent to `my_func = dec_factory(a, b)(my_func)`

from functools import wraps
from time import perf_counter
from another_timer import recursive_fib

def dec_factory(a, b):
    print('running dec factory')

    def dec(fn):
        print('running dec')

        def inner(*args, **kwargs):
            print('running inner')
            print(f'a={a}, b={b}')
            return fn(*args, **kwargs)

        return inner

    return dec

dec = dec_factory(10, 20)

@dec
def my_func():
    print('running my func')

my_func()

@dec_factory(100, 200)
def my_func():
    print('running my func')

my_func()

my_func = dec_factory(150, 250)(my_func)
my_func()

#---------------------------#

def timed(num_reps = 1):

    def decorator(fn):

        @wraps(fn)
        def inner(*args, **kwargs):
            total_elapsed = 0

            for _ in range(num_reps):
                start = perf_counter()
                result = fn(*args, **kwargs)
                end = perf_counter()
                total_elapsed += (end - start)

            avg_elapsed = total_elapsed / num_reps
            print(f'Avg Run time: {avg_elapsed:.6f}s ({num_reps} reps)')

            return result

        return inner

    return decorator

@timed(10)
def fib(n):
    return recursive_fib(n)

fib(10)