package com.fengdui.shiro.server.datasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by fd on 2016/10/18.
 */
public class DataSourceExchange implements MethodInterceptor {
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getAnnotation(DataSource.class));
        System.out.println(invocation.getMethod().getDeclaredAnnotation(DataSource.class));
        String dataSourceName = invocation.getMethod().getAnnotation(DataSource.class).name();
        DataSourceHolder.setDataSource(dataSourceName);
        System.out.println("使用数据源"+dataSourceName);
//        try {
//            invocation.proceed();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }
        invocation.proceed();
        return null;
    }
}
