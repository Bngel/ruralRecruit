package cn.bngel.applicant.service;

import cn.bngel.pojo.WorkExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkExperienceService {

    /**
     * 通过工作经历主键id获取求职者具体工作经历
     * @param id 主键id
     * @return 工作经历详细信息
     */
    WorkExperience getWorkExperience(Integer id);

    /**
     * 通过求职者手机号返回该求职者工作经历列表
     * @return 求职者工作经历列表
     */
    List<WorkExperience> listWorkExperiences(String phone);

    /**
     * 创建一个新的工作经历
     * @param workExperience 求职者工作经历
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveWorkExperience(WorkExperience workExperience);

    /**
     * 更新求职者工作经历
     * @param workExperience 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateWorkExperience(WorkExperience workExperience);

    /**
     * 根据主键id删除求职者工作经历
     * @param id 主键id
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeWorkExperience(Integer id);
}
