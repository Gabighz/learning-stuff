package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.Order;
import com.exchange.exchangeassets.common.OrderMatcher;
import com.exchange.exchangeassets.common.OrderStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    OrderSubmissionResponse submitOrder(@RequestBody Order newOrder) {
        ArrayList<Order> matchedWithOrders = OrderMatcher.matchOrders(orderStore, newOrder);

        if (newOrder.isNotFulfilled()) {
            orderStore.getOrders().add(newOrder);
        }

        if (newOrder.isFulfilled() || matchedWithOrders.stream().anyMatch(Order::isFulfilled)) {
            // TODO after implementing TransactionHistory
            System.out.println("Sending fulfilled orders to TransactionHistory service");
        }

        return new OrderSubmissionResponse(newOrder.getId(), newOrder.getMatches());
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    void cancelOrder(@PathVariable String id) {
        orderStore.deleteOrder(id);
    }

}
