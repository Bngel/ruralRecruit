package cn.bngel.applicant.controller;

import cn.bngel.pojo.WorkExperience;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.applicant.service.WorkExperienceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户模块 - 工作经历")
public class WorkExperienceController {

    /**
     * 根据主键id获取求职者工作经历
     * @param id 主键id
     * @return 工作经历
     */
    @ApiOperation(value = "WorkExperience - 获取求职者工作经历")
    @GetMapping("/workExperience")
    public CommonResult<?> getWorkExperience(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            WorkExperience workExperience = workExperienceService.getWorkExperience(id);
            if (workExperience == null) {
                workExperience = new WorkExperience();
                result = CommonResult.dataNotExist(workExperience);
            } else {
                result = CommonResult.success(workExperience);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new WorkExperience());
            return result;
        }
        return result;
    }

    /**
     * 获取求职者的所有工作经历
     * @param phone 求职者手机号
     * @return 求职者工作经历列表
     */
    @ApiOperation(value = "WorkExperience - 获取求职者的所有工作经历")
    @GetMapping("/workExperience/applicant")
    public CommonResult<?> listWorkExperienceByApplyStatus(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            List<WorkExperience> workExperience = workExperienceService.listWorkExperiences(phone);
            if (workExperience == null) {
                workExperience = new ArrayList<>();
                result = CommonResult.dataNotExist(workExperience);
            } else {
                result = CommonResult.success(workExperience);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 求职者创建新的工作经历
     * @param workExperience 新增的求职者工作经历
     * @return 成功则返回传入的求职者工作经历, 失败则返回空求职者工作经历
     */
    @ApiOperation(value = "WorkExperience - 新建求职者工作经历")
    @PostMapping("/workExperience")
    public CommonResult<?> saveWorkExperience(@RequestBody WorkExperience workExperience) {
        CommonResult<?> result;
        try {
            int save = workExperienceService.saveWorkExperience(workExperience);
            if (save == 0) {
                result = CommonResult.failure(new WorkExperience());
            } else {
                result = CommonResult.success(workExperience);
            }
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new WorkExperience());
            return result;
        }
        return result;
    }

    /**
     * 删除求职者工作经历
     * @param id 主键id
     * @return 成功则返回被删除的求职者工作经历, 失败则返回空求职者工作经历
     */
    @ApiOperation(value = "WorkExperience - 删除求职者工作经历")
    @DeleteMapping("/workExperience")
    public CommonResult<?> removeWorkExperience(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            WorkExperience removedWorkExperience = workExperienceService.getWorkExperience(id);
            if (removedWorkExperience == null) {
                result = CommonResult.dataNotExist(new WorkExperience());
                return result;
            }
            int save = workExperienceService.removeWorkExperience(id);
            if (save == 0) {
                result = CommonResult.failure(new WorkExperience());
            } else {
                result = CommonResult.success(removedWorkExperience);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new WorkExperience());
            return result;
        }
        return result;
    }

    /**
     * 根据传入的Body修改求职者信息, 不需要修改的数据置NULL,
     * 至少需要传入id字段作为WHERE搜索索引
     * @param workExperience 需要修改的求职者工作经历
     * @return 修改成功则返回修改后的数据, 否则返回空求职者工作经历
     */
    @ApiOperation(value = "WorkExperience - 修改求职者工作经历")
    @PutMapping("/workExperience")
    public CommonResult<?> updateWorkExperience(@RequestBody WorkExperience workExperience) {
        CommonResult<?> result;
        try {
            Integer id = workExperience.getId();
            if (id == null) {
                result = CommonResult.failure(new WorkExperience());
                return result;
            }
            int update = workExperienceService.updateWorkExperience(workExperience);
            if (update == 0) {
                result = CommonResult.failure(new WorkExperience());
            } else {
                WorkExperience updatedWorkExperience = workExperienceService.getWorkExperience(id);
                result = CommonResult.success(updatedWorkExperience);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new WorkExperience());
            return result;
        }
        return result;
    }

    @Autowired
    private WorkExperienceService workExperienceService;
}
