package com.egeinfo.base.datasource;

public class DynamicDBContextHolder {

	// 当前的数据源
	private static final ThreadLocal<String> curDataSource = new ThreadLocal<String>();

	public static void setCurDataSource(String dataSource) {
		curDataSource.set(dataSource);
	}

	public static String getCurDataSource() {
		return curDataSource.get();
	}

	public static void clearCurDataSource() {
		curDataSource.remove();
	}
}
