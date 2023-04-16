package com.exchange.exchangeassets.tradingengine

import com.exchange.exchangeassets.tradingengine.orderstore.ListOrderStore
import com.exchange.exchangeassets.common.enums.Currency
import com.exchange.exchangeassets.common.enums.OrderSide
import com.exchange.exchangeassets.common.enums.OrderType
import com.exchange.exchangeassets.common.Order
import com.exchange.exchangeassets.tradingengine.orderstore.OrderStore
import spock.lang.Specification

class ListOrderStoreTest extends Specification {
    def "GetOrders"() {
        given:
        OrderStore orderStore = new ListOrderStore()
        Order order1 = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order order2 = new Order(OrderSide.SELL, OrderType.LIMIT, 10, 100, Currency.USD)
        orderStore.addOrder(order1)
        orderStore.addOrder(order2)

        expect:
        orderStore.getOrders().size() == 2
        orderStore.getOrders().contains(order1)
        orderStore.getOrders().contains(order2)
    }

    def "AddOrder"() {
        given:
        OrderStore orderStore = new ListOrderStore()
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        when:
        orderStore.addOrder(order)

        then:
        orderStore.getOrders().size() == 1
        orderStore.getOrders().contains(order)
    }
}