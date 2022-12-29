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

def memoize(fn):
    'An alternative to functools cache'
    cache = dict()

    def inner(n):
        if n not in cache:
            cache[n] = fn(n)
        return cache[n]

    return inner

def recursive_fib(n):
    if n < 2:
        return n
    return recursive_fib(n - 1) + recursive_fib(n - 2)

@timer
def fib(n):
    return recursive_fib(n)

@memoize
@timer
def memoized_fib(n):
    return recursive_fib(n)

fib(20)
memoized_fib(20)

@timer
def iterative_fib(n):
    fib_1 = 1
    fib_2 = 1

    for _ in range(3, n + 1):
        fib_1, fib_2 = fib_2, fib_1 + fib_2

    return fib_2

iterative_fib(20)

@timer
def fib_reduce(n):
    from functools import reduce

    initial = (0, 1)
    calculate_to = range(n)
    fib_n = reduce(lambda prev, _: (prev[1], prev[0] + prev[1]), calculate_to, initial)
    return fib_n[0]

fib_reduce(20)