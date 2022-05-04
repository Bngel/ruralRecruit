package cn.bngel.applicant.service;

import cn.bngel.applicant.dao.ApplicantDao;
import cn.bngel.redis.SimpleRedisClient;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.Constant;
import cn.bngel.util.TencentCloudClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 功能的详细说明见接口Interface文件
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {

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
        // 通过身份证号解析出信息进行填充
        String idCard = applicant.getIdCard();
        if (idCard != null) {
            applicant = parseIdCard(applicant);
            if (applicant == null) {
                return 0;
            }
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

    @Override
    public Applicant login(String phone, String code) {
        if (phone == null || code == null) {
            return null;
        }
        final String LOGIN_CODE_KEY = Constant.CACHE_APPLICANT_LOGIN + phone;
        String cacheCode = redisClient.get(LOGIN_CODE_KEY);
        Applicant applicant = null;
        if (code.equals(cacheCode)) {
            applicant = getApplicant(phone);
            if (applicant == null) {
                Applicant newApplicant = new Applicant();
                newApplicant.setPhone(phone);
                newApplicant.setNickName(phone);
                int register = saveApplicant(newApplicant);
                if (register == 0) {
                    return null;
                }
                applicant = newApplicant;
            }
            redisClient.del(LOGIN_CODE_KEY);
        }
        return applicant;
    }

    @Override
    public String sendLoginCode(String phone) {
        if (phone == null){
            return null;
        }
        // 创建一个六位数的短信验证码
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        try {
            String cacheCode = redisClient.setex(Constant.CACHE_APPLICANT_LOGIN + phone,
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
    public String uploadProfile(String phone, MultipartFile profile) throws IOException{
        String bucketName = "recruit-profile";
        String profileUrl = tencentCloudClient.uploadFile(profile, bucketName, phone + "/profile.png");
        if (profileUrl == null)
            return null;
        Applicant applicant = getApplicant(phone);
        if (applicant == null)
            return null;
        applicant.setProfile(profileUrl);
        Applicant updateApplicant = new Applicant();
        updateApplicant.setPhone(applicant.getPhone());
        updateApplicant.setProfile(profileUrl);
        Integer saveUser = updateApplicant(updateApplicant);
        if (saveUser == 1)
            return profileUrl;
        else
            return null;
    }

    @Autowired
    private ApplicantDao applicantDao;

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private SimpleRedisClient redisClient;

    @Autowired
    private TencentCloudClient tencentCloudClient;

    private Applicant parseIdCard(Applicant applicant) {
        String idCard = applicant.getIdCard();
        if (idCard != null && idCard.length() == 18) {
            try {
                String yearStr = idCard.substring(6, 10);
                String monthStr = idCard.substring(10, 12);
                String dayStr = idCard.substring(12, 14);
                String birthdayStr = yearStr + "-" + monthStr + "-" + dayStr;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday = dateFormat.parse(birthdayStr);
                applicant.setBirthday(birthday.getTime());
                int gender = (idCard.charAt(16) - '0') % 2;
                applicant.setGender(gender);
                String now = dateFormat.format(new Date());
                String[] timeArr = now.split("-");
                if (timeArr.length != 3) {
                    return null;
                }
                int yearNow = Integer.parseInt(timeArr[0]);
                int monthNow = Integer.parseInt(timeArr[1]);
                int dayNow = Integer.parseInt(timeArr[2]);
                int age = yearNow - Integer.parseInt(yearStr);
                if (monthNow < Integer.parseInt(monthStr) ||
                        (monthNow == Integer.parseInt(monthStr) && dayNow < Integer.parseInt(dayStr))) {
                    age -= 1;
                }
                applicant.setAge(age);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return applicant;
    }
}
