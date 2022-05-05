package cn.bngel.employer.service;

import cn.bngel.employer.dao.EmployerDao;
import cn.bngel.pojo.*;
import cn.bngel.pojo.Employer;
import cn.bngel.pojo.Employer;
import cn.bngel.redis.SimpleRedisClient;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.util.TencentCloudClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class EmployerServiceImpl implements EmployerService{

    @Override
    public Employer getEmployer(String phone) {
        if (phone == null) {
            return null;
        }
        final String CACHE_KEY = Constant.CACHE_EMPLOYER + phone;
        Employer cacheEmployer = (Employer) cacheClient.getCache(CACHE_KEY);
        if (cacheEmployer != null) {
            return cacheEmployer;
        }
        Employer employer = employerDao.getEmployer(phone);
        if (employer != null) {
            cacheClient.setCache(CACHE_KEY, employer);
        }
        return employer;
    }

    @Override
    public Integer saveEmployer(Employer employer) {
        if (employer == null || employer.getPhone() == null) {
            return 0;
        }
        int save = employerDao.saveEmployer(employer);
        if (save == 1) {
            final String CACHE_KEY = Constant.CACHE_EMPLOYER + employer.getPhone();
            cacheClient.setCache(CACHE_KEY, employer);
        }
        return save;
    }

    @Override
    public Integer updateEmployer(Employer employer) {
        if (employer == null || employer.getPhone() == null) {
            return 0;
        }
        int update = employerDao.updateEmployer(employer);
        if (update == 1) {
            final String CACHE_KEY = Constant.CACHE_EMPLOYER + employer.getPhone();
            cacheClient.setCache(CACHE_KEY, employer);
        }
        return update;
    }

    @Override
    public Integer removeEmployer(String phone) {
        if (phone == null) {
            return 0;
        }
        int remove = employerDao.removeEmployer(phone);
        if (remove == 1) {
            final String CACHE_KEY = Constant.CACHE_EMPLOYER + phone;
            cacheClient.delCache(CACHE_KEY);
        }
        return remove;
    }

    @Override
    public Employer login(String phone, String code) {
        if (phone == null || code == null) {
            return null;
        }
        final String LOGIN_CODE_KEY = Constant.CACHE_EMPLOYER_LOGIN + phone;
        String cacheCode = redisClient.get(LOGIN_CODE_KEY);
        Employer employer = null;
        if (code.equals(cacheCode)) {
            employer = getEmployer(phone);
            if (employer == null) {
                Employer newEmployer = new Employer();
                newEmployer.setPhone(phone);
                int register = saveEmployer(newEmployer);
                if (register == 0) {
                    return null;
                }
                employer = newEmployer;
            }
            redisClient.del(LOGIN_CODE_KEY);
        }
        return employer;
    }

    @Override
    public String sendLoginCode(String phone) {
        if (phone == null){
            return null;
        }
        // 创建一个六位数的短信验证码
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        try {
            String cacheCode = redisClient.setex(Constant.CACHE_EMPLOYER_LOGIN + phone,
                    Constant.CACHE_EXPIRE_LOGIN_CODE, code);
            if (cacheCode == null) {
                return null;
            }
            String smsCode = tencentCloudClient.sendMessage("+86", phone, code);
            if (!"OK".equals(smsCode)) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return code;
    }

    @Override
    public String uploadProfile(String phone, MultipartFile profile) throws IOException {
        String bucketName = "recruit-profile";
        String profileUrl = tencentCloudClient.uploadFile(profile, bucketName, phone + "/1-profile.png");
        if (profileUrl == null)
            return null;
        Employer employer = getEmployer(phone);
        if (employer == null)
            return null;
        employer.setProfile(profileUrl);
        Employer updateEmployer = new Employer();
        updateEmployer.setPhone(employer.getPhone());
        updateEmployer.setProfile(profileUrl);
        Integer saveUser = updateEmployer(updateEmployer);
        if (saveUser == 1)
            return profileUrl;
        else
            return null;
    }

    @Autowired
    private EmployerDao employerDao;

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private SimpleRedisClient redisClient;

    @Autowired
    private TencentCloudClient tencentCloudClient;
}
