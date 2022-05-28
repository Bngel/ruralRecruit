package cn.bngel.logger.controller;

import cn.bngel.logger.service.LoggerService;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.RLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "日志模块")
public class LoggerController {

    /**
     * 通过日志的id来获取日志信息(大概用的会比较少)
     * @param id 日志id
     * @return 日志
     */
    @ApiOperation(value = "Log - 查询日志 By ID")
    @GetMapping("/log/admin")
    CommonResult<?> getLog(@RequestParam("id") Integer id) {
        CommonResult<?> result;
        try {
            RLog log = loggerService.getLog(id);
            if (log == null) {
                log = new RLog();
                result = CommonResult.dataNotExist(log);
            } else {
                result = CommonResult.success(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new RLog());
            return result;
        }
        return result;
    }

    /**
     * 通过用户的主键id(手机号)获取所有日志信息
     * @param phone 用户手机号
     * @return 日志列表
     */
    @ApiOperation(value = "Log - 查询日志 By 手机号")
    @GetMapping("/log/admin/phone")
    CommonResult<?> listLogsByPhone(@RequestParam("phone") String phone){
        CommonResult<?> result;
        try {
            List<RLog> logs = loggerService.listLogsByPhone(phone);
            if (logs == null) {
                logs = new ArrayList<>();
                result = CommonResult.dataNotExist(logs);
            } else {
                result = CommonResult.success(logs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 通过时间段来确定日志列表返回
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 该时间段内的日志列表
     */
    @ApiOperation(value = "Log - 查询日志 By 时间戳")
    @GetMapping("/log/admin/timestamp")
    CommonResult<?> listLogsByTimestamp(@RequestParam("startTime") Long startTime,
                                   @RequestParam("endTime") Long endTime){
        CommonResult<?> result;
        try {
            List<RLog> logs = loggerService.listLogsByTimestamp(startTime, endTime);
            if (logs == null) {
                logs = new ArrayList<>();
                result = CommonResult.dataNotExist(logs);
            } else {
                result = CommonResult.success(logs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 通过某一特定ip获取该ip的所有访问
     * @param ip 用户访问ip地址
     * @return 根据ip地址锁定的ip信息
     */
    @ApiOperation(value = "Log - 查询日志 By IP")
    @GetMapping("/log/admin/ip")
    CommonResult<?> listLogsByIp(@RequestParam("ip") String ip){
        CommonResult<?> result;
        try {
            List<RLog> logs = loggerService.listLogsByIp(ip);
            if (logs == null) {
                logs = new ArrayList<>();
                result = CommonResult.dataNotExist(logs);
            } else {
                result = CommonResult.success(logs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 保存日志信息
     * @param rLog 需要进行保存的日志信息
     * @return 成功返回1, 失败则返回0
     */
    @ApiOperation(value = "Log - 创建日志")
    @PostMapping("/log/admin")
    CommonResult<?> saveLog(@RequestBody RLog rLog){
        CommonResult<?> result;
        try {
            int save = loggerService.saveLog(rLog);
            if (save == 0) {
                result = CommonResult.failure(new RLog());
            } else {
                result = CommonResult.success(rLog);
            }
        } catch(Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new RLog());
            return result;
        }
        return result;
    }

    /**
     * 通过日志id删除指定日志信息
     * @param id 日志主键id
     * @return 成功返回1, 失败则返回0
     */
    @ApiOperation(value = "Log - 删除日志 By ID")
    @DeleteMapping("/log/admin")
    CommonResult<?> removeLog(@RequestParam("id") Integer id){
        CommonResult<?> result;
        try {
            RLog removedLog = loggerService.getLog(id);
            if (removedLog == null) {
                result = CommonResult.dataNotExist(new RLog());
                return result;
            }
            int save = loggerService.removeLog(id);
            if (save == 0) {
                result = CommonResult.failure(new RLog());
            } else {
                result = CommonResult.success(removedLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new RLog());
            return result;
        }
        return result;
    }

    /**
     * 通过起止时间删除指定时间段内的日志信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 成功删除的日志条数
     */
    @ApiOperation(value = "Log - 删除日志 By 时间戳")
    @DeleteMapping("/log/admin/timestamp")
    CommonResult<?> removeLogsByTimestamp(@RequestParam("startTime") Long startTime,
                                  @RequestParam("endTime") Long endTime){
        CommonResult<?> result;
        try {
            List<RLog> removedLogs = loggerService.listLogsByTimestamp(startTime, endTime);
            if (removedLogs == null) {
                result = CommonResult.dataNotExist(new ArrayList<>());
                return result;
            }
            int remove = loggerService.removeLogsByTimestamp(startTime, endTime);
            if (remove == 0) {
                result = CommonResult.failure(new ArrayList<>());
            } else {
                result = CommonResult.success(removedLogs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new ArrayList<>());
            return result;
        }
        return result;
    }

    /**
     * 更新某一条具体的日志信息
     * @param log 修改的日志信息
     * @return 成功返回1, 失败返回0
     */
    @ApiOperation(value = "Log - 修改日志")
    @PutMapping("/log/admin")
    CommonResult<?> updateLog(@RequestBody RLog log){
        CommonResult<?> result;
        try {
            Integer id = log.getId();
            int update = loggerService.updateLog(log);
            if (update == 0) {
                result = CommonResult.failure(new RLog());
            } else {
                RLog updatedLog = loggerService.getLog(id);
                result = CommonResult.success(updatedLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CommonResult.failure(new RLog());
            return result;
        }
        return result;
    }

    @Autowired
    private LoggerService loggerService;
}
