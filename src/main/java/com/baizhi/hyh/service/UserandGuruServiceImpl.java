package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.UserandGuruDao;
import com.baizhi.hyh.entity.Userandguru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserandGuruServiceImpl implements UserandGuruService {
    @Autowired
    UserandGuruDao userandGuruDao;

    @Override
    public void insert(Userandguru userandGuru) {
        userandGuruDao.insert(userandGuru);
    }
}
