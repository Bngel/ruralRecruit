package cn.bngel.employer.dao;

import cn.bngel.pojo.Employer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployerDao {

    /**
     * 通过雇主手机号获取雇主详细信息
     * @param phone 雇主手机号
     * @return 雇主详细信息
     */
    Employer getEmployer(@Param("phone") String phone);

    /**
     * 创建一个新的雇主用户
     * @param employer 雇主用户信息
     * @return 成功则返回 1 失败则返回 0
     */
    Integer saveEmployer(@Param("employer") Employer employer);

    /**
     * 更新雇主详细信息
     * @param employer 用于修改的数据(不需要修改的部分留null)
     * @return 成功则返回 1 失败则返回 0
     */
    Integer updateEmployer(@Param("employer") Employer employer);

    /**
     * 根据手机号删除雇主信息
     * @param phone 雇主手机号
     * @return 成功则返回 1 失败则返回 0
     */
    Integer removeEmployer(@Param("phone") String phone);
}
