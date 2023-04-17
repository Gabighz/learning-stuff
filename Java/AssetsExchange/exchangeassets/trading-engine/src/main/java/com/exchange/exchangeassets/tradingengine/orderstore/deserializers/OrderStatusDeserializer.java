package com.exchange.exchangeassets.tradingengine.orderstore.deserializers;

import com.exchange.exchangeassets.common.enums.OrderStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OrderStatusDeserializer extends StdDeserializer<OrderStatus> {

    public OrderStatusDeserializer() {
        super(OrderStatus.class);
    }

    @Override
    public OrderStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        parser.nextTextValue();
        String name = parser.nextTextValue();
        return OrderStatus.valueOf(name);
    }
}

