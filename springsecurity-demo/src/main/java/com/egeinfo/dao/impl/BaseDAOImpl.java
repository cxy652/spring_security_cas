package com.egeinfo.dao.impl;

import com.egeinfo.dao.BaseDAO;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
public class BaseDAOImpl implements BaseDAO {

    private static final Logger log = Logger.getLogger(BaseDAOImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSession;

    public List selectList(String sqlName, Object obj){
        return this.sqlSession.selectList(sqlName);
    }

    public List selectList(String sqlName){
        return this.sqlSession.selectList(sqlName);
    }

    public Object selectOne(String sqlName, Object obj){
        return this.sqlSession.selectOne(sqlName, obj);
    }
}
