from functools import wraps

def make_posh(func):
    ''' Pretty-print something '''

    @wraps(func)
    def wrapper():
        to_be_printed = func()
        width = len(to_be_printed) - 2

        sides = f"|{' ' * width}|"
        upper_part = f"+{'-' * width}+\n{sides}"
        lower_part = f"{sides}\n+{'=' * width}+"

        print(upper_part)
        print(to_be_printed)
        print(lower_part)
    
    # if we weren't using wraps, to not lose this metadata:
    #wrapper.__name__ = func.__name__
    #wrapper.__doc__ = func.__doc__

    return wrapper

@make_posh
def pfib():
    ''' Print out Fibonacci '''
    return ' Fibonacci '

pfib()

#-----------------------------------#

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

#-----------------------------------#

def bold(func):
    ''' Bold something '''

    @wraps(func)
    def wrapper():
        return f'<b>{str(func())}</b>'

    return wrapper

def italics(func):
    ''' Italize something '''

    @wraps(func)
    def wrapper():
        return f'<i>{str(func())}</i>'

    return wrapper

@bold
@italics
def printfib():
    '''return Fibonacci'''
    return 'Fibonacci'

print(printfib())