package com.exchange.exchangeassets.tradingengine.orderstore.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class RemainingContractsSerializer extends StdScalarSerializer<AtomicInteger> {

    public RemainingContractsSerializer() {
        super(AtomicInteger.class);
    }

    @Override
    public void serialize(AtomicInteger value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.get());
    }
}
