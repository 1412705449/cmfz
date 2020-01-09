package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.Chapter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ChapterDao extends Mapper<Chapter>, DeleteByIdListMapper<Chapter, String>, SelectByIdListMapper<Chapter, String> {
}
