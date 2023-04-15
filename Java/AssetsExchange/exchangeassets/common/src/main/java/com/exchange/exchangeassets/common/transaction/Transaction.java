package com.exchange.exchangeassets.common.transaction;

import com.exchange.exchangeassets.common.MatchResult;
import com.exchange.exchangeassets.common.Order;
import com.exchange.exchangeassets.common.transaction.exceptions.InvalidTransactionException;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.UUID;

public class Transaction {

    private final UUID id;
    private final UUID fulfillerId;
    private final int totalFilledContracts;
    private final double totalAverageExecutionPrice;
    private final List<Order> matchedWith;

    public Transaction(Order newOrder, MatchResult matchResult) throws InvalidTransactionException {
        this.id = UUID.randomUUID();
        List<Pair<Integer, Integer>> filledContracts = matchResult.filledContracts();
        matchedWith = matchResult.matchedOrders();

        List<UUID> fulfilledOrderIds = matchResult.matchedOrders().stream()
                .filter(Order::isFulfilled)
                .map(Order::getId)
                .toList();

        if (newOrder.isFulfilled()) {
            fulfillerId = newOrder.getId();
        } else if (fulfilledOrderIds.size() > 1) {
            fulfillerId = fulfilledOrderIds.get(0);
        } else {
            throw new InvalidTransactionException("Invalid Transaction attempted with order " + newOrder.getId() + " !");
        }

        totalFilledContracts = filledContracts.stream()
                .mapToInt(pair -> pair.getFirst() + pair.getSecond())
                .sum();

        int sumOfAverageExecutionPrices = matchedWith.stream()
                .mapToInt(order -> (order.getLimitPrice() + newOrder.getLimitPrice()) / 2)
                .sum();

        totalAverageExecutionPrice = (double) sumOfAverageExecutionPrices / matchedWith.size();
    }

    public UUID getTransactionId() {
        return id;
    }

    public UUID getFulfillerId() {
        return fulfillerId;
    }

    public int getTotalFilledContracts() {
        return totalFilledContracts;
    }

    public double getTotalAverageExecutionPrice() {
        return totalAverageExecutionPrice;
    }

    public List<Order> getMatchedWith() {
        return matchedWith;
    }

}
