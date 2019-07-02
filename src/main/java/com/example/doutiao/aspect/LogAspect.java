package com.example.doutiao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wz on 2019/5/10.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.example.doutiao.contorller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb= new StringBuilder();
        for(Object arg : joinPoint.getArgs()){
            sb.append("arg".toString()+arg.toString()+"|");
        }

        logger.info("before method:"+sb.toString());

    }

    @After("execution(* com.example.doutiao.contorller.*Controller.*(..))")
    public void afterMethod() {
        logger.info("after method:");
    }
}
