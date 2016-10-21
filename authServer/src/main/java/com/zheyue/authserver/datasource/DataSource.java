package com.zheyue.authserver.datasource;

import java.lang.annotation.*;

/**
 * Created by fd on 2016/10/18.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSource.master;
    public static String master = "BasicDataSource";
    public static String slave = "BasicDataSource2";
}
