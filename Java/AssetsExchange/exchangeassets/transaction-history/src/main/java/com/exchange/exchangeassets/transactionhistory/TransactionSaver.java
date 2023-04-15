package com.exchange.exchangeassets.transactionhistory;

import com.exchange.exchangeassets.common.Order;
import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import com.exchange.exchangeassets.transactionhistory.dao.OrderHistoryDAO;
import com.exchange.exchangeassets.transactionhistory.dao.TransactionDAO;
import com.exchange.exchangeassets.transactionhistory.entities.OrderHistory;
import com.exchange.exchangeassets.transactionhistory.entities.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionSaver {

    private final TransactionDAO transactionDAO;
    private final OrderHistoryDAO orderHistoryDAO;

    public TransactionSaver(TransactionDAO transactionDAO, OrderHistoryDAO orderHistoryDAO) {
        this.transactionDAO = transactionDAO;
        this.orderHistoryDAO = orderHistoryDAO;
    }

    @Transactional
    public void saveTransactionAndMatchingOrders(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction(transactionDTO);
        transactionDAO.save(transaction);

        transactionDTO.getMatches().parallelStream()
                .filter(Order::isFulfilled)
                .map(order -> new OrderHistory(order.getId(), transactionDTO))
                .forEach(orderHistoryDAO::save);
    }
}
