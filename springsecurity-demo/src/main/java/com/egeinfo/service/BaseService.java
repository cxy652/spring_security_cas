package com.egeinfo.service;

import com.sun.webkit.WebPage;

import java.util.Collection;
import java.util.List;


public interface BaseService {
	

	/**
	 * 查询列表数据
	 */
	@SuppressWarnings("rawtypes")
	public List selectList(String sqlName, Object obj);
	
	/**
	 * 查询列表数据
	 */
	@SuppressWarnings("rawtypes")
	public List selectList(String sqlName);
	/**
	 * 查询一条数据
	 * @param sqlName
	 * @param obj
	 * @return
	 */
	public Object selectOne(String sqlName, Object obj);
}
