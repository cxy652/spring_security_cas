package com.egeinfo.base.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 确定数据源的key
	 */
	protected Object determineCurrentLookupKey() {
		String lookupKey = DynamicDBContextHolder.getCurDataSource();
		DynamicDBContextHolder.clearCurDataSource();
		return lookupKey;
	}
}
