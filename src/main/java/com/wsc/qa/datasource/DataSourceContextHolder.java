package com.wsc.qa.datasource;

/**
 * @ClassName: DataSourceContextHolder
 * @Description: 数据库切换工具类
 * @author: libiao
 * @date: 2015-12-14 下午5:56:27
 */
public class DataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDbType() {
		return ((String) contextHolder.get());
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
