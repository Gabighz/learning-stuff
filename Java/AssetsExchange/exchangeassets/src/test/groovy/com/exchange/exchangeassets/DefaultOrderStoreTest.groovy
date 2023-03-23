package com.exchange.exchangeassets

import com.exchange.exchangeassets.orders.DefaultOrderStore
import com.exchange.exchangeassets.orders.Enums.Currency
import com.exchange.exchangeassets.orders.Enums.OrderSide
import com.exchange.exchangeassets.orders.Enums.OrderType
import com.exchange.exchangeassets.orders.Order
import com.exchange.exchangeassets.orders.OrderStore
import spock.lang.Specification

class DefaultOrderStoreTest extends Specification {
    def "GetOrders"() {
        given:
        OrderStore orderStore = new DefaultOrderStore()
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
        OrderStore orderStore = new DefaultOrderStore()
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        when:
        orderStore.addOrder(order)

        then:
        orderStore.getOrders().size() == 1
        orderStore.getOrders().contains(order)
    }
}