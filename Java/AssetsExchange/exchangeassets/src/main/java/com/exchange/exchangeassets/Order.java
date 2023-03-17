package com.exchange.exchangeassets;

import com.exchange.exchangeassets.Enums.Currency;
import com.exchange.exchangeassets.Enums.OrderStatus;
import com.exchange.exchangeassets.Enums.OrderType;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Order {
    private static int orderCounter = 0;
    private OrderType type;
    private Integer numContracts;
    private Integer limitPrice;
    private Currency currency;
    private String id;
    private int remainingContracts;
    private OrderStatus status;
    private CopyOnWriteArrayList<Order> matches;

    public Order(OrderType type, Integer numContracts, Integer limitPrice, Currency currency) {
        this.type = type;
        this.numContracts = numContracts;
        this.limitPrice = limitPrice;
        this.currency = currency;
        orderCounter += 1;
        this.id = this.type.name() + '_' + orderCounter;
        this.remainingContracts = numContracts;
        this.status = OrderStatus.Unfulfilled;
    }
}
    
    