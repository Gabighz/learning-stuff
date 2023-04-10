package com.exchange.exchangeassets.common;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class DefaultOrderStore implements OrderStore {
    // NOTE: To be replaced with a data store, such as Redis. Only unfulfilled or partially filled Orders should be stored here.
    private final ConcurrentSkipListSet<Order> orders = new ConcurrentSkipListSet<>();

    @Override
    public Collection<Order> getOrders() {
        return orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void deleteOrder(String id) {
        orders.removeIf(order -> Objects.equals(order.getId(), id));
    }
}
