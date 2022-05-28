package cn.bngel.logger.service;

import cn.bngel.logger.dao.LoggerDao;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.bngel.redis.cache.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Override
    public RLog getLog(Integer id) {
        if (id == null || id < 0) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_LOG + id;
        RLog cacheLog = (RLog) cacheClient.getCache(CACHE_KEY);
        if (cacheLog != null) {
            return cacheLog;
        }
        RLog log = logDao.getLog(id);
        if (log != null) {
            cacheClient.setCache(CACHE_KEY, log);
        }
        return log;
    }

    @Override
    public List<RLog> listLogsByPhone(String phone) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_LOG_PHONE + phone);
        if (origin != null) {
            List<RLog> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((RLog) obj);
            }
            return target;
        }
        List<RLog> logs = logDao.listLogsByPhone(phone);
        if (logs != null) {
            cacheClient.setCaches(Constant.CACHE_LOG_PHONE + phone, logs);
        }
        return logs;
    }

    @Override
    public List<RLog> listLogsByTimestamp(Long startTime, Long endTime) {
        // TODO 时间戳日志的缓存手段待定
        return logDao.listLogsByTimestamp(startTime, endTime);
    }

    @Override
    public List<RLog> listLogsByIp(String ip) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_LOG_IP + ip);
        if (origin != null) {
            List<RLog> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((RLog) obj);
            }
            return target;
        }
        List<RLog> logs = logDao.listLogsByPhone(ip);
        if (logs != null) {
            cacheClient.setCaches(Constant.CACHE_LOG_PHONE + ip, logs);
        }
        return logs;
    }

    @Override
    public Integer saveLog(RLog rLog) {
        if (rLog == null) {
            return 0;
        }
        int save = logDao.saveLog(rLog);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_LOG + rLog.getId();
            cacheClient.setCache(CACHE_KEY, rLog);
        }
        return save;
    }

    @Override
    public Integer removeLog(Integer id) {
        if (id == null) {
            return 0;
        }
        int remove = logDao.removeLog(id);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_LOG + id;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

    @Override
    public Integer removeLogsByTimestamp(Long startTime, Long endTime) {
        // TODO 时间戳日志的缓存手段待定
        return logDao.removeLogsByTimestamp(startTime, endTime);
    }

    @Override
    public Integer updateLog(RLog log) {
        if (log == null || log.getId() == null) {
            return 0;
        }
        int update = logDao.updateLog(log);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_LOG + log.getId();
            cacheClient.setCache(CACHE_KEY, log);
        }
        return update;
    }

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private LoggerDao logDao;
}
