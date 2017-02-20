package com.fengdui.oa.framework.orm;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Wander.Zeng
 * @data 2015-6-16 下午4:00:10
 * @desc MultiDataSource.java
 */
public class MultiDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	public static void clearDataSourceKey() {
		dataSourceKey.remove();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}

}
