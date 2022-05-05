package cn.bngel.employer.config;

import cn.bngel.employer.interceptor.ApplicantTokenInterceptor;
import cn.bngel.employer.interceptor.EmployerTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public EmployerTokenInterceptor employerTokenInterceptor() {
        return new EmployerTokenInterceptor();
    }

    @Bean
    public ApplicantTokenInterceptor applicantTokenInterceptor() {
        return new ApplicantTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(applicantTokenInterceptor())
                .addPathPatterns("/employer/applicant/**");
        registry.addInterceptor(employerTokenInterceptor())
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
