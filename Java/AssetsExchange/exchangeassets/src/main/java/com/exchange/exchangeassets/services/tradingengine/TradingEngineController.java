package com.exchange.exchangeassets.services.tradingengine;

import com.exchange.exchangeassets.orders.Order;
import com.exchange.exchangeassets.orders.OrderMatcher;
import com.exchange.exchangeassets.orders.OrderMatches;
import com.exchange.exchangeassets.orders.OrderStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class TradingEngineController {

    final OrderStore orderStore;

    @Autowired
    TradingEngineController(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    @GetMapping("/orders")
    Collection<Order> getOrdersReceived() {
        return orderStore.getOrders();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    OrderMatches submitOrder(@RequestBody Order newOrder) {
        OrderMatcher.matchOrders(orderStore, newOrder);

        if (newOrder.getRemainingContracts() > 0) {
            orderStore.getOrders().add(newOrder);
        }

        return newOrder.getMatches();
    }

}
