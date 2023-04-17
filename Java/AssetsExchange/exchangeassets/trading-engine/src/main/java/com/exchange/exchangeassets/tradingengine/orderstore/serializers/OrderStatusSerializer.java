package com.exchange.exchangeassets.tradingengine.orderstore.serializers;

import com.exchange.exchangeassets.common.enums.OrderStatus;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;

public class OrderStatusSerializer extends StdScalarSerializer<OrderStatus> {

    public OrderStatusSerializer() {
        super(OrderStatus.class);
    }

    @Override
    public void serialize(OrderStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.name());
    }
}