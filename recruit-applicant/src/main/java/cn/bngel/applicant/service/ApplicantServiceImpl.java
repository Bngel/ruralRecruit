package cn.bngel.applicant.service;

import cn.bngel.applicant.dao.ApplicantDao;
import cn.bngel.applicant.service.cache.CacheClient;
import cn.bngel.applicant.service.cache.CacheRedisClient;
import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Autowired
    private ApplicantDao applicantDao;

    @Autowired
    private CacheClient cacheClient;

    @Override
    public Applicant getApplicant(String phone) {
        if (phone == null) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_APPLICANT + phone;
        Applicant cacheApplicant = (Applicant) cacheClient.getCache(CACHE_KEY);
        if (cacheApplicant != null) {
            return cacheApplicant;
        }
        Applicant applicant = applicantDao.getApplicant(phone);
        if (applicant != null) {
            cacheClient.setCache(CACHE_KEY, applicant);
        }
        return applicant;
    }

    @Override
    public List<Applicant> listApplicantsByApplyStatus() {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_APPLICANT_APPLYING);
        if (origin != null) {
            List<Applicant> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((Applicant) obj);
            }
            return target;
        }
        List<Applicant> applicants = applicantDao.listApplicantsByApplyStatus();
        cacheClient.setCaches(Constant.CACHE_APPLICANT_APPLYING, applicants);
        return applicants;
    }

    @Override
    public Integer saveApplicant(Applicant applicant) {
        if (applicant == null || applicant.getPhone() == null) {
            return 0;
        }
        int save = applicantDao.saveApplicant(applicant);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_APPLICANT + applicant.getPhone();
            cacheClient.setCache(CACHE_KEY, applicant);
        }
        return save;
    }

    @Override
    public Integer updateApplicant(Applicant applicant) {
        if (applicant == null || applicant.getPhone() == null) {
            return 0;
        }
        int update = applicantDao.updateApplicant(applicant);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_APPLICANT + applicant.getPhone();
            cacheClient.setCache(CACHE_KEY, applicant);
        }
        return update;
    }

    @Override
    public Integer removeApplicant(String phone) {
        if (phone == null) {
            return 0;
        }
        int remove = applicantDao.removeApplicant(phone);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_APPLICANT + phone;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

}
