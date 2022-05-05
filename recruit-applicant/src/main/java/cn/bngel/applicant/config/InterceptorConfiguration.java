package cn.bngel.applicant.config;

import cn.bngel.applicant.interceptor.ApplicantTokenInterceptor;
import cn.bngel.applicant.interceptor.EmployerTokenInterceptor;
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
                .addPathPatterns("/applicant/applicant/**")
                .addPathPatterns("/workExperience/applicant/**")
                .excludePathPatterns("/applicant/login/**");
        registry.addInterceptor(employerTokenInterceptor())
                .addPathPatterns("/applicant/employer/**")
                .addPathPatterns("/workExperience/employer/**");
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
