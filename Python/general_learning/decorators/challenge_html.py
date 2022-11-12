from functools import wraps

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