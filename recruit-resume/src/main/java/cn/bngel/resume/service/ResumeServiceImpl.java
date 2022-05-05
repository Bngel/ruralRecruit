package cn.bngel.resume.service;

import cn.bngel.pojo.Resume;
import cn.bngel.pojo.Resume;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.Resume;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.resume.dao.ResumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class ResumeServiceImpl implements ResumeService{

    @Override
    public Resume getResume(Integer id) {
        if (id == null) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_RESUME + id;
        Resume cacheResume = (Resume) cacheClient.getCache(CACHE_KEY);
        if (cacheResume != null) {
            return cacheResume;
        }
        Resume resume = resumeDao.getResume(id);
        if (resume != null) {
            cacheClient.setCache(CACHE_KEY, resume);
        }
        return resume;
    }

    @Override
    public List<Resume> listSentResumes(String phone) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_RESUME_SENT + phone);
        if (origin != null) {
            List<Resume> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((Resume) obj);
            }
            return target;
        }
        List<Resume> resumes = resumeDao.listSentResumes(phone);
        if (resumes != null) {
            cacheClient.setCaches(Constant.CACHE_RESUME_SENT + phone, resumes);
        }
        return resumes;
    }

    @Override
    public List<Resume> listReceivedResumes(String phone) {
        List<Object> origin = cacheClient.listCaches(Constant.CACHE_RESUME_RECEIVED + phone);
        if (origin != null) {
            List<Resume> target = new ArrayList<>();
            for (Object obj: origin) {
                target.add((Resume) obj);
            }
            return target;
        }
        List<Resume> resumes = resumeDao.listReceivedResumes(phone);
        if (resumes != null) {
            cacheClient.setCaches(Constant.CACHE_RESUME_RECEIVED + phone, resumes);
        }
        return resumes;
    }

    @Override
    public Integer saveResume(Resume resume) {
        if (resume == null || resume.getAppPhone() == null || resume.getEmpPhone() == null) {
            return 0;
        }
        int save = resumeDao.saveResume(resume);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_RESUME + resume.getId();
            cacheClient.setCache(CACHE_KEY, resume);
        }
        return save;
    }

    @Override
    public Integer updateResume(Resume resume) {
        if (resume == null || resume.getId() == null) {
            return 0;
        }
        int update = resumeDao.updateResume(resume);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_RESUME + resume.getId();
            cacheClient.setCache(CACHE_KEY, resume);
        }
        return update;
    }

    @Override
    public Integer removeResume(Integer id) {
        if (id == null) {
            return 0;
        }
        int remove = resumeDao.removeResume(id);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_RESUME + id;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private CacheClient cacheClient;
}
