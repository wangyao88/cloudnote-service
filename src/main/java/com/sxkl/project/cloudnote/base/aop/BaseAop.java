package com.sxkl.project.cloudnote.base.aop;

import com.sxkl.project.cloudnote.base.entity.OperateResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BaseAop {

    @Around("@annotation(operation)")
    public Object aroundMethod(ProceedingJoinPoint pjp, Operation operation){
        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable throwable) {
            return OperateResult.buildFail(throwable);
        }
        return object;
    }

    @Around("@annotation(showSql)")
    public Object aroundMethod(ProceedingJoinPoint pjp, ShowSql showSql){
        Object object = null;
        try {
            object = pjp.proceed();
            log.info("sql: {}", object.toString());
            for(Object arg : pjp.getArgs()) {
                log.info("arg: {}", arg.toString());
            }
        } catch (Throwable throwable) {
            return OperateResult.buildFail(throwable);
        }
        return object;
    }
}
