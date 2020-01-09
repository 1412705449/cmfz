package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.ArticleDao;
import com.baizhi.hyh.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    public Map selectAll(Integer page, Integer rows) {
        HashMap map = new HashMap();
        /*添加总条数*/
        int records = articleDao.selectCount(null);
        map.put("records", records);
        /*添加总页数*/
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        /*添加当前页数*/
        map.put("page", page);
        List<Article> article = articleDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows", article);
        return map;
    }

    @Override
    public void insertArticle(Article article) {
        articleDao.insert(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public Article selectById(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public void deleteByIdsArticle(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public List<Article> selectByIds(String[] id) {
        List<Article> articles = articleDao.selectByIdList(Arrays.asList(id));
        return articles;
    }


    /*前台*/

    @Override
    public List<Article> selectAllArticle() {
        return articleDao.selectAll();
    }
}
