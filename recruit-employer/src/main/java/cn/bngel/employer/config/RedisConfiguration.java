package cn.bngel.employer.config;

import cn.bngel.redis.SimpleRedisClient;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.redis.cache.CacheRedisClient;
import cn.bngel.redis.token.TokenClient;
import cn.bngel.redis.token.TokenRedisClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public SimpleRedisClient simpleRedisClient() {
        return new SimpleRedisClient(host, account, password, port, timeout, maxIdle, maxTotal);
    }

    @Bean
    public TokenClient tokenClient() {
        return new TokenRedisClient();
    }

    @Bean
    public CacheClient cacheClient() {
        return new CacheRedisClient();
    }

}
