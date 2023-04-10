package com.exchange.exchangeassets.common.transaction;

import com.exchange.exchangeassets.common.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class TransactionDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int transactionId;
    private int totalFilledContracts;
    private double totalAverageExecutionPrice;
    private String fulfillerId;
    private List<Order> matches;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.totalFilledContracts = transaction.getTotalFilledContracts();
        this.totalAverageExecutionPrice = transaction.getTotalAverageExecutionPrice();
        this.matches = transaction.getMatchedWith();
        this.fulfillerId = transaction.getFulfillerId();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getTotalFilledContracts() {
        return totalFilledContracts;
    }

    public double getTotalAverageExecutionPrice() {
        return totalAverageExecutionPrice;
    }

    public String getFulfillerId() {
        return fulfillerId;
    }

    public List<Order> getMatches() {
        return matches;
    }
}
