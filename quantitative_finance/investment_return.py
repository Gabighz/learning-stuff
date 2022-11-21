#!/usr/bin/python3

def get_data_from_broker():
    NotImplemented

def net_return(initial_investment: float, current_value: float):
    return (current_value - initial_investment) / initial_investment

if __name__ == '__main__':

    initial_investment = float(input("Initial investment: "))
    current_value = float(input("Current value of investment: "))
    as_percentage = 100 * net_return(initial_investment, current_value)

    print(f'Net return: {as_percentage:.2f}%')