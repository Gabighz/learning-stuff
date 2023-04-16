package com.exchange.exchangeassets.transactionhistory.entities;

import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    private UUID transactionId;

    @Column(name = "fulfiller_id")
    private UUID fulfillerId;

    @Column(name = "total_filled_contracts")
    private int totalFilledContracts;

    @Column(name = "total_average_execution_price")
    private double totalAverageExecutionPrice;

    public Transaction() {
    }

    public Transaction(TransactionDTO transactionDTO) {
        this.transactionId = transactionDTO.getTransactionId();
        this.fulfillerId = transactionDTO.getFulfillerId();
        this.totalFilledContracts = transactionDTO.getTotalFilledContracts();
        this.totalAverageExecutionPrice = transactionDTO.getTotalAverageExecutionPrice();
    }
}
