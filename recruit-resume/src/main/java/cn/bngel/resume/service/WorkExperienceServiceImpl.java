package cn.bngel.resume.service;

import cn.bngel.pojo.Constant;
import cn.bngel.pojo.WorkExperience;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.resume.dao.WorkExperienceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService{

    @Override
    public WorkExperience getWorkExperience(Integer id) {
        if (id == null) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_WORK_EXPERIENCE + id;
        WorkExperience cacheWorkExperience = (WorkExperience) cacheClient.getCache(CACHE_KEY);
        if (cacheWorkExperience != null) {
            return cacheWorkExperience;
        }
        WorkExperience workExperience = workExperienceDao.getWorkExperience(id);
        if (workExperience != null) {
            cacheClient.setCache(CACHE_KEY, workExperience);
        }
        return workExperience;
    }

    @Override
    public List<WorkExperience> listWorkExperiences(String phone) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_WORK_EXPERIENCE_APPLICANT + phone);
        if (origin != null) {
            ArrayList<WorkExperience> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((WorkExperience) obj);
            }
            return target;
        }
        List<WorkExperience> workExperiences = workExperienceDao.listWorkExperiences(phone);
        if (workExperiences != null) {
            cacheClient.setCaches(Constant.CACHE_WORK_EXPERIENCE_APPLICANT + phone, workExperiences);
        }
        return workExperiences;
    }

    @Override
    public Integer saveWorkExperience(WorkExperience workExperience) {
        if (workExperience == null || workExperience.getAppPhone() == null) {
            return 0;
        }
        int save = workExperienceDao.saveWorkExperience(workExperience);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_WORK_EXPERIENCE + workExperience.getId();
            cacheClient.setCache(CACHE_KEY, workExperience);
        }
        return save;
    }

    @Override
    public Integer updateWorkExperience(WorkExperience workExperience) {
        if (workExperience == null || workExperience.getAppPhone() == null || workExperience.getId() == null) {
            return 0;
        }
        int update = workExperienceDao.updateWorkExperience(workExperience);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_WORK_EXPERIENCE + workExperience.getId();
            cacheClient.setCache(CACHE_KEY, workExperience);
        }
        return update;
    }

    @Override
    public Integer removeWorkExperience(Integer id) {
        if (id == null) {
            return 0;
        }
        int remove = workExperienceDao.removeWorkExperience(id);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_WORK_EXPERIENCE + id;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

    @Autowired
    private WorkExperienceDao workExperienceDao;

    @Autowired
    private CacheClient cacheClient;
}
