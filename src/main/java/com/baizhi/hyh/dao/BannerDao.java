package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.Banner;
import tk.mybatis.mapper.common.Mapper;

public interface BannerDao extends Mapper<Banner> {
    //假删除
    public void updateByStatus(String id);
}
