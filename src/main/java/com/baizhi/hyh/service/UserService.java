package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.*;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查所有
    public Map selectByPage(Integer page, Integer rows);

    //修改
    public void updateStatus(User user);

    /*查一个*/
    public User selectOne(String id);

    /*统计*/
    public Integer selectBySexAndDay(String sex, Integer day);

    /*统计*/
    public List<MapDto> selectBySexAndLocation(String sex);

    /*添加*/
    public void addUser(User user);


    /*登陆*/
    public User login(String phone, String password);

    /*补充个人信息*/
    public void information(User user);

    /*一级页面*/
    /*查5条轮播图*/
    public List<Banner> selectBanner();

    /*查询专辑*/
    public List<Album> selectAlbum(String uid);

    /*查询文章*/
    public List<Article> selectArticle(String uid);

    /*修改个人信息*/
    public void updateUserInformation(User user);

    //随机展示
    public List<User> selectRandom(String id);
}
