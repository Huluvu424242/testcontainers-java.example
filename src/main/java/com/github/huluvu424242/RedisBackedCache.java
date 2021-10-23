package com.github.huluvu424242;

import java.util.Optional;
import redis.clients.jedis.Jedis;

/**
 * An implementation of {@link Cache} that stores data in Redis.
 */
public class RedisBackedCache implements Cache {

    private final Jedis jedis;
    private final String cacheName;

    public RedisBackedCache(Jedis jedis, String cacheName) {
        this.jedis = jedis;
        this.cacheName = cacheName;
    }

    @Override
    public void put(String key, String value) {
        this.jedis.hset(this.cacheName, key, value);
    }

    @Override
    public Optional<String> get(String key) {
        String foundJson = this.jedis.hget(this.cacheName, key);

        if (foundJson == null) {
            return Optional.empty();
        }

        return Optional.of(foundJson);
    }
}
