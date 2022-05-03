package cn.bngel.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

    @Value("${recruit-redis-config.host}")
    private String host;

    @Value("${recruit-redis-config.account}")
    private String account;

    @Value("${recruit-redis-config.password}")
    private String password;

    @Value("${recruit-redis-config.port}")
    private int port;

    @Value("${recruit-redis-config.timeout}")
    private int timeout;

    @Value("${recruit-redis-config.maxIdle}")
    private int maxIdle;

    @Value("${recruit-redis-config.maxTotal}")
    private int maxTotal;

    @Bean
    public JedisPool getJedisPool() {
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
