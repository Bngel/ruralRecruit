package cn.bngel.interceptor;

import cn.bngel.openfeign.logger.LoggerFeignClient;
import cn.bngel.openfeign.logger.LoggerService;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.bngel.redis.token.TokenClient;
import cn.bngel.util.InterceptorUtil;
import cn.bngel.util.SerializeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
abstract class InterceptorTemplate implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (loggerSwitch) {
                loggerEvent(request, response, handler);
            }
            if (tokenSwitch) {
                String json = InterceptorUtil.DEFAULT_AUTH_ERROR_JSON;
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                if (!tokenEvent(request, response, handler)) {
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.print(json);
                    outputStream.close();
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected TokenClient tokenClient;

    protected LoggerService logger;

    /**
     * 各个功能模块是否开启
     */

    protected boolean tokenSwitch = false;
    protected boolean loggerSwitch = false;

    /**
     * 模板方法的通用方法
     */

    protected abstract boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler);

    protected void loggerEvent(HttpServletRequest request, HttpServletResponse response, Object handler){
        RLog rLog = getLogFromRequest(request);
        log.info(rLog.toString());
        try {
            CommonResult<?> result = logger.saveLog(rLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户访问接口带有的request进行相关日志信息的获取
     * @param request 用户请求接口的request信息
     * @return 封装完成的日志信息实例
     */
    private RLog getLogFromRequest(HttpServletRequest request){
        RLog log = new RLog();
        log.setId(0);
        log.setMethod(request.getMethod());
        String ip = request.getRemoteAddr();
        log.setIp(ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip);
        log.setUrl(request.getRequestURI());
        log.setTimestamp(new Date().getTime());
        //log.setBody(SerializeUtil.serializeToString(request.getParameterMap()));
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                String decryptedToken = tokenClient.getDecryptedToken(token);
                JSONObject jsonObject = JSONUtil.toBean(decryptedToken, JSONObject.class);
                log.setPhone((String) jsonObject.get(Constant.TOKEN_PARAM_PHONE));
                log.setUserType((Integer) jsonObject.get(Constant.TOKEN_PARAM_LOGIN_TYPE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return log;
    }
}
