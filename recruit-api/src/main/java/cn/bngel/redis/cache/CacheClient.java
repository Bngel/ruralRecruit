package cn.bngel.redis.cache;

import java.util.Collection;
import java.util.List;

/**
 * 将MySQL中的数据在Redis中进行缓存
 */
public interface CacheClient {

    /**
     * 根据key从Redis中获取缓存数据
     * @param key 需要获取的数据的key
     * @return 反序列化后得到的Object数据
     */
    Object getCache(String key);

    /**
     * 根据一组数据的key列表得到一组缓存数据
     * @param keys 数据key的集合
     * @return 缓存数据列表
     */
    List<Object> listCaches(Collection<String> keys);

    /**
     * 通过redis中一个集合的key得到这个集合的列表数据
     * @param key redis集合的key
     * @return 缓存数据列表
     */
    List<Object> listCaches(String key);

    /**
     * 根据 k-v 键值对缓存数据
     * @param key 缓存数据存入的key
     * @param obj 缓存的数据
     */
    void setCache(String key, Object obj) ;

    /**
     * 删除缓存数据
     * @param key 删除的缓存数据的key
     */
    void delCache(String key);

    /**
     * 对一个数据集合进行缓存,存入redis的集合中
     * @param key redis集合的key
     * @param data 缓存数据集合
     */
    void setCaches(String key, Collection<?> data);
}
