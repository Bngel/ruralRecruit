package cn.bngel.interceptor;

import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.bngel.redis.token.TokenClient;
import cn.bngel.util.InterceptorUtil;
import cn.bngel.util.SerializeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

    /**
     * 各个功能模块是否开启
     */

    protected boolean tokenSwitch = true;
    protected boolean loggerSwitch = true;

    /**
     * 模板方法的通用方法
     */

    protected abstract boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler);

    protected void loggerEvent(HttpServletRequest request, HttpServletResponse response, Object handler){
        RLog log = getLogFromRequest(request);
        try {
            // TODO 上传日志信息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RLog getLogFromRequest(HttpServletRequest request){
        RLog log = new RLog();
        log.setMethod(request.getMethod());
        log.setIp(request.getRemoteAddr());
        log.setUrl(request.getRequestURI());
        log.setTimestamp(new Date().getTime());
        log.setBody(SerializeUtil.serializeToString(request.getParameterMap()));
        String token = request.getHeader("Authorization");
        String decryptedToken = tokenClient.getDecryptedToken(token);
        JSONObject jsonObject = JSONUtil.toBean(decryptedToken, JSONObject.class);
        log.setPhone((String) jsonObject.get(Constant.TOKEN_PARAM_PHONE));
        log.setUserType((Integer) jsonObject.get(Constant.TOKEN_PARAM_LOGIN_TYPE));
        return log;
    }
}
