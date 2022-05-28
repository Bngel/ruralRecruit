package cn.bngel.interceptor;

import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.pojo.RLog;
import cn.bngel.redis.token.TokenClient;
import cn.bngel.util.InterceptorUtil;
import cn.bngel.util.SerializeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

class EmployerTokenInterceptor extends InterceptorTemplate {

    protected EmployerTokenInterceptor(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    @Override
    protected boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_EMPLOYER);
        return token != null && tokenClient.verifyToken(token, jsonObject);
    }

    private EmployerTokenInterceptor() {}

}
