package com.exchange.exchangeassets.common;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class OrderMatcher {

    public static ArrayList<Order> matchOrders(OrderStore currentOrders, Order newOrder) {

        return currentOrders.getOrders().stream()
                .filter(order -> order.canBeMatchedWith(newOrder))
                .peek(order -> modifyOrders(order, newOrder))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public static void modifyOrders(Order order, Order newOrder){
        int initialOrderRemaining = order.getRemainingContracts();
        int initialNewOrderRemaining = newOrder.getRemainingContracts();

        order.updateRemainingContracts(Math.max(0, initialOrderRemaining - initialNewOrderRemaining));
        newOrder.updateRemainingContracts(Math.max(0, initialNewOrderRemaining - initialOrderRemaining));

        order.updateStatus();
        newOrder.updateStatus();

        order.updateMatches(newOrder);
        newOrder.updateMatches(order);

    }

}