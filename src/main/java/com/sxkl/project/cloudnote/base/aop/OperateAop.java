package com.sxkl.project.cloudnote.base.aop;

import com.sxkl.project.cloudnote.base.entity.OperateResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperateAop {

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
}
