package cn.bngel.redis.cache;

import java.util.Collection;
import java.util.List;

public interface CacheClient {

    Object getCache(String key);

    List<Object> listCaches(Collection<String> keys);

    List<Object> listCaches(String key);

    void setCache(String key, Object obj) ;

    void delCache(String key);

    void setCaches(String key, Collection<?> data);
}
