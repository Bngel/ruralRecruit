package cn.bngel.applicant.controller;

import cn.bngel.applicant.service.ApplicantService;
import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.Employer;
import cn.bngel.redis.token.TokenClient;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户模块 - 求职者")
public class ApplicantController {

    /**
     * 根据手机号获取求职者详细信息
     * @param phone 求职者手机号
     * @return 求职者详细信息
     */
    @ApiOperation(value = "Applicant - 获取求职者信息")
    @GetMapping("/applicant")
    public CommonResult<?> getApplicant(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            Applicant applicant = applicantService.getApplicant(phone);
            if (applicant == null) {
                applicant = new Applicant();
                result = CommonResult.dataNotExist(applicant);
            } else {
                result = CommonResult.success(applicant);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }

    /**
     * 根据求职状态获取求职中的求职者信息
     * @return 求职者列表
     */
    @ApiOperation(value = "Applicant - 获取正在求职的求职者列表")
    @GetMapping("/applicant/applying")
    public CommonResult<?> listApplicantByApplyStatus() {
        CommonResult<?> result;
        try {
            List<Applicant> applicant = applicantService.listApplicantsByApplyStatus();
            if (applicant == null) {
                applicant = new ArrayList<>();
                result = CommonResult.dataNotExist(applicant);
            } else {
                result = CommonResult.success(applicant);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 求职者注册账号
     * @param applicant 新增的求职者账号信息
     * @return 成功则返回传入的求职者信息, 失败则返回空求职者信息
     */
    @ApiOperation(value = "Applicant - 注册求职者")
    @PostMapping("/applicant")
    public CommonResult<?> saveApplicant(@RequestBody Applicant applicant) {
        CommonResult<?> result;
        try {
            int save = applicantService.saveApplicant(applicant);
            if (save == 0) {
                result = CommonResult.failure(new Applicant());
            } else {
                result = CommonResult.success(applicant);
            }
        } catch (DuplicateKeyException duplicateKeyException){
            result = new CommonResult<>(
                    Constant.RESULT_CODE_USER_EXISTED,
                    Constant.RESULT_MSG_USER_EXISTED,
                    new Applicant()
            );
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }

    /**
     * 根据手机号注销求职者账号
     * @param phone 手机号
     * @return 成功则返回被注销的求职者信息, 失败则返回空求职者信息
     */
    @ApiOperation(value = "Applicant - 注销求职者")
    @DeleteMapping("/applicant")
    public CommonResult<?> removeApplicant(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            Applicant removedApplicant = applicantService.getApplicant(phone);
            if (removedApplicant == null) {
                result = CommonResult.dataNotExist(new Applicant());
                return result;
            }
            int save = applicantService.removeApplicant(phone);
            if (save == 0) {
                result = CommonResult.failure(new Applicant());
            } else {
                result = CommonResult.success(removedApplicant);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }

    /**
     * 根据传入的Body修改求职者信息, 不需要修改的数据置NULL,
     * 至少需要传入phone字段作为WHERE搜索索引
     * @param applicant 需要修改的求职者信息
     * @return 修改成功则返回修改后的数据, 否则返回空求职者信息
     */
    @ApiOperation(value = "Applicant - 修改求职者信息")
    @PutMapping("/applicant")
    public CommonResult<?> updateApplicant(@RequestBody Applicant applicant) {
        CommonResult<?> result;
        try {
            String phone = applicant.getPhone();
            if (phone == null) {
                result = CommonResult.failure(new Applicant());
                return result;
            }
            int update = applicantService.updateApplicant(applicant);
            if (update == 0) {
                result = CommonResult.failure(new Applicant());
            } else {
                Applicant updatedApplicant = applicantService.getApplicant(phone);
                result = CommonResult.success(updatedApplicant);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }

    /**
     * 求职者通过手机号与短信验证码进行登录
     * @param phone 手机号
     * @param code 短信验证码
     * @return 求职者详细信息
     */
    @ApiOperation(value = "Applicant - 求职者登录")
    @GetMapping("/applicant/login")
    public CommonResult<?> login(@RequestParam("phone") String phone,
                                 @RequestParam("code") String code) {
        CommonResult<?> result;
        try {
            Applicant applicant = applicantService.login(phone, code);
            if (applicant == null) {
                result = CommonResult.failure(new Applicant());
                return result;
            }
            result = CommonResult.success(applicant);
            String token = getToken(getTokenMap(applicant));
            result.setMessage(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }

    /**
     * 向指定的手机号发送短信验证码
     * @param phone 手机号
     * @return 成功则返回指定的短信验证码, 失败则返回空字符串
     */
    @ApiOperation(value = "Applicant - 发送短信验证码")
    @GetMapping("/applicant/login/code")
    public CommonResult<?> sendCode(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            String code = applicantService.sendLoginCode(phone);
            if (code == null) {
                result = CommonResult.failure("");
                return result;
            }
            result = CommonResult.success(code);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure("");
            return result;
        }
        return result;
    }

    /**
     * 上传求职者的头像
     * @param profile 头像文件
     * @return 成功上传则返回头像URL, 否则返回空串
     */
    @ApiOperation(value = "Applicant - 上传头像")
    @PostMapping("/applicant/upload/profile")
    public CommonResult<?> uploadProfile(@RequestParam("phone") String phone,
                                         @RequestPart MultipartFile profile) {
        CommonResult<?> result;
        try {
            String profileUrl = applicantService.uploadProfile(phone, profile);
            if (profileUrl == null) {
                result = CommonResult.failure("");
                return result;
            }
            result = CommonResult.success(profileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure("");
            return result;
        }
        return result;
    }

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private TokenClient tokenClient;

    private Map<String,Object> getTokenMap(Applicant applicant){
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_PARAM_ID, Constant.LOGIN_TYPE_APPLICANT + "-" + applicant.getPhone());
        map.put(Constant.TOKEN_PARAM_PHONE, applicant.getPhone());
        map.put(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_APPLICANT);
        return map;
    }

    private String getToken(Map<String, Object> map) {
        JSONObject json = new JSONObject();
        String id = (String) map.get(Constant.TOKEN_PARAM_ID);
        if (id == null) {
            return null;
        }
        for (String key: map.keySet()) {
            json.set(key, map.get(key));
        }
        return tokenClient.refreshToken(id, json.toString());
    }

}
