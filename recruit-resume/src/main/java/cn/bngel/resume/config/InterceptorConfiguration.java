package cn.bngel.resume.config;

import cn.bngel.resume.interceptor.ApplicantTokenInterceptor;
import cn.bngel.resume.interceptor.EmployerTokenInterceptor;
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
                .addPathPatterns("/resume/applicant/**");
        registry.addInterceptor(employerTokenInterceptor())
                .addPathPatterns("/resume/employer/**");
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
