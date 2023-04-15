package com.exchange.exchangeassets.common;

import com.exchange.exchangeassets.common.enums.Currency;
import com.exchange.exchangeassets.common.enums.OrderSide;
import com.exchange.exchangeassets.common.enums.OrderStatus;
import com.exchange.exchangeassets.common.enums.OrderType;
import jakarta.persistence.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Entity
public class Order implements Comparable<Order>{

    private final UUID id;
    private final OrderSide side;
    private final OrderType type;
    private final int numContracts;
    private final int limitPrice;
    private final Currency currency;
    private final AtomicInteger remainingContracts;
    private final AtomicReference<OrderStatus> status;
    private final OrderMatches matches;

    public Order(OrderSide side, OrderType type, int numContracts, int limitPrice, Currency currency) {
        this.side = side;
        this.type = type;
        this.id = UUID.randomUUID();

        this.numContracts = numContracts;
        this.limitPrice = limitPrice;
        this.currency = currency;
        this.remainingContracts = new AtomicInteger(numContracts);
        this.status = new AtomicReference<>(OrderStatus.Unfulfilled);
        this.matches = new OrderMatches();
    }

    public boolean canBeMatchedWith(@NotNull Order order) {
        return this.side != order.getSide()
                && this.currency == order.getCurrency()
                && isPriceMatchingWith(order)
                && this.remainingContracts.get() >= order.remainingContracts.get();
    }

    public boolean isPriceMatchingWith(Order order) {
        // NOTE: Supporting LIMIT common only at the moment
        if (side == OrderSide.BUY) {
            return this.limitPrice >= order.limitPrice;
        } else {
            return this.limitPrice <= order.limitPrice;
        }
    }

    public OrderSide getSide() {
        return side;
    }

    public OrderType getType() {
        return type;
    }

    public Integer getNumContracts() {
        return numContracts;
    }

    public Integer getLimitPrice() {
        return limitPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public UUID getId() {
        return id;
    }

    public int getRemainingContracts() {
        return remainingContracts.get();
    }

    public OrderStatus getStatus() {
        return status.get();
    }

    public OrderMatches getMatches() {
        return matches;
    }

    public void updateMatches(Order newOrder) {
        this.getMatches().addMatch(newOrder.getId(),
                newOrder.getNumContracts(),
                (this.getLimitPrice() + newOrder.getLimitPrice()) / 2);
    }

    public void updateStatus() {
        if (this.getRemainingContracts() == 0) {
            this.status.set(OrderStatus.Fulfilled);
        } else if (this.getRemainingContracts() < this.getNumContracts()) {
                this.status.set(OrderStatus.PartiallyFilled);
        }
    }

    public void updateRemainingContracts(int newRemainingContracts) {
        this.remainingContracts.set(newRemainingContracts);
    }

    public boolean isFulfilled() {
        return this.getRemainingContracts() == 0;
    }

    public boolean isNotFulfilled() {
        return this.getRemainingContracts() > 0;
    }

    @Override
    public int compareTo(@NotNull Order other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", side=" + side +
                ", type=" + type +
                ", numContracts=" + numContracts +
                ", limitPrice=" + limitPrice +
                ", currency=" + currency +
                ", remainingContracts=" + remainingContracts +
                ", status=" + status +
                '}';
    }
}