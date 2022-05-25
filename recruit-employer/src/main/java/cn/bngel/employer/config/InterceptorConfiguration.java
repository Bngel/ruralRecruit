package cn.bngel.employer.config;

import cn.bngel.interceptor.InterceptorFactory;
import cn.bngel.redis.token.TokenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenClient tokenClient;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(InterceptorFactory.createEmployerTokenInterceptor(tokenClient))
                .addPathPatterns("/employer/applicant/**");
        registry.addInterceptor(InterceptorFactory.createApplicantTokenInterceptor(tokenClient))
                .addPathPatterns("/employer/employer/**")
                .excludePathPatterns("/employer/login/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").
                addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").
                addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
