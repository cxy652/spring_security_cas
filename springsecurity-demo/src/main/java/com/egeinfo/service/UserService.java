package com.egeinfo.service;

import java.util.List;

public interface UserService extends  BaseService{
    Object selectOne(String sqlName, Object obj);
}
