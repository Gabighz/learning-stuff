# Order Matching System / Assets Exchange

### Work In Progress

Intended to simulate a very simple assets exchange platform, where clients are able to make Buy or Sell orders and the app would match these orders. Made with a microservices architecture in mind 

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

Specifically, a REST API server that should be able to CRUD orders. Each such operation will run in its own microservice.

To support GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS

Technologies used / to be used

- **For the app itself:** Java 19, Spring, postgresql, and Kafka as a queue to address possible congestion.
- **For the infrastructure:** should use Docker, docker-compose, Kubernetes, Terraform for AWS, Travis or Jenkins.
