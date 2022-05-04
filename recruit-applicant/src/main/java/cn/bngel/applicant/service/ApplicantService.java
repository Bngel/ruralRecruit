package cn.bngel.applicant.service;

import cn.bngel.pojo.Applicant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApplicantService {

    /**
     * 通过求职者手机号获取求职者详细信息
     * @param phone 求职者手机号
     * @return 求职者详细信息
     */
    Applicant getApplicant(String phone);

    /**
     * 通过求职者求职状态返回求职者列表(雇主首页能够获取到的数据)
     * @return 正在求职的求职者列表
     */
    List<Applicant> listApplicantsByApplyStatus();

    /**
     * 创建一个新的求职者用户
     * @param applicant 求职者用户信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveApplicant(Applicant applicant);

    /**
     * 更新求职者详细信息
     * @param applicant 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateApplicant(Applicant applicant);

    /**
     * 根据手机号删除求职者信息
     * @param phone 求职者手机号
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeApplicant(String phone);

    /**
     * 用户使用手机号以及手机验证码进行登录
     * @param phone 手机号
     * @param code 手机验证码
     * @return 求职者详细信息
     */
    Applicant login(String phone, String code);

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
