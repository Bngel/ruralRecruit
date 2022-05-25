package cn.bngel.interceptor;

import cn.bngel.pojo.Constant;
import cn.bngel.util.InterceptorUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

abstract class InterceptorTemplate implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String json = InterceptorUtil.DEFAULT_AUTH_ERROR_JSON;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.append(json);
            writer.close();
            return preHandleEvent(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected abstract boolean preHandleEvent(HttpServletRequest request, HttpServletResponse response, Object handler);

}
