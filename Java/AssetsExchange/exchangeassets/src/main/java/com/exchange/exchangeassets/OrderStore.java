package com.exchange.exchangeassets;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

public interface OrderStore {
    Collection<Order> getOrders();
    void addOrder(Order order);
}

@Component
class DefaultOrderStore implements OrderStore {
    // NOTE: To be replaced with a data store, such as Redis. Only unfulfilled or partially filled Orders should be stored there.
    private final ConcurrentSkipListSet<Order> orders = new ConcurrentSkipListSet<>();

    @Override
    public Collection<Order> getOrders() {
        return orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }
}