package cn.bngel.job.service;

import cn.bngel.pojo.Job;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobService {

    /**
     * 通过职位id获取职位信息
     * @param id 职位id信息
     * @return 职位具体信息
     */
    Job getJob(@Param("id") Integer id);

    /**
     * 通过雇主手机号获取所有其发布的职位详细信息
     * @param phone 雇主手机号
     * @return 职位详细信息
     */
    List<Job> listJobs(@Param("phone") String phone);

    /**
     * 发布一个新的职位信息
     * @param job 职位信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveJob(@Param("job") Job job);

    /**
     * 更新职位详细信息
     * @param job 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateJob(@Param("job") Job job);

    /**
     * 根据id删除职位信息
     * @param id 职位id信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeJob(@Param("id") Integer id);
}
