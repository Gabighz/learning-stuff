package com.exchange.exchangeassets.transactionhistory;

import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import com.exchange.exchangeassets.transactionhistory.dao.TransactionDAO;
import com.exchange.exchangeassets.transactionhistory.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryController.class);

    @Autowired
    TransactionDAO transactionDAO;

    @PostMapping("/transactions")
    void receiveTransaction(@RequestBody TransactionDTO transactionDTO) {
        LOGGER.info("Received transaction of fulfilled order(s): transactionId={}, fulfillerId = {}, numContracts={}, " +
                        "price={}, numOrdersMatchedWith = {}",
                transactionDTO.getTransactionId(),
                transactionDTO.getFulfillerId(),
                transactionDTO.getTotalFilledContracts(),
                transactionDTO.getTotalAverageExecutionPrice(),
                transactionDTO.getMatches().size());

        transactionDAO.save(new Transaction(transactionDTO));

    }
}