package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumDao extends Mapper<Album>, DeleteByIdListMapper<Album, String>, SelectByIdListMapper<Album, String> {
}
