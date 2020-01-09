package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.AdminDao;
import com.baizhi.hyh.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin loginAdmin(Admin admin) {
        return adminDao.selectOne(admin);
    }
}
