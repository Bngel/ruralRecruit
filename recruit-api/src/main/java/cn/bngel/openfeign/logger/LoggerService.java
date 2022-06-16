package cn.bngel.openfeign.logger;

import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.RLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

public interface LoggerService {

    /**
     * 通过日志的id来获取日志信息(大概用的会比较少)
     * @param id 日志id
     * @return 日志
     */
    @GetMapping("/log/admin")
    CommonResult<?> getLog(@RequestParam("id") Integer id);

    /**
     * 通过用户的主键id(手机号)获取所有日志信息
     * @param phone 用户手机号
     * @return 日志列表
     */
    @GetMapping("/log/admin/phone")
    CommonResult<?> listLogsByPhone(@RequestParam("phone") String phone);

    /**
     * 通过时间段来确定日志列表返回
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 该时间段内的日志列表
     */
    @GetMapping("/log/admin/timestamp")
    CommonResult<?> listLogsByTimestamp(@RequestParam("startTime") Long startTime,
                                        @RequestParam("endTime") Long endTime);

    /**
     * 通过某一特定ip获取该ip的所有访问
     * @param ip 用户访问ip地址
     * @return 根据ip地址锁定的ip信息
     */
    @GetMapping("/log/admin/ip")
    CommonResult<?> listLogsByIp(@RequestParam("ip") String ip);

    /**
     * 保存日志信息
     * @param rLog 需要进行保存的日志信息
     * @return 成功返回1, 失败则返回0
     */
    @PostMapping(value = "/log/admin", consumes = {"application/json"})
    CommonResult<?> saveLog(@RequestBody RLog rLog);

    /**
     * 通过日志id删除指定日志信息
     * @param id 日志主键id
     * @return 成功返回1, 失败则返回0
     */
    @DeleteMapping("/log/admin")
    CommonResult<?> removeLog(@RequestParam("id") Integer id);

    /**
     * 通过起止时间删除指定时间段内的日志信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 成功删除的日志条数
     */
    @DeleteMapping("/log/admin/timestamp")
    CommonResult<?> removeLogsByTimestamp(@RequestParam("startTime") Long startTime,
                                          @RequestParam("endTime") Long endTime);

    /**
     * 更新某一条具体的日志信息
     * @param log 修改的日志信息
     * @return 成功返回1, 失败返回0
     */
    @PutMapping(value = "/log/admin", consumes = {"application/json"})
    CommonResult<?> updateLog(@RequestBody RLog log);
}
