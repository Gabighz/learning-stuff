package com.exchange.exchangeassets.orders;

import java.util.Collection;

public interface OrderStore {
    Collection<Order> getOrders();
    void addOrder(Order order);
}