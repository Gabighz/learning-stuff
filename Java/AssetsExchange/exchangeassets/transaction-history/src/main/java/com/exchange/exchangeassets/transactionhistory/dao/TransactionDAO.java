package com.exchange.exchangeassets.transactionhistory.dao;

import com.exchange.exchangeassets.transactionhistory.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDAO extends JpaRepository<Transaction, String> {
}
