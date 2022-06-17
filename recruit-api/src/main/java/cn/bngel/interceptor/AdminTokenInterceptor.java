package cn.bngel.interceptor;

import cn.bngel.pojo.Constant;
import cn.bngel.redis.token.TokenClient;
import cn.hutool.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class AdminTokenInterceptor extends InterceptorTemplate {

    protected AdminTokenInterceptor(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
        this.tokenSwitch = true;
    }

    @Override
    protected boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        JSONObject jsonObject = new JSONObject();
        jsonObject.set(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_ADMIN);
        return token != null && tokenClient.verifyToken(token, jsonObject);
    }

    @Override
    protected void loggerEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
    }

    private TokenClient tokenClient;

    private AdminTokenInterceptor(){}


}
