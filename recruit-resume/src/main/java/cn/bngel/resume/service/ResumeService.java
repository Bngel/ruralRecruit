package cn.bngel.resume.service;

import cn.bngel.pojo.Resume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResumeService {

    /**
     * 通过主键id获取简历状态信息
     * @param id 主键id
     * @return 简历状态信息
     */
    Resume getResume(@Param("id") Integer id);

    /**
     * 通过求职者手机号获取求职者所投出的所有简历状态信息
     * @param phone 求职者手机号
     * @return 求职者所投出的所有简历状态信息
     */
    List<Resume> listSentResumes(@Param("phone") String phone);

    /**
     * 通过雇主手机号获取雇主所收到的的所有简历状态信息
     * @param phone 雇主手机号
     * @return 雇主所收到的的所有简历状态信息
     */
    List<Resume> listReceivedResumes(@Param("phone") String phone);

    /**
     * 创建一个新的简历状态信息(有新的简历投递)
     * @param resume 简历状态信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveResume(@Param("resume") Resume resume);

    /**
     * 更新简历状态信息
     * @param resume 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateResume(@Param("resume") Resume resume);

    /**
     * 根据id删除对应简历信息(撤回简历投递)
     * @param id 主键id
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeResume(@Param("id") Integer id);

}
