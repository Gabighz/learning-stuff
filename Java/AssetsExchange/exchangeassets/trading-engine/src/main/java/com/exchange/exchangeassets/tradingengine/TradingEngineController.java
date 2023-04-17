package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.*;
import com.exchange.exchangeassets.common.transaction.exceptions.InvalidTransactionException;
import com.exchange.exchangeassets.common.transaction.Transaction;
import com.exchange.exchangeassets.common.transaction.TransactionDTO;
import com.exchange.exchangeassets.tradingengine.orderstore.OrderStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

@RestController
public class TradingEngineController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradingEngineController.class);
    private final OrderStore orderStore;
    private final TransactionHistoryClient transactionHistoryClient;

    public TradingEngineController(
            OrderStore orderStore,
            TransactionHistoryClient transactionHistoryClient) {
        this.orderStore = orderStore;
        this.transactionHistoryClient = transactionHistoryClient;
    }

    @GetMapping("/orders")
    Collection<Order> getOrdersReceived() {
        return orderStore.getOrders();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    OrderSubmissionResponse submitOrder(@RequestBody OrderDTO newOrderDTO) {
        Order newOrder = new Order(
                newOrderDTO.side(),
                newOrderDTO.type(),
                newOrderDTO.numContracts(),
                newOrderDTO.limitPrice(),
                newOrderDTO.currency()
        );

        MatchResult matchResult = OrderMatcher.matchOrders(orderStore::getOrders, orderStore::deleteOrder, newOrder);


        if (newOrder.isNotFulfilled()) {
            orderStore.addOrder(newOrder);
        }

        if (newOrder.isFulfilled() || matchResult.matchedOrders().stream().anyMatch(Order::isFulfilled)) {
            try {
                processFulfilledOrders(newOrder, matchResult);
            } catch (InvalidTransactionException e) {
                LOGGER.error("An invalid transaction has been attempted! " + e.getMessage());
                e.printStackTrace();
            }
        }

        return new OrderSubmissionResponse(newOrder.getId(), newOrder.getMatches());
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    void cancelOrder(@PathVariable String id) {
        orderStore.deleteOrder(id);
    }

    private Transaction createTransaction(Order newOrder, MatchResult matchResult) throws InvalidTransactionException {
        return new Transaction(newOrder, matchResult);
    }

    private void processFulfilledOrders(Order newOrder, MatchResult matchResult) throws InvalidTransactionException {
        Transaction transaction = createTransaction(newOrder, matchResult);
        TransactionDTO transactionDTO = new TransactionDTO(transaction);

        transactionHistoryClient.postTransaction(transactionDTO);

        LOGGER.info("Sent fulfilled orders to TransactionHistory service: transactionId={}, numContracts={}, price={}",
                transactionDTO.getTransactionId(),
                transactionDTO.getTotalFilledContracts(),
                transactionDTO.getTotalAverageExecutionPrice());
    }
}
