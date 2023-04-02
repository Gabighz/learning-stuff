package com.exchange.exchangeassets.tradingengine;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.exchange.exchangeassets.common.OrderMatches;
import java.io.IOException;

public class OrderMatchesSerializer extends JsonSerializer<OrderMatches> {

    @Override
    public void serialize(OrderMatches orderMatches, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(orderMatches.getMatches());
    }
}
