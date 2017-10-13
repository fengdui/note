package com.fengdui.test.shiro.server.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by fd on 2016/9/8.
 */
public class RedisClient {

    public static int SessionTimeout = 60*30;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private ShardedJedis getResource() {
        return shardedJedisPool.getResource();
    }

    public void set(String key, String value) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            shardedJedis.set(key, value);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }
    public String get(String key) {
        ShardedJedis shardedJedis = null;
        String value = null;
        try {
            shardedJedis = getResource();
            value =  shardedJedis.get(key);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return value;
    }

    public void expire(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            shardedJedis.expire(key, SessionTimeout);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }
    public void del(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getResource();
            shardedJedis.del(key);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
    }
}
