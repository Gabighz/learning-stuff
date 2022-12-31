from functools import update_wrapper
from datetime import datetime, timezone

class MyClass:

    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __call__(self, fn):
        def inner(*args, **kwargs):
            print(f'MyClass instance called: a={self.a}, b={self.b}')
            return fn(*args, **kwargs)
        return inner

@MyClass(10, 20)
def my_func(s):
    print(f'Hello {s}')

my_func('world')

#---------------------------#

class Count:
    def __init__(self, func):
        update_wrapper(self, func)
        self.func = func
        self.cnt = 0

    def __call__(self, *args, **kwargs):
        self.cnt += 1
        print(f'Current count: {self.cnt}')
        result = self.func(*args, **kwargs)
        return result

@Count
def fib(n):
    ''' return the Fibonacci sequence '''
    if n < 2:
        return n
    return fib(n - 1) + fib(n - 2)

fib(10)

#---------------------------#

def debug_info(cls):

    def info(self):
        results = []
        results.append(f'time: {datetime.now(timezone.utc)}')
        results.append(f'class: {self.__class__.__name__}')
        results.append(f'id: {hex(id(self))}')
        
        if vars(self):
            results.extend(f'{k}: {v}' for k, v in vars(self).items())
        
        return results
    
    cls.debug = info
    
    return cls

@debug_info
class Person:
    def __init__(self, name, birth_year):
        self.name = name
        self.birth_year = birth_year
        
    def say_hi():
        return 'Hello there!'

p1 = Person('John', 1939)
print(p1.debug())