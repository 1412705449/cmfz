package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Banner;
import com.baizhi.hyh.entity.PageDto;

public interface BannerService {
    /*查所有*/
    PageDto selectByPage(Integer page, Integer rows);

    /*添加*/
    public void insertBanner(Banner banner);

    /*假删除*/
    public void deleteBanner(String id);

    /*修改*/
    public void updateBanner(Banner banner);

    /*查一个*/
    public Banner selectById(String id);
}
