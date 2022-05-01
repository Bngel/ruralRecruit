package cn.bngel.applicant.controller;

import cn.bngel.applicant.service.ApplicantService;
import cn.bngel.pojo.Applicant;
import cn.bngel.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "用户模块 - 求职者")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    /**
     * 根据手机号获取求职者详细信息
     * @param phone 求职者手机号
     * @return 求职者详细信息
     */
    @ApiOperation(value = "Applicant - 获取求职者信息")
    @GetMapping("/applicant")
    public CommonResult<?> getApplicant(@RequestParam("phone") String phone) {
        CommonResult<?> result;
        try {
            Applicant applicant = applicantService.getApplicant(phone);
            if (applicant == null) {
                applicant = new Applicant();
                result = CommonResult.dataNotExist(applicant);
            } else {
                result = CommonResult.success(applicant);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new Applicant());
            return result;
        }
        return result;
    }
}
