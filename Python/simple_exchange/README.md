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

(WIP) For the sake of learning variety, converted this into a Spring Boot app here: https://github.com/Gabighz/learning-stuff/tree/master/Java/SimpleExchange 
