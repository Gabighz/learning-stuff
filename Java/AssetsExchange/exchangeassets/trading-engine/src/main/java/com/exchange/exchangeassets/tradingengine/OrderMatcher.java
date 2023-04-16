package com.exchange.exchangeassets.tradingengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.exchange.exchangeassets.common.MatchResult;
import com.exchange.exchangeassets.common.Order;
import org.springframework.data.util.Pair;

public class OrderMatcher {

    public static MatchResult matchOrders(Supplier<Collection<Order>> currentOrdersSupplier, Order newOrder) {
        List<Pair<Integer, Integer>> filledContracts = new ArrayList<>();

        Collection<Order> currentOrders = currentOrdersSupplier.get();
        List<Order> matchedOrders = currentOrders.stream()
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