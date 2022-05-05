package cn.bngel.resume.controller;

import cn.bngel.pojo.Resume;
import cn.bngel.pojo.CommonResult;
import cn.bngel.redis.cache.CacheClient;
import cn.bngel.resume.service.ResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "招聘模块 - 简历投递状态")
public class ResumeController {

    /**
     * 根据主键id获取简历状态信息
     * @param id 主键id
     * @return 简历状态信息
     */
    @ApiOperation(value = "Resume - 获取简历状态信息")
    @GetMapping("/resume/applicant")
    public CommonResult<?> getResume(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            Resume resume = resumeService.getResume(id);
            if (resume == null) {
                resume = new Resume();
                result = CommonResult.dataNotExist(resume);
            } else {
                result = CommonResult.success(resume);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Resume());
            return result;
        }
        return result;
    }

    /**
     * 根据求职者手机号获取投递的简历信息
     * @param phone 求职者手机号
     * @return 简历状态信息列表
     */
    @ApiOperation(value = "Resume - 获取投递的简历状态信息列表")
    @GetMapping("/resume/applicant/sent")
    public CommonResult<?> listSentResumes(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            List<Resume> resume = resumeService.listSentResumes(phone);
            if (resume == null) {
                resume = new ArrayList<>();
                result = CommonResult.dataNotExist(resume);
            } else {
                result = CommonResult.success(resume);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 根据雇主手机号获取收到的简历信息
     * @param phone 雇主手机号
     * @return 简历状态信息列表
     */
    @ApiOperation(value = "Resume - 获取收到的简历状态信息列表")
    @GetMapping("/resume/employer/received")
    public CommonResult<?> listReceivedResumes(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            List<Resume> resume = resumeService.listReceivedResumes(phone);
            if (resume == null) {
                resume = new ArrayList<>();
                result = CommonResult.dataNotExist(resume);
            } else {
                result = CommonResult.success(resume);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 求职者向雇主投递简历
     * @param resume 求职者简历状态信息
     * @return 成功则返回传入的简历状态信息, 失败则返回空简历状态信息
     */
    @ApiOperation(value = "Resume - 新建简历状态信息")
    @PostMapping("/resume/applicant")
    public CommonResult<?> saveResume(@RequestBody Resume resume) {
        CommonResult<?> result;
        try {
            int save = resumeService.saveResume(resume);
            if (save == 0) {
                result = CommonResult.failure(new Resume());
            } else {
                result = CommonResult.success(resume);
            }
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Resume());
            return result;
        }
        return result;
    }

    /**
     * 求职者撤回投递的简历
     * @param id 主键id
     * @return 成功则返回被删除的的简历状态信息, 失败则返回空简历状态信息
     */
    @ApiOperation(value = "Resume - 删除简历状态信息(撤回简历)")
    @DeleteMapping("/resume/applicant")
    public CommonResult<?> removeResume(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            Resume removedResume = resumeService.getResume(id);
            if (removedResume == null) {
                result = CommonResult.dataNotExist(new Resume());
                return result;
            }
            int save = resumeService.removeResume(id);
            if (save == 0) {
                result = CommonResult.failure(new Resume());
            } else {
                result = CommonResult.success(removedResume);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Resume());
            return result;
        }
        return result;
    }

    /**
     * 根据传入的Body修改求职者简历状态信息, 不需要修改的数据置NULL,
     * 至少需要传入id字段作为WHERE搜索索引
     * @param resume 需要修改的简历状态信息(做标记)
     * @return 修改成功则返回修改后的数据, 否则返回空求职者简历状态信息
     */
    @ApiOperation(value = "Resume - 修改简历状态信息(打标记)")
    @PutMapping("/resume/employer")
    public CommonResult<?> updateResume(@RequestBody Resume resume) {
        CommonResult<?> result;
        try {
            Integer id = resume.getId();
            if (id == null) {
                result = CommonResult.failure(new Resume());
                return result;
            }
            int update = resumeService.updateResume(resume);
            if (update == 0) {
                result = CommonResult.failure(new Resume());
            } else {
                Resume updatedResume = resumeService.getResume(id);
                result = CommonResult.success(updatedResume);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Resume());
            return result;
        }
        return result;
    }


    @Autowired
    private ResumeService resumeService;

    @Autowired
    private CacheClient cacheClient;
}
