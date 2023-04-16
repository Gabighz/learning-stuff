package com.exchange.exchangeassets.tradingengine.orderstore;

import com.exchange.exchangeassets.common.Order;

import java.util.Collection;

public interface OrderStore {
    Collection<Order> getOrders();
    void addOrder(Order order);
    void deleteOrder(String id);
}