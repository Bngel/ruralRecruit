package cn.bngel.applicant.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAdvice {

    @Pointcut("execution (* cn.bngel.applicant.controller.*.*(..))")
    public void cache() {}

}
