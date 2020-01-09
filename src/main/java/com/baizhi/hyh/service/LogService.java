package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Log;

import java.util.List;

public interface LogService {
    /*查所有*/
    public List<Log> selectAll();

    /*添加*/
    public void insert(Log log);
}
