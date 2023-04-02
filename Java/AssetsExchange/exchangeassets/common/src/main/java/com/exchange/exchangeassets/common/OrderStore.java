package com.exchange.exchangeassets.common;

import java.util.Collection;

public interface OrderStore {
    Collection<Order> getOrders();
    void addOrder(Order order);
    void deleteOrder(String id);
}