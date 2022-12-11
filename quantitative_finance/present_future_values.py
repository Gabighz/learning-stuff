from math import exp

'''
Keep in mind that the gross return of an investment at a periodically compounded interest rate equals the
future discrete value of an initial investment.

To calculate equivalent interest rates we can equate the gross or net returns,
which is the same as equating the future value of 1 dollar.
'''

def future_discrete_value(x, r, n):
    return x * (1 + r) ** n

def present_discrete_value(x, r, n):
    return x * (1 + r) ** -n

def future_continuous_value(x, r, t):
    return x * exp(r * t)

def present_continuous_value(x, r, t):
    return x * exp(-r * t)

def periodic_compounding(x, r, t, m):
    return (1 + r / m) ** (m * t) * x

if __name__ == '__main__':

    x = float(input("Principal: "))
    r = float(input("Annual interest rate expressed as a floating-point number: "))
    t = float(input("Period in years: "))
    m = input("Compouding period (optional): ")

    print("Future value (discrete model) of x: %.2f" % future_discrete_value(x, r, t))
    print("Present value (discrete model) of x: %.2f" % present_discrete_value(x, r, t))
    print("Future value (continuous model) of x: %.2f" % future_continuous_value(x, r, t))
    print("Present values (continuous model) of x: %.2f" % present_continuous_value(x, r, t))
    if m:
        print("Future value (periodic compounding) of x: %.2f" % periodic_compounding(x, r, t, float(m)))