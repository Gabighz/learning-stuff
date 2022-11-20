from math import exp

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

    # value of investment in dollars
    x = float(input("Principal: "))
    # define the interest rate (r)
    r = float(input("Interest rate expressed as a floating-point number: "))
    # duration (years)
    t = float(input("Period in years: "))
    # optional compounding period
    m = input("Compouding period (optional): ")

    print("Future value (discrete model) of x: %.2f" % future_discrete_value(x, r, t))
    print("Present value (discrete model) of x: %.2f" % present_discrete_value(x, r, t))
    print("Future value (continuous model) of x: %.2f" % future_continuous_value(x, r, t))
    print("Present values (continuous model) of x: %.2f" % present_continuous_value(x, r, t))
    if m:
        print("Future value (periodic compounding) of x: %.2f" % periodic_compounding(x, r, t, float(m)))