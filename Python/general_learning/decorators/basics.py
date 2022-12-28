def print_fib(a, *args):
    '''Using *args'''
    print(a)
    print(args)

print_fib(1, 1, 2, 3)

print_fib(1)

def print_fib(a, **kwargs):
    '''Using **kwargs'''
    print(a)
    print(kwargs)

print_fib(1, se=1, th=2, fo=3, fi=5)

print_fib(1)

def print_fib(*args, **kwargs):
    '''Using *args and **kwargs'''
    print(args)
    print(kwargs)

print_fib(1, 1, 2, 3)

print_fib(fi=1, se=1, th=2, fo=3)

print_fib(1, 1, 2, fo=3, fi=5)

print_fib()

def wrapper(*args, **kwargs):
    print('In wrapper ... unpacking args')
    print(*args)
    print_fib(*args, **kwargs)

wrapper(1, 1, th=2)

#-----------------------------------#

def my_decorator(func):
    '''Decorator function'''
    def wrapper(): 
        '''Return string F-I-B-O-N-A-C-C-I'''
        return 'F-I-B-O-N-A-C-C-I'
    return wrapper

@my_decorator
def pfib():
    '''Return Fibonacci'''
    return 'Fibonacci'


print(pfib())