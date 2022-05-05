package cn.bngel.job.service;

import cn.bngel.job.dao.JobDao;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.Job;
import cn.bngel.redis.cache.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{


    @Override
    public Job getJob(Integer id) {
        if (id == null || id < 0) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_JOB + id;
        Job cacheJob = (Job) cacheClient.getCache(CACHE_KEY);
        if (cacheJob != null) {
            return cacheJob;
        }
        Job job = jobDao.getJob(id);
        if (job != null) {
            cacheClient.setCache(CACHE_KEY, job);
        }
        return job;
    }

    @Override
    public List<Job> listJobs(String phone) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_JOB_EMPLOYER + phone);
        if (origin != null) {
            List<Job> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((Job) obj);
            }
            return target;
        }
        List<Job> jobs = jobDao.listJobs(phone);
        if (jobs != null) {
            cacheClient.setCaches(Constant.CACHE_JOB_EMPLOYER + phone, jobs);
        }
        return jobs;
    }

    @Override
    public Integer saveJob(Job job) {
        if (job == null || job.getPhone() == null) {
            return 0;
        }
        int save = jobDao.saveJob(job);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_JOB + job.getId();
            cacheClient.setCache(CACHE_KEY, job);
        }
        return save;
    }

    @Override
    public Integer updateJob(Job job) {
        if (job == null || job.getPhone() == null || job.getId() == null) {
            return 0;
        }
        int update = jobDao.updateJob(job);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_JOB + job.getId();
            cacheClient.setCache(CACHE_KEY, job);
        }
        return update;
    }

    @Override
    public Integer removeJob(Integer id) {
        if (id == null) {
            return 0;
        }
        int remove = jobDao.removeJob(id);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_JOB + id;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

    @Autowired
    private JobDao jobDao;

    @Autowired
    private CacheClient cacheClient;
}
