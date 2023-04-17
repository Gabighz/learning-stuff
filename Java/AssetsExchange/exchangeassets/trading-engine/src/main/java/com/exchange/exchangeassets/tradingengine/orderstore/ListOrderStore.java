package com.exchange.exchangeassets.tradingengine.orderstore;

import com.exchange.exchangeassets.common.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class ListOrderStore implements OrderStore {
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
        orders.removeIf(order -> Objects.equals(String.valueOf(order.getId()), id));
    }
}
