package cn.bngel.interceptor;

import cn.bngel.pojo.CommonResult;
import cn.bngel.pojo.Constant;
import cn.bngel.redis.token.TokenClient;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

class ApplicantTokenInterceptor extends InterceptorTemplate {

    private TokenClient tokenClient;

    protected ApplicantTokenInterceptor(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    @Override
    protected boolean preHandleEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_APPLICANT);
        return token != null && tokenClient.verifyToken(token, jsonObject);
    }

    private ApplicantTokenInterceptor() {}

}