package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.CounterDao;
import com.baizhi.hyh.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;

    @Override
    public Counter selectById(String id) {
        return counterDao.selectByPrimaryKey(id);
    }

    @Override
    public void insertCounter(Counter counter) {
        counterDao.insertSelective(counter);
    }

    @Override
    public void deleteCounter(String id) {
        counterDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateCountById(String id, String count) {
        counterDao.updateCountById(id, count);
    }
}
