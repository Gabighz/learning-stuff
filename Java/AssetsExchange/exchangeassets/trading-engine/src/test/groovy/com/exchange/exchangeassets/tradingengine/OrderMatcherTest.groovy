package com.exchange.exchangeassets.tradingengine

import com.exchange.exchangeassets.tradingengine.orderstore.ListOrderStore
import com.exchange.exchangeassets.tradingengine.orderstore.OrderStore
import com.exchange.exchangeassets.common.enums.Currency
import com.exchange.exchangeassets.common.enums.OrderSide
import com.exchange.exchangeassets.common.enums.OrderStatus
import com.exchange.exchangeassets.common.enums.OrderType
import com.exchange.exchangeassets.common.Order
import spock.lang.Specification

class OrderMatcherTest extends Specification {

    def "MatchOrders"() {
        given:
        OrderStore orderStore = new ListOrderStore()
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order sellOrder = new Order(OrderSide.SELL, OrderType.LIMIT, 10, 100, Currency.USD)
        orderStore.addOrder(buyOrder)
        orderStore.addOrder(sellOrder)

        when:
        Order newOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 5, 100, Currency.USD)
        OrderMatcher.matchOrders(orderStore::getOrders, newOrder)

        then:
        buyOrder.getRemainingContracts() == 10
        sellOrder.getRemainingContracts() == 5
        newOrder.getRemainingContracts() == 0
        buyOrder.getStatus() == OrderStatus.Unfulfilled
        sellOrder.getStatus() == OrderStatus.PartiallyFilled
        newOrder.getStatus() == OrderStatus.Fulfilled
    }

    def "ModifyOrders"() {
        given:
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order sellOrder = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 100, Currency.USD)

        when:
        OrderMatcher.modifyOrders(buyOrder, sellOrder)

        then:
        buyOrder.getRemainingContracts() == 5
        sellOrder.getRemainingContracts() == 0
        buyOrder.getStatus() == OrderStatus.PartiallyFilled
        sellOrder.getStatus() == OrderStatus.Fulfilled
        buyOrder.getMatches().getMatches().size() == 1
        sellOrder.getMatches().getMatches().size() == 1
    }
}