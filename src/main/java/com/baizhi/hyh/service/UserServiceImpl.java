package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.UserDao;
import com.baizhi.hyh.entity.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map selectByPage(Integer page, Integer rows) {
        HashMap map = new HashMap();
        /*添加总条数*/
        int records = userDao.selectCount(null);
        map.put("records", records);
        /*添加总页数*/
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        /*添加当前页数*/
        map.put("page", page);
        List<User> albums = userDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows", albums);
        return map;
    }

    @Override
    public void updateStatus(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectOne(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectBySexAndDay(String sex, Integer day) {
        return userDao.selectBySexAndDay(sex, day);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapDto> selectBySexAndLocation(String sex) {
        return userDao.selectBySexAndLocation(sex);
    }

    @Override
    public void addUser(User user) {
        userDao.insertSelective(user);
    }

    /*前台/*/

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(String phone, String password) {
        return userDao.login(phone, password);
    }

    @Override
    public void information(User user) {
        userDao.information(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> selectBanner() {
        return userDao.selectBanner();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> selectAlbum(String uid) {
        return userDao.selectAlbum(uid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> selectArticle(String uid) {
        return userDao.selectArticle(uid);
    }

    @Override
    public void updateUserInformation(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectRandom(String id) {
        return userDao.selectRandom(id);
    }
}
