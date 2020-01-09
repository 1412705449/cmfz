package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends Mapper<Article>, DeleteByIdListMapper<Article, String>, SelectByIdListMapper<Article, String> {
}
