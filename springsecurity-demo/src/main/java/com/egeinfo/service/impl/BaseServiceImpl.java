package com.egeinfo.service.impl;

import java.util.Collection;
import java.util.List;

import com.egeinfo.dao.BaseDAO;
import com.egeinfo.service.BaseService;
import com.sun.webkit.WebPage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;



/**
 * 基础的业务处理层<br>
 * 实际使用中，一个操作不会只对一张表进行操作，基本都会记录日志<br>
 * 这样在controller的一个事务性的业务操作中不能调用多个service进行处理，而是应该在service中进行包装<br>
 * 这样才能保证事务的完整性，所以不建议直接使用BaseServiceImpl
 * 
 * @author Develop
 * 
 */
@Service("baseService")
@Scope("prototype")
public class BaseServiceImpl implements BaseService {
	protected final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected BaseDAO baseDAO;

	/**
	 * 查询列表数据
	 */
	@SuppressWarnings("rawtypes")
	public List selectList(String sqlName, Object obj) {
		List list = this.baseDAO.selectList(sqlName, obj);
		return list;
	}

	public List selectList(String sqlName) {
		return this.baseDAO.selectList(sqlName);
	}

	/**
	 * 查询一条数据
	 * 
	 * @param sqlName
	 * @param obj
	 * @return
	 */
	public Object selectOne(String sqlName, Object obj) {
		return this.baseDAO.selectOne(sqlName, obj);
	}

}
