package cn.bngel.job.controller;

import cn.bngel.job.service.JobService;
import cn.bngel.pojo.Job;
import cn.bngel.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "招聘模块 - 职位信息")
public class JobController {


    /**
     * 根据id获取职位详细信息
     * @param id 职位id
     * @return 职位详细信息
     */
    @ApiOperation(value = "Job - 获取职位信息")
    @GetMapping("/applicant/job")
    public CommonResult<?> getJob(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            Job job = jobService.getJob(id);
            if (job == null) {
                job = new Job();
                result = CommonResult.dataNotExist(job);
            } else {
                result = CommonResult.success(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Job());
            return result;
        }
        return result;
    }

    /**
     * 根据雇主手机号获取职位信息
     * @return 职位列表
     */
    @ApiOperation(value = "Job - 获取特定雇主发布的职位列表")
    @GetMapping("/applicant/job/employer")
    public CommonResult<?> listJobs(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            List<Job> job = jobService.listJobs(phone);
            if (job == null) {
                job = new ArrayList<>();
                result = CommonResult.dataNotExist(job);
            } else {
                result = CommonResult.success(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 职位注册账号
     * @param job 新增的职位信息
     * @return 成功则返回传入的职位信息, 失败则返回空职位信息
     */
    @ApiOperation(value = "Job - 发布职位信息")
    @PostMapping("/employer/job")
    public CommonResult<?> saveJob(@RequestBody Job job) {
        CommonResult<?> result;
        try {
            int save = jobService.saveJob(job);
            if (save == 0) {
                result = CommonResult.failure(new Job());
            } else {
                result = CommonResult.success(job);
            }
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Job());
            return result;
        }
        return result;
    }

    /**
     * 根据id删除职位信息
     * @param id 职位id
     * @return 成功则返回被删除的职位信息, 失败则返回空职位信息
     */
    @ApiOperation(value = "Job - 注销职位")
    @DeleteMapping("/employer/job")
    public CommonResult<?> removeJob(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            Job removedJob = jobService.getJob(id);
            if (removedJob == null) {
                result = CommonResult.dataNotExist(new Job());
                return result;
            }
            int save = jobService.removeJob(id);
            if (save == 0) {
                result = CommonResult.failure(new Job());
            } else {
                result = CommonResult.success(removedJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Job());
            return result;
        }
        return result;
    }

    /**
     * 根据传入的Body修改职位信息, 不需要修改的数据置NULL,
     * 至少需要传入phone字段作为WHERE搜索索引
     * @param job 需要修改的职位信息
     * @return 修改成功则返回修改后的数据, 否则返回空职位信息
     */
    @ApiOperation(value = "Job - 修改职位信息")
    @PutMapping("/employer/job")
    public CommonResult<?> updateJob(@RequestBody Job job) {
        CommonResult<?> result;
        try {
            Integer id = job.getId();
            int update = jobService.updateJob(job);
            if (update == 0) {
                result = CommonResult.failure(new Job());
            } else {
                Job updatedJob = jobService.getJob(id);
                result = CommonResult.success(updatedJob);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Job());
            return result;
        }
        return result;
    }


    @Autowired
    private JobService jobService;
}
