package cn.bngel.interceptor;

import cn.bngel.redis.token.TokenClient;
import org.springframework.web.servlet.HandlerInterceptor;

public class InterceptorFactory {

    /**
     * 返回雇主对应的token鉴权拦截器
     * @param tokenClient 进行鉴权需要的TokenClient
     * @return 雇员对应的token鉴权拦截器
     */
    public static HandlerInterceptor createEmployerTokenInterceptor(TokenClient tokenClient){
        return new EmployerTokenInterceptor(tokenClient);
    }

    /**
     * 返回求职者对应的token鉴权拦截器
     * @param tokenClient 进行鉴权需要的TokenClient
     * @return 求职者对应的token鉴权拦截器
     */
    public static HandlerInterceptor createApplicantTokenInterceptor(TokenClient tokenClient){
        return new ApplicantTokenInterceptor(tokenClient);
    }

    /**
     * 私有化工厂类的构造方法，不允许进行实例化
     */
    private InterceptorFactory() {}
}
