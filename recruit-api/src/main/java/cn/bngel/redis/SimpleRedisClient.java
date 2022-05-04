package cn.bngel.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SimpleRedisClient {

    public SimpleRedisClient(String host, String account, String password, int port, int timeout, int maxIdle, int maxTotal) {
        this.redisClientPool = getJedisPool(host, account, password, port, timeout, maxIdle, maxTotal);
    }

    public interface SyncContext {
        Object invoke(Jedis jedis);
    }

    public interface SyncContextWithArgs {
        Object invoke(Jedis jedis, Object args);
    }

    public Object sync(SyncContext syncContext) {
        try (Jedis jedis = redisClientPool.getResource()) {
            return syncContext.invoke(jedis);
        }
    }

    public Object syncWithArgs(SyncContextWithArgs syncContext, Object... args) {
        try (Jedis jedis = redisClientPool.getResource()) {
            return syncContext.invoke(jedis, args);
        }
    }

    public String get(String key) {
        return (String)sync(jedis -> jedis.get(key));
    }

    public String set(String key, String value) {
        return (String)sync(jedis -> jedis.set(key, value));
    }

    public String setex(String key, int seconds, String value) {
        return (String) sync(jedis -> jedis.setex(key, seconds, value));
    }

    public long del(String key) {
        return (long) sync(jedis -> jedis.del(key));
    }

    public long expire(String key, int seconds) {
        return (long) sync(jedis -> jedis.expire(key, seconds));
    }

    private JedisPool redisClientPool;

    private JedisPool getJedisPool(String host, String account, String password, int port, int timeout, int maxIdle, int maxTotal) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        String auth = password;
        if (account != null) {
            auth = account + ":" + password;
        }
        return new JedisPool(config, host, port, timeout, auth);
    }
}