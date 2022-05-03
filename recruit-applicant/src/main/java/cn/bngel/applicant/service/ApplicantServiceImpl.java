package cn.bngel.applicant.service;

import cn.bngel.applicant.dao.ApplicantDao;
import cn.bngel.applicant.service.cache.CacheClient;
import cn.bngel.applicant.service.cache.CacheRedisClient;
import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class ApplicantServiceImpl implements ApplicantService{

    @Autowired
    private ApplicantDao applicantDao;

    @Autowired
    private CacheClient cacheClient;

    @Override
    public Applicant getApplicant(String phone) {
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
        return applicantDao.listApplicantsByApplyStatus();
    }

    @Override
    public Integer saveApplicant(Applicant applicant) {
        return applicantDao.saveApplicant(applicant);
    }

    @Override
    public Integer updateApplicant(Applicant applicant) {
        return applicantDao.updateApplicant(applicant);
    }

    @Override
    public Integer removeApplicant(String phone) {
        return applicantDao.removeApplicant(phone);
    }

}
