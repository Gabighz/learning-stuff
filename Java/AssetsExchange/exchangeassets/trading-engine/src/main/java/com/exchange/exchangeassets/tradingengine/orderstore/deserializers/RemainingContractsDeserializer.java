package com.exchange.exchangeassets.tradingengine.orderstore.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RemainingContractsDeserializer extends JsonDeserializer<AtomicInteger> {

    @Override
    public AtomicInteger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        int value = p.readValueAs(Integer.class);
        return new AtomicInteger(value);
    }
}
