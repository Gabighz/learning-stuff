package com.exchange.exchangeassets.transactionhistory.dao;

import com.exchange.exchangeassets.transactionhistory.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryDAO extends JpaRepository<OrderHistory, String> {
}
