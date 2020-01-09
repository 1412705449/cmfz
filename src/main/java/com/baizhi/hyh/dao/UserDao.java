package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    /*统计*/
    public Integer selectBySexAndDay(@Param("sex") String sex, @Param("day") Integer day);

    /*统计*/
    public List<MapDto> selectBySexAndLocation(@Param("sex") String sex);


    /*前台*/
    /*登陆*/
    public User login(@Param("phone") String phone, @Param("password") String password);

    /*补充个人信息*/
    public void information(User user);

    /*一级页面*/
    /*查5条轮播图*/
    public List<Banner> selectBanner();

    /*查询专辑*/
    public List<Album> selectAlbum(String uid);

    /*查询文章*/
    public List<Article> selectArticle(String uid);

    //随机展示
    public List<User> selectRandom(String id);
}