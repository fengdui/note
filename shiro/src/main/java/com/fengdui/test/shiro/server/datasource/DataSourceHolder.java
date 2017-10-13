package com.fengdui.test.shiro.server.datasource;

/**
 * Created by fd on 2016/10/18.
 */
public class DataSourceHolder {
    public static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    public static void setDataSource(String dataSourceName) {
        dataSources.set(dataSourceName);
    }

    public static String getDataSource() {
        return (String)dataSources.get();
    }

    public static void clear() {
        dataSources.remove();
    }
}
