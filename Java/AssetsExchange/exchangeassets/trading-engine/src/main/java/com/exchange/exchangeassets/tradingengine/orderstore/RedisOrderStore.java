package com.exchange.exchangeassets.tradingengine.orderstore;

import com.exchange.exchangeassets.common.Order;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

import java.util.Collection;

@Component
@Primary
public class RedisOrderStore implements OrderStore {
    private static final String ORDER_KEY_PREFIX = "order:";
    private final RedissonClient redissonClient;

    public RedisOrderStore() {
        this.redissonClient = new RedissonConfig().redissonClient();
    }

    @Override
    public Collection<Order> getOrders() {
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keysIterator = keys.getKeysByPattern(ORDER_KEY_PREFIX + "*");
        List<Order> orders = new ArrayList<>();

        for (String key : keysIterator) {
            RBucket<Order> bucket = redissonClient.getBucket(key);
            Order order = bucket.get();
            if (order != null) {
                orders.add(order);
            }
        }

        return orders;
    }

    @Override
    public void addOrder(Order order) {
        String key = getOrderKey(String.valueOf(order.getId()));
        RBucket<Order> bucket = redissonClient.getBucket(key);
        bucket.set(order);
    }

    @Override
    public void deleteOrder(String id) {
        String key = getOrderKey(id);
        redissonClient.getKeys().delete(key);
    }

    private String getOrderKey(String id) {
        return ORDER_KEY_PREFIX + id;
    }
}
