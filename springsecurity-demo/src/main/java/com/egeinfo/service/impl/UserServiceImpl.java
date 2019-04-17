package com.egeinfo.service.impl;

import com.egeinfo.service.BaseService;
import com.egeinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService  {
    @Autowired
    private BaseService baseService;

    /**
     * 查询一条数据
     *
     * @param sqlName
     * @param obj
     * @return
     */
    public Object selectOne(String sqlName, Object obj) {
        return baseService.selectOne(sqlName, obj);
    }
}
