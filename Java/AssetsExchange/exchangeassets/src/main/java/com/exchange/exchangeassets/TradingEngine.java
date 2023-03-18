package com.exchange.exchangeassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingEngine {

    private final OrderStore orderStore;

    @Autowired
    TradingEngine(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    @GetMapping("/orders")
    OrderStore getOrdersReceived() {
        return orderStore;
    }

    @PostMapping("/orders")
    OrderMatches submitOrder(@RequestBody Order newOrder) {
        OrderMatcher.matchOrders(orderStore, newOrder);

        if (newOrder.getRemainingContracts() > 0) {
            orderStore.getOrders().add(newOrder);
        }

        return newOrder.getMatches();
    }

}
