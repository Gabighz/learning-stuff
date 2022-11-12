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