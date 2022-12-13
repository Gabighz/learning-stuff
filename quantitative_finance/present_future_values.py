#!/usr/bin/python3
import decimal
from decimal import Decimal

decimal.getcontext().rounding = decimal.ROUND_HALF_EVEN # Banker's rounding
decimal.getcontext().prec = 28 # Precision to 28 decimal places

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
    return x * Decimal.exp(r * t)

def present_continuous_value(x, r, t):
    return x * Decimal.exp(-r * t)

def periodic_compounding(x, r, t, m):
    return (1 + r / m) ** (m * t) * x

if __name__ == '__main__':

    # NOTE: Decimals instead of floats here are highly desirable for their exact finite representations
    x = Decimal(input("Principal: "))
    r = Decimal(input("Annual interest rate expressed as a fixed-point number: "))
    t = Decimal(input("Period in years: "))
    m = input("Compouding period (optional): ")

    print(f"Future value (discrete model) of x: {future_discrete_value(x, r, t):.2f}")
    print(f"Present value (discrete model) of x: {present_discrete_value(x, r, t):.2f}")
    print(f"Future value (continuous model) of x: {future_continuous_value(x, r, t):.2f}")
    print(f"Present values (continuous model) of x: {present_continuous_value(x, r, t):.2f}")
    if m:
        print(f"Future value (periodic compounding) of x: {periodic_compounding(x, r, t, Decimal(m)):.2f}")