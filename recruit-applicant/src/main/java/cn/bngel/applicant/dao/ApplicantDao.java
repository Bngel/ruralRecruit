package cn.bngel.applicant.dao;

import cn.bngel.pojo.Applicant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface ApplicantDao {

    /**
     * 通过求职者手机号获取求职者详细信息
     * @param phone 求职者手机号
     * @return 求职者详细信息
     */
    Applicant getApplicant(@Param("phone") String phone);

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
    Integer saveApplicant(@Param("applicant") Applicant applicant);

    /**
     * 更新求职者详细信息
     * @param applicant 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateApplicant(@Param("applicant") Applicant applicant);

    /**
     * 根据手机号删除求职者信息
     * @param phone 求职者手机号
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeApplicant(@Param("phone") String phone);

}
