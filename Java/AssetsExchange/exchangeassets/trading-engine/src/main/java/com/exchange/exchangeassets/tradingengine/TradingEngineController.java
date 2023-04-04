package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.MatchResult;
import com.exchange.exchangeassets.common.Order;
import com.exchange.exchangeassets.common.OrderMatcher;
import com.exchange.exchangeassets.common.OrderStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

@RestController
public class TradingEngineController {

    final OrderStore orderStore;

    private static final Logger LOGGER = LoggerFactory.getLogger(TradingEngineController.class);

    @Autowired
    TransactionHistoryClient transactionHistoryClient;

    @Autowired
    TradingEngineController(OrderStore orderStore) {
        this.orderStore = orderStore;
    }

    @GetMapping("/orders")
    Collection<Order> getOrdersReceived() {
        return orderStore.getOrders();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    OrderSubmissionResponse submitOrder(@RequestBody Order newOrder) {
        MatchResult matchResult = OrderMatcher.matchOrders(orderStore, newOrder);

        if (newOrder.isNotFulfilled()) {
            orderStore.getOrders().add(newOrder);
        }

        if (newOrder.isFulfilled() || matchResult.matchedOrders().stream().anyMatch(Order::isFulfilled)) {
            processFulfilledOrders(newOrder, matchResult);
        }

        return new OrderSubmissionResponse(newOrder.getId(), newOrder.getMatches());
    }

    @DeleteMapping("/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    void cancelOrder(@PathVariable String id) {
        orderStore.deleteOrder(id);
    }

    private Transaction createTransaction(Order newOrder, MatchResult matchResult) {
        return new Transaction(newOrder, matchResult);
    }

    private void processFulfilledOrders(Order newOrder, MatchResult matchResult) {
        Transaction transaction = createTransaction(newOrder, matchResult);

        LOGGER.info("Sending fulfilled orders to TransactionHistory service: transactionId={}, numContracts={}, price={}",
                transaction.getTransactionId(),
                transaction.getTotalFilledContracts(),
                transaction.getTotalAverageExecutionPrice());

        transactionHistoryClient.postTransaction(transaction);

        LOGGER.info("Sent fulfilled orders to TransactionHistory service: transactionId={}, numContracts={}, price={}",
                transaction.getTransactionId(),
                transaction.getTotalFilledContracts(),
                transaction.getTotalAverageExecutionPrice());
    }

}
