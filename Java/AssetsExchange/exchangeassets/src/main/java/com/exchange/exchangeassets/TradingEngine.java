package com.exchange.exchangeassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TradingEngine {

    final OrderStore orderStore;

    @Autowired
    TradingEngine(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    @GetMapping("/orders")
    Collection<Order> getOrdersReceived() {
        return orderStore.getOrders();
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
