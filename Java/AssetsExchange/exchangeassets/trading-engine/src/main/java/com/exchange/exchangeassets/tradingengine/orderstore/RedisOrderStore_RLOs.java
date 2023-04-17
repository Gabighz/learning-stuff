package com.exchange.exchangeassets.tradingengine.orderstore;

import com.exchange.exchangeassets.common.Order;

import org.redisson.api.RedissonClient;
import org.redisson.api.condition.Condition;
import org.redisson.api.condition.Conditions;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

/*
    Keeping this dead code for self-educational purposes and because it was a lot of work.

    Serialization and deserialization did not happen as intended. Therefore, GET /orders returned [] and there was
    no order matching.

    Order instances were represented and stored as:
    127.0.0.1:6379> KEYS *
    1) "redisson_live_object:{2233326566613561652d623361322d346135302d623164372d64396163383437666262363522}:com.exchange.exchangeassets.common.Order"
 */

@Component
public class RedisOrderStore_RLOs implements OrderStore {

    private final RedissonClient redissonClient;

    public RedisOrderStore_RLOs(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Collection<Order> getOrders() {
        Condition condition = Conditions.eq("id", "*");
        return redissonClient.getLiveObjectService().find(Order.class, condition);
    }

    @Override
    public void addOrder(Order order) {
        redissonClient.getLiveObjectService().persist(order);
    }

    @Override
    public void deleteOrder(String id) {
        Order rlOrder = redissonClient.getLiveObjectService().get(Order.class, UUID.fromString(id));
        if (rlOrder != null) {
            redissonClient.getLiveObjectService().delete(rlOrder);
        }
    }
}
