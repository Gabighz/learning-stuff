package com.exchange.exchangeassets.common.transaction;

import com.exchange.exchangeassets.common.Order;
import java.util.List;
import java.util.UUID;

public class TransactionDTO {

    private UUID transactionId;
    private UUID fulfillerId;
    private int totalFilledContracts;
    private double totalAverageExecutionPrice;
    private List<Order> matches;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.fulfillerId = transaction.getFulfillerId();
        this.totalFilledContracts = transaction.getTotalFilledContracts();
        this.totalAverageExecutionPrice = transaction.getTotalAverageExecutionPrice();
        this.matches = transaction.getMatchedWith();
    }

    public UUID getTransactionId() {
        return transactionId;
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

    public List<Order> getMatches() {
        return matches;
    }
}
