import importlib
Order = importlib.import_module('order').Order
#from .order import Order

orders = dict()

def get_order(order_id: str) -> Order:
    return orders.get(order_id)

def new_order(type: str, num_contracts: int, limit_price: int, currency: str) -> str:
    new_order = Order(type, num_contracts, limit_price, currency)
    orders[new_order.id] = new_order
    order_matching(new_order)
    return new_order.id

def new_buy(num_contracts: int, limit_price: int, currency: str) -> str:
    return new_order('buy', num_contracts, limit_price, currency)

def new_sell(num_contracts: int, limit_price: int, currency: str) -> str:
    return new_order('sell', num_contracts, limit_price, currency)

def order_matching(new_order: Order) -> None:
    
    for order in orders.values():
        if order.type != new_order.type \
            and order.currency == new_order.currency \
            and are_prices_matching(order, new_order) \
            and order.remaining_contracts >= new_order.remaining_contracts:
                modify_orders(order, new_order)

def are_prices_matching(order: Order, new_order: Order) -> bool:
    if order.type == 'buy':
        return order.limit_price >= new_order.limit_price
    else:
        return order.limit_price <= new_order.limit_price
    
def modify_orders(order: Order, new_order: Order) -> None:
    initial_order_remaining = order.remaining_contracts
    initial_new_order_remaining = new_order.remaining_contracts

    order.remaining_contracts = max(0, initial_order_remaining - initial_new_order_remaining)
    new_order.remaining_contracts = max(0, initial_new_order_remaining - initial_order_remaining)

    if order.remaining_contracts == 0:
        order.status = 'fulfilled'
    elif order.remaining_contracts < order.num_contracts:
        order.status = 'partially_filled'

    if new_order.remaining_contracts == 0:
        new_order.status = 'fulfilled'
    elif new_order.remaining_contracts < order.num_contracts:
        new_order.status = 'partially_filled'

    order.matches.append({'id': new_order.id,
                          'numContracts': new_order.num_contracts,
                          'execution_price': (order.limit_price + new_order.limit_price) // 2})
    
    new_order.matches.append({'id': order.id,
                            'numContracts': order.num_contracts,
                            'execution_price': (order.limit_price + new_order.limit_price) // 2})

if __name__ == '__main__':
    buy_order_id = new_buy(100, 98, "USD")
    sell_order_id = new_sell(10, 96, "USD")
    print(Order.count)
    for order in orders.values():
        print(order.__dict__)