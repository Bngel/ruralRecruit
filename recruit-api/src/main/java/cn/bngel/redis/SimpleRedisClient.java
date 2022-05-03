package cn.bngel.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class SimpleRedisClient {

    public interface SyncContext {
        Object invoke(Jedis jedis);
    }

    public Object sync(SyncContext syncContext) {
        try (Jedis jedis = redisClientPool.getResource()) {
            return syncContext.invoke(jedis);
        }
    }

    public String get(String key) {
        return (String)sync(jedis -> jedis.get(key));
    }

    public String set(String key, String value) {
        return (String)sync(jedis -> jedis.set(key, value));
    }

    public String setex(String key, long seconds, String value) {
        return (String) sync(jedis -> jedis.setex(key, seconds, value));
    }

    public long del(String key) {
        return (long) sync(jedis -> jedis.del(key));
    }


    @Autowired
    private JedisPool redisClientPool;
}