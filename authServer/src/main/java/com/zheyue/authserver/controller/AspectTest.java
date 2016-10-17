package com.zheyue.authserver.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.omg.CORBA.*;

import java.lang.Object;

/**
 * Created by fd on 2016/10/10.
 */

@Aspect
public class AspectTest {

    @Pointcut("execution(* *.test(..))")
    public void test() {

    }
    @Before("test()")
    public void beforeTest() {
        System.out.println("beforeTest");
    }

    @After("test()")
    public void afterTest() {
        System.out.println("afterTest");
    }


    @Around("test()")
    public Object arountTest(ProceedingJoinPoint p) {
        System.out.println("before1");
        Object o = null;
        try {
            o = p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("before2");
        return 0;
    }
}
