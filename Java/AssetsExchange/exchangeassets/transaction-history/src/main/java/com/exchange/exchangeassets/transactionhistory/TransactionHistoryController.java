package com.exchange.exchangeassets.transactionhistory;

import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionHistoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryController.class);

    final TransactionSaver transactionSaver;

    public TransactionHistoryController(TransactionSaver transactionSaver) {
        this.transactionSaver = transactionSaver;
    }

    @KafkaListener(id = "transactionHistoryService", topics = "transaction-history-events")
    void receiveTransaction(TransactionDTO transactionDTO) {
        LOGGER.info("Received transaction of fulfilled order(s): transactionId={}, fulfillerId = {}, numContracts={}, " +
                        "price={}, numOrdersMatchedWith = {}",
                transactionDTO.getTransactionId(),
                transactionDTO.getFulfillerId(),
                transactionDTO.getTotalFilledContracts(),
                transactionDTO.getTotalAverageExecutionPrice(),
                transactionDTO.getMatches().size());

        transactionSaver.saveTransactionAndMatchingOrders(transactionDTO);

        LOGGER.info("Committed transaction with transactionId={}", transactionDTO.getTransactionId());
    }
}