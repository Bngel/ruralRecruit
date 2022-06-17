package cn.bngel.interceptor;

import cn.bngel.openfeign.logger.LoggerFeignClient;
import cn.bngel.redis.token.TokenClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerInterceptor extends InterceptorTemplate {

    @Override
    protected boolean tokenEvent(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    protected LoggerInterceptor(TokenClient tokenClient) {
        this.loggerSwitch = true;
        this.tokenClient = tokenClient;
        this.logger = LoggerFeignClient.build();
    }
}
