package cn.bngel.employer.controller;

import cn.bngel.employer.service.EmployerService;
import cn.bngel.pojo.Employer;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
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
@Api(tags = "用户模块 - 雇主")
public class EmployerController {

    /**
     * 根据手机号获取雇主详细信息
     * @param phone 雇主手机号
     * @return 雇主详细信息
     */
    @ApiOperation(value = "Employer - 获取雇主信息")
    @GetMapping("/employer/employer")
    public CommonResult<?> getEmployer(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            Employer employer = employerService.getEmployer(phone);
            if (employer == null) {
                employer = new Employer();
                result = CommonResult.dataNotExist(employer);
            } else {
                result = CommonResult.success(employer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Employer());
            return result;
        }
        return result;
    }

    /**
     * 雇主注册账号
     * @param employer 新增的雇主账号信息
     * @return 成功则返回传入的雇主信息, 失败则返回空雇主信息
     */
    @ApiOperation(value = "Employer - 注册雇主")
    @PostMapping("/employer/employer")
    public CommonResult<?> saveEmployer(@RequestBody Employer employer) {
        CommonResult<?> result;
        try {
            int save = employerService.saveEmployer(employer);
            if (save == 0) {
                result = CommonResult.failure(new Employer());
            } else {
                result = CommonResult.success(employer);
            }
        } catch (DuplicateKeyException duplicateKeyException){
            result = new CommonResult<>(
                    Constant.RESULT_CODE_USER_EXISTED,
                    Constant.RESULT_MSG_USER_EXISTED,
                    new Employer()
            );
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Employer());
            return result;
        }
        return result;
    }

    /**
     * 根据手机号注销雇主账号
     * @param phone 手机号
     * @return 成功则返回被注销的雇主信息, 失败则返回空雇主信息
     */
    @ApiOperation(value = "Employer - 注销雇主")
    @DeleteMapping("/employer/employer")
    public CommonResult<?> removeEmployer(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            Employer removedEmployer = employerService.getEmployer(phone);
            if (removedEmployer == null) {
                result = CommonResult.dataNotExist(new Employer());
                return result;
            }
            int save = employerService.removeEmployer(phone);
            if (save == 0) {
                result = CommonResult.failure(new Employer());
            } else {
                result = CommonResult.success(removedEmployer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Employer());
            return result;
        }
        return result;
    }

    /**
     * 根据传入的Body修改雇主信息, 不需要修改的数据置NULL,
     * 至少需要传入phone字段作为WHERE搜索索引
     * @param employer 需要修改的雇主信息
     * @return 修改成功则返回修改后的数据, 否则返回空雇主信息
     */
    @ApiOperation(value = "Employer - 修改雇主信息")
    @PutMapping("/employer/employer")
    public CommonResult<?> updateEmployer(@RequestBody Employer employer) {
        CommonResult<?> result;
        try {
            String phone = employer.getPhone();
            if (phone == null) {
                result = CommonResult.failure(new Employer());
                return result;
            }
            int update = employerService.updateEmployer(employer);
            if (update == 0) {
                result = CommonResult.failure(new Employer());
            } else {
                Employer updatedEmployer = employerService.getEmployer(phone);
                result = CommonResult.success(updatedEmployer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Employer());
            return result;
        }
        return result;
    }

    /**
     * 雇主通过手机号与短信验证码进行登录
     * @param phone 手机号
     * @param code 短信验证码
     * @return 雇主详细信息
     */
    @ApiOperation(value = "Employer - 雇主登录")
    @GetMapping("/employer/login")
    public CommonResult<?> login(@RequestParam("phone") String phone,
                                 @RequestParam("code") String code) {
        CommonResult<?> result;
        try {
            Employer employer = employerService.login(phone, code);
            if (employer == null) {
                result = CommonResult.failure(new Employer());
                return result;
            }
            result = CommonResult.success(employer);
            String token = getToken(getTokenMap(employer));
            result.setMessage(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Employer());
            return result;
        }
        return result;
    }

    /**
     * 向指定的手机号发送短信验证码
     * @param phone 手机号
     * @return 成功则返回指定的短信验证码, 失败则返回空字符串
     */
    @ApiOperation(value = "Employer - 发送短信验证码")
    @GetMapping("/employer/login/code")
    public CommonResult<?> sendCode(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            String code = employerService.sendLoginCode(phone);
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
     * 上传雇主的头像
     * @param profile 头像文件
     * @return 成功上传则返回头像URL, 否则返回空串
     */
    @ApiOperation(value = "Employer - 上传头像")
    @PostMapping("/employer/employer/upload/profile")
    public CommonResult<?> uploadProfile(@RequestParam("phone") String phone,
                                         @RequestPart MultipartFile profile) {
        CommonResult<?> result;
        try {
            String profileUrl = employerService.uploadProfile(phone, profile);
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
    private EmployerService employerService;

    @Autowired
    private TokenClient tokenClient;

    private Map<String,Object> getTokenMap(Employer employer){
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.TOKEN_PARAM_ID, Constant.LOGIN_TYPE_EMPLOYER + "-" + employer.getPhone());
        map.put(Constant.TOKEN_PARAM_PHONE, employer.getPhone());
        map.put(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_EMPLOYER);
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
