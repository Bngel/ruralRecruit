package cn.bngel.applicant.service.cache;

import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.Constant;
import cn.bngel.redis.SimpleRedisClient;
import cn.bngel.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class CacheRedisClient implements CacheClient{

    @Autowired
    private SimpleRedisClient redisClient;

    // 设置key的过期时间
    private final int MIN_EXPIRED_TIME = 600;
    private final int MAX_EXPIRED_TIME = 3600;

    /**
     * 通过手机号在Redis中尝试获取缓存
     * @param key Redis中的key
     * @return 若存在则返回缓存, 否则返回NULL
     */
    public Object getCache(String key) {
        Object obj;
        try {
            obj = redisClient.sync(jedis -> {
                String serialized = jedis.get(key);
                if (serialized != null) {
                    return SerializeUtil.deserializeToObject(serialized);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * 通过手机号在Redis中尝试获取缓存列表
     * @param keys Redis中的key list
     * @return 若存在则返回缓存, 否则返回NULL
     */
    public List<Object> listCaches(List<String> keys) {
        List<Object> objects = new ArrayList<>();
        try {
            for (String key: keys) {
                Applicant applicant = (Applicant) redisClient.sync(jedis -> {
                    String serialized = jedis.get(key);
                    if (serialized != null) {
                        return SerializeUtil.deserializeToObject(serialized);
                    }
                    return null;
                });
                if (applicant != null) {
                    objects.add(applicant);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objects;
    }

    /**
     * 将数据放入Redis缓存中(如果已存在则覆盖)
     * @param obj 放入缓存的对象
     */
    public void setCache(String key, Object obj) {
        try {
            int seconds = MIN_EXPIRED_TIME + new Random().nextInt(MAX_EXPIRED_TIME - MIN_EXPIRED_TIME);
            redisClient.sync(jedis ->
                    jedis.setex(key, seconds, SerializeUtil.serializeToString(obj)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCaches(Map<String, Object> data) {
        try {
            redisClient.sync(jedis -> {
                for (String key: data.keySet()) {
                    int seconds = MIN_EXPIRED_TIME + new Random().nextInt(MAX_EXPIRED_TIME - MIN_EXPIRED_TIME);
                    jedis.setex(key, seconds, SerializeUtil.serializeToString(data.get(key)));
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}