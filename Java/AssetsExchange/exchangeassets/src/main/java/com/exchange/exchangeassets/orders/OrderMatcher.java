package com.exchange.exchangeassets.orders;

public class OrderMatcher {

    public static void matchOrders(OrderStore currentOrders, Order newOrder) {

        currentOrders.getOrders().stream()
                .filter(order -> order.canBeMatchedWith(newOrder))
                .forEach(order -> modifyOrders(order, newOrder));

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