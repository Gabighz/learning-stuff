package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.enums.Currency;
import com.exchange.exchangeassets.common.enums.OrderSide;
import com.exchange.exchangeassets.common.enums.OrderType;

public record OrderDTO(OrderSide side, OrderType type, int numContracts, int limitPrice, Currency currency) {
}
