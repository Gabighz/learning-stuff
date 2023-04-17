package com.exchange.exchangeassets.tradingengine.orderstore;

import com.exchange.exchangeassets.common.enums.OrderStatus;
import com.exchange.exchangeassets.tradingengine.orderstore.deserializers.OrderStatusDeserializer;
import com.exchange.exchangeassets.tradingengine.orderstore.deserializers.RemainingContractsDeserializer;
import com.exchange.exchangeassets.tradingengine.orderstore.serializers.RemainingContractsSerializer;
import com.exchange.exchangeassets.tradingengine.orderstore.serializers.OrderStatusSerializer;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.redisson.codec.JsonJacksonCodec;
import java.util.concurrent.atomic.AtomicInteger;

/*
    Will serialize as following:

    127.0.0.1:6379> KEYS *
    1) "order:4eb14d4b-ab90-45e6-adb6-d162fb67df07"
    127.0.0.1:6379> GET order:4eb14d4b-ab90-45e6-adb6-d162fb67df07
    "{\"@class\":\"com.exchange.exchangeassets.common.Order\",\"currency\":\"USD\",\"limitPrice\":10,\"numContracts\":100,
    \"side\":\"BUY\",\"type\":\"LIMIT\",\"fulfilled\":false,\"id\":\"4eb14d4b-ab90-45e6-adb6-d162fb67df07\",
    \"matches\":{\"@class\":\"com.exchange.exchangeassets.common.OrderMatches\",
    \"matches\":[\"java.util.concurrent.ConcurrentLinkedQueue\",[]]},\"notFulfilled\":true,
    \"remainingContracts\":[\"java.util.concurrent.atomic.AtomicInteger\",100],
    \"status\":[\"com.exchange.exchangeassets.common.enums.OrderStatus\",\"Unfulfilled\"]}"
 */
public class RedisCodec extends JsonJacksonCodec {

    public RedisCodec() {
        super(createCustomObjectMapper());
    }

    private static ObjectMapper createCustomObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(new RemainingContractsSerializer());
        module.addSerializer(new OrderStatusSerializer());

        module.addDeserializer(OrderStatus.class, new OrderStatusDeserializer());
        module.addDeserializer(AtomicInteger.class, new RemainingContractsDeserializer());

        objectMapper.registerModule(module);
        return objectMapper;
    }
}


