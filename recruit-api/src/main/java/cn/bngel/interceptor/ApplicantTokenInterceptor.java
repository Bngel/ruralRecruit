package cn.bngel.interceptor;

import cn.bngel.openfeign.logger.LoggerFeignClient;
import cn.bngel.openfeign.logger.LoggerService;
import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.bngel.redis.token.TokenClient;
import cn.bngel.util.SerializeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

class ApplicantTokenInterceptor extends InterceptorTemplate {

    protected ApplicantTokenInterceptor(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
        this.tokenSwitch = true;
    }
    @Override
    protected boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_APPLICANT);
        try {
            return token != null && tokenClient.verifyToken(token, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private TokenClient tokenClient;

    private ApplicantTokenInterceptor() {}

}