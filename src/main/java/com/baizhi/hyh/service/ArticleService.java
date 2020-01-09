package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    /*查所有*/
    public Map selectAll(Integer page, Integer rows);

    /*添加*/
    public void insertArticle(Article article);

    /*修改*/
    public void updateArticle(Article article);

    //查一个
    public Article selectById(String id);

    /*批量删除*/
    public void deleteByIdsArticle(String[] id);

    /*批量查询*/
    public List<Article> selectByIds(String[] id);


    //前台
    /*查所有*/
    public List<Article> selectAllArticle();
}
