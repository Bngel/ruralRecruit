package cn.bngel.employer.interceptor;

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

public class EmployerTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenClient tokenClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String json = JSONUtil.toJsonStr(CommonResult.authError());
        try {
            String token = request.getHeader("Authorization");
            JSONObject jsonObject = new JSONObject();
            jsonObject.set(Constant.TOKEN_PARAM_LOGIN_TYPE, Constant.LOGIN_TYPE_EMPLOYER);
            if (token == null || !tokenClient.verifyToken(token, jsonObject)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.append(json);
                writer.close();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.append(json);
            writer.close();
            return false;
        }
    }

}
