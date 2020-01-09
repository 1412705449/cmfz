package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    /*查所有*/
    public Map selectAll(Integer page, Integer rows, String albumId);

    /*添加*/
    public void insertChapter(Chapter charter);

    /*删除*/
    public void deleteChapter(String id);

    /*批量删除*/
    public void deleteAll(String[] id);

    /*修改*/
    public void updateChapter(Chapter charter);

    //查一个
    public List<Chapter> selectById(String albumId);

    /*批量查找*/
    public List<Chapter> selectByIds(String[] id);
}
