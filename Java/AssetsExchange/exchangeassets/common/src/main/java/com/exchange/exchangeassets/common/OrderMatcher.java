package com.exchange.exchangeassets.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.util.Pair;

public class OrderMatcher {

    public static MatchResult matchOrders(OrderStore currentOrders, Order newOrder) {
        List<Pair<Integer, Integer>> filledContracts = new ArrayList<>();

        List<Order> matchedOrders = currentOrders.getOrders().stream()
                .filter(order -> order.canBeMatchedWith(newOrder))
                .peek(order -> filledContracts.add(modifyOrders(order, newOrder)))
                .collect(Collectors.toList());

        return new MatchResult(matchedOrders, filledContracts);
    }

    public static Pair<Integer, Integer> modifyOrders(Order order, Order newOrder){
        int initialOrderRemaining = order.getRemainingContracts();
        int initialNewOrderRemaining = newOrder.getRemainingContracts();

        order.updateRemainingContracts(Math.max(0, initialOrderRemaining - initialNewOrderRemaining));
        newOrder.updateRemainingContracts(Math.max(0, initialNewOrderRemaining - initialOrderRemaining));

        order.updateStatus();
        newOrder.updateStatus();

        order.updateMatches(newOrder);
        newOrder.updateMatches(order);

        return Pair.of(initialNewOrderRemaining - newOrder.getRemainingContracts(),
                initialOrderRemaining - order.getRemainingContracts());
    }

}