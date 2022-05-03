package cn.bngel.applicant.service.cache;

import java.util.List;

public interface CacheClient {

    Object getCache(String key);

    List<Object> listCaches(List<String> keys);

    void setCache(String key, Object obj) ;
}
