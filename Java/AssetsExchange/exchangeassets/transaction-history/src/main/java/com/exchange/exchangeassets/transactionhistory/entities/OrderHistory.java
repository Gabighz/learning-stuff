package com.exchange.exchangeassets.transactionhistory.entities;

import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="orders")
public class OrderHistory {

    @Id
    @Column(name="id")
    private UUID orderId;

    @JoinColumn(name = "transaction_id")
    private UUID transactionId;

    public OrderHistory() {
    }

    public OrderHistory (UUID orderUUID, TransactionDTO transactionDTO) {
        this.orderId = orderUUID;
        this.transactionId = transactionDTO.getTransactionId();
    }
}
