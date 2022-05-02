package cn.bngel.applicant.service;

import cn.bngel.applicant.dao.ApplicantDao;
import cn.bngel.pojo.Applicant;
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

    @Override
    public Applicant getApplicant(String phone) {
        return applicantDao.getApplicant(phone);
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
