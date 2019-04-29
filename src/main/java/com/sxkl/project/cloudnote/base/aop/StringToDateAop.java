package com.sxkl.project.cloudnote.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class StringToDateAop {

    @Before("@annotation(convert)")
    public void beforeMethod(JoinPoint point, StringToDateConvert convert){
        //获取方法名
        String mathName = point.getSignature().getName();
        //获取参数列表
        List<Object> args = Arrays.asList(point.getArgs());
        System.out.println(mathName);
    }
}
