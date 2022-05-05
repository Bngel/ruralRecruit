package cn.bngel.employer.service;

import cn.bngel.pojo.Employer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployerService {

    /**
     * 通过雇主手机号获取雇主详细信息
     * @param phone 雇主手机号
     * @return 雇主详细信息
     */
    Employer getEmployer(String phone);

    /**
     * 创建一个新的雇主用户
     * @param employer 雇主用户信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveEmployer(Employer employer);

    /**
     * 更新雇主详细信息
     * @param employer 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateEmployer(Employer employer);

    /**
     * 根据手机号删除雇主信息
     * @param phone 雇主手机号
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeEmployer(String phone);

    /**
     * 用户使用手机号以及手机验证码进行登录
     * @param phone 手机号
     * @param code 手机验证码
     * @return 雇主详细信息
     */
    Employer login(String phone, String code);

    /**
     * 向指定手机号发送短信验证码
     * @param phone 收短信的手机号
     * @return 发送的验证码
     */
    String sendLoginCode(String phone);

    /**
     * 上传头像
     * @param phone 手机号
     * @param profile 上传的头像文件
     * @return 头像的URL
     */
    String uploadProfile(String phone, MultipartFile profile) throws IOException;
}
