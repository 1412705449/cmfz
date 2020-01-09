package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.LogDao;
import com.baizhi.hyh.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    LogDao logDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Log> selectAll() {
        return logDao.selectAll();
    }

    @Override
    public void insert(Log log) {
        logDao.insert(log);
    }
}
