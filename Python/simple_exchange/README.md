# Order Matching System / Assets Exchange

Intended to simulate a very simple assets exchange platform, where clients are able to make Buy or Sell orders and the program would match these orders.

As an example, this is how a sell order would be represented:

```
{
    "order_id": "sell_1",
    "type": "sell",
    "currency": "USD",
    "limit_price": "10",
    "num_contracts": "2",
    "remanining_contracts": "1",
    "matches": [
        {"order_id": "buy_1", "contracts": "1"}
    ]
}
```

To be converted into a REST API server with Flask. Should be able to CRUD orders and run as microservices (or just as a containerized app, not sure yet).

To support GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS