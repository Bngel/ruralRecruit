package cn.bngel.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class SimpleRedisClient {

    @Autowired
    private JedisPool redisClientPool;

    public interface SyncContext {
        Object invoke(Jedis jedis);
    }

    public Object sync(SyncContext syncContext) {
        try (Jedis jedis = redisClientPool.getResource()) {
            return syncContext.invoke(jedis);
        }
    }

}