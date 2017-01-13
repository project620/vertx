//Jan 9, 2017, jim.huang, create
package com.vertx3.database;

import org.springframework.stereotype.Component;

import io.vertx.core.Vertx;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jim.huang
 */

@Component
public class VertxJedisPool {

    JedisPool jedisPool;
    Boolean isInit;
    Vertx vertx;
    static final String cnofig = "jedis.json";

    public VertxJedisPool() {
        vertx = Vertx.vertx();
        final JedisPoolConfig config = new JedisPoolConfig();
        // determine whether the client is valid
        config.setTestOnBorrow(true);
        jedisPool = new JedisPool(config, "localhost", 6379);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close(final Jedis jedis) {
        jedis.close();
    }
}
