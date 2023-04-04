package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.MatchResult;
import com.exchange.exchangeassets.common.Order;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.UUID;

public class Transaction {

    private final String id;
    private final int totalFilledContracts;
    private final double totalAverageExecutionPrice;

    Transaction(Order newOrder, MatchResult matchResult) {
        this.id = String.valueOf(UUID.randomUUID());
        List<Pair<Integer, Integer>> filledContracts = matchResult.filledContracts();
        List<Order> matchedWith = matchResult.matchedOrders();

        totalFilledContracts = filledContracts.stream()
                .mapToInt(pair -> pair.getFirst() + pair.getSecond())
                .sum();

        int sumOfAverageExecutionPrices = matchedWith.stream()
                .mapToInt(order -> (order.getLimitPrice() + newOrder.getLimitPrice()) / 2)
                .sum();

        totalAverageExecutionPrice = (double) sumOfAverageExecutionPrices / matchedWith.size();
    }

    public String getTransactionId() {
        return id;
    }

    public int getTotalFilledContracts() {
        return totalFilledContracts;
    }

    public double getTotalAverageExecutionPrice() {
        return totalAverageExecutionPrice;
    }

}
