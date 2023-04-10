package com.exchange.exchangeassets

import com.exchange.exchangeassets.common.enums.Currency
import com.exchange.exchangeassets.common.enums.OrderSide
import com.exchange.exchangeassets.common.enums.OrderStatus
import com.exchange.exchangeassets.common.enums.OrderType
import com.exchange.exchangeassets.common.Order
import spock.lang.Specification

class OrderTest extends Specification {

    def "CanBeMatchedWith"() {
        given:
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order sellOrder = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 90, Currency.USD)
        Order sameSideOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 5, 90, Currency.USD)
        Order differentCurrencyOrder = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 90, Currency.EUR)

        expect:
        buyOrder.canBeMatchedWith(sellOrder)
        !buyOrder.canBeMatchedWith(sameSideOrder)
        !buyOrder.canBeMatchedWith(differentCurrencyOrder)
    }

    def "IsPriceMatchingWith"() {
        given:
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order sellOrderHigher = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 90, Currency.USD)
        Order sellOrderLower = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 110, Currency.USD)

        expect:
        buyOrder.isPriceMatchingWith(sellOrderHigher)
        !buyOrder.isPriceMatchingWith(sellOrderLower)
    }

    def "GetSide"() {
        given:
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        buyOrder.getSide() == OrderSide.BUY
    }

    def "GetType"() {
        given:
        Order limitOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        limitOrder.getType() == OrderType.LIMIT
    }

    def "GetNumContracts"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getNumContracts() == 10
    }

    def "GetLimitPrice"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getLimitPrice() == 100
    }

    def "GetCurrency"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getCurrency() == Currency.USD
    }

    def "GetId"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getId() != null
        order.getId().startsWith("BUY_")
    }

    def "GetRemainingContracts"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getRemainingContracts() == 10
    }

    def "GetStatus"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getStatus() == OrderStatus.Unfulfilled
    }

    def "GetMatches"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.getMatches() != null
    }

    def "UpdateMatches"() {
        given:
        Order buyOrder = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order sellOrder = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 90, Currency.USD)

        when:
        buyOrder.updateMatches(sellOrder)

        then:
        buyOrder.getMatches().getMatches().size() == 1
        buyOrder.getMatches().getMatches()[0].id() == sellOrder.getId()
    }

    def "UpdateStatus"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        when:
        order.updateStatus()

        then:
        order.getStatus() == OrderStatus.Unfulfilled

        when:
        order.updateRemainingContracts(0)
        order.updateStatus()

        then:
        order.getStatus() == OrderStatus.Fulfilled
    }

    def "UpdateRemainingContracts"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        when:
        order.updateRemainingContracts(5)

        then:
        order.getRemainingContracts() == 5
    }

    def "CompareTo"() {
        given:
        Order order1 = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)
        Order order2 = new Order(OrderSide.SELL, OrderType.LIMIT, 5, 90, Currency.USD)

        expect:
        order1 != order2
        order1 == order1
    }

    def "ToString"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        order.toString() != null
        order.toString().contains("Order{")
        order.toString().contains("id='")
    }
}