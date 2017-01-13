/**
 * Jan 9, 2017, jim.huang, create
 */
package com.vertx3.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vertx3.database.VertxJedisPool;
import com.vertx3.util.GsonUtil;

import redis.clients.jedis.Jedis;

/**
 * @author jim.huang
 */

@Component("cacheManager")
public class JedisManager implements CacheManager {

    @Autowired
    VertxJedisPool pool;

    /*
     * (non-Javadoc)
     * @see com.vertx3.util.CacheManager#get(java.lang.String)
     */
    @Override
    public <T> T get(final String key, final Class<T> keyClass) {
        final Jedis jedis = pool.getJedis();
        final String value = jedis.get(key);
        final T result = GsonUtil.toEntity(value, keyClass);
        pool.close(jedis);
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.vertx3.util.CacheManager#set(java.lang.String, java.lang.Object)
     */
    @Override
    public void set(final String key, final Object value) {
        final Jedis jedis = pool.getJedis();
        final String encode = GsonUtil.toJson(value);
        jedis.set(key, encode);
        pool.close(jedis);
    }

    /*
     * (non-Javadoc)
     * @see com.vertx3.util.CacheManager#delete(java.lang.String)
     */
    @Override
    public void delete(final String key) {
        final Jedis jedis = pool.getJedis();
        jedis.del(key);
        pool.close(jedis);
    }

    /*
     * (non-Javadoc)
     * @see com.vertx3.util.CacheManager#add(java.lang.String, java.lang.Object)
     */
    @Override
    public void add(final String key, final Object object) {
        final Jedis jedis = pool.getJedis();
        final String value = GsonUtil.toJson(object);
        jedis.append(key, value);
        pool.close(jedis);
    }

}
