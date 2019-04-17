package com.egeinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

public interface BaseDAO {
	public List selectList(String sqlName, Object obj);

	public List selectList(String sqlName);

	public Object selectOne(String sqlName, Object obj);
}
