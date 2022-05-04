package cn.bngel.redis.cache;

import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.Constant;
import cn.bngel.redis.SimpleRedisClient;
import cn.bngel.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

public class CacheRedisClient implements CacheClient{

    @Autowired
    private SimpleRedisClient redisClient;

    /**
     * 通过key在Redis中尝试获取缓存
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
     * 通过key在Redis中尝试获取缓存列表
     * @param keys Redis中的key list
     * @return 若存在则返回缓存, 否则返回NULL
     */
    public List<Object> listCaches(Collection<String> keys) {
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
     * 通过key在Redis中尝试获取缓存列表
     * @param key Redis中的key
     * @return 若存在则返回缓存, 否则返回NULL
     */
    public List<Object> listCaches(String key) {
        List<Object> objects;
        try {
            objects = (List<Object>) redisClient.sync(jedis -> {
                Set<String> serializedSet = jedis.smembers(key);
                List<Object> cacheObjects = new ArrayList<>();
                if (serializedSet != null && serializedSet.size() > 0) {
                    for (String serialized: serializedSet) {
                        cacheObjects.add(SerializeUtil.deserializeToObject(serialized));
                    }
                    return cacheObjects;
                }
                return null;
            });
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
            int seconds = Constant.CACHE_EXPIRE_TIME_MIN + new Random().nextInt(Constant.CACHE_EXPIRE_TIME_MAX - Constant.CACHE_EXPIRE_TIME_MIN);
            redisClient.sync(jedis ->
                    jedis.setex(key, seconds, SerializeUtil.serializeToString(obj)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据放入Redis缓存中(如果已存在则覆盖)
     * @param data 放入缓存的对象集合
     */
    public void setCaches(String key, Collection<?> data) {
        try {
            redisClient.sync(jedis -> {
                int seconds = Constant.CACHE_EXPIRE_TIME_MIN + new Random().nextInt(Constant.CACHE_EXPIRE_TIME_MAX - Constant.CACHE_EXPIRE_TIME_MIN);
                for (Object datum: data) {
                    jedis.sadd(key, SerializeUtil.serializeToString(datum));
                }
                jedis.expire(key, seconds);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据Redis缓存删除
     * @param key 即将被清除的缓存Key
     */
    public void delCache(String key) {
        try {
            redisClient.sync(jedis -> jedis.del(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
