package com.baizhi.hyh.service;

import com.baizhi.hyh.aspect.Log;
import com.baizhi.hyh.dao.ChapterDao;
import com.baizhi.hyh.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Override
    public List<Chapter> selectById(String albumId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("albumId", albumId);
        return chapterDao.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map selectAll(Integer page, Integer rows, String albumId) {
        Example example = new Example(Chapter.class);
        example.createCriteria().andEqualTo("albumId", albumId);
        HashMap map = new HashMap();
        /*添加总条数*/
        int records = chapterDao.selectCountByExample(example);
        map.put("records", records);
        /*添加总页数*/
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        /*添加当前页数*/
        map.put("page", page);
        List<Chapter> chapter = chapterDao.selectByExampleAndRowBounds(example, new RowBounds((page - 1) * rows, rows));
        map.put("rows", chapter);
        return map;
    }

    @Override
    @Log(value = "章节的添加")
    public void insertChapter(Chapter charter) {
        chapterDao.insert(charter);
    }

    @Override
    @Log(value = "章节的删除")
    public void deleteChapter(String id) {
        chapterDao.deleteByPrimaryKey(id);
    }

    @Override
    @Log(value = "章节的批量删除")
    public void deleteAll(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @Log(value = "章节的修改")
    public void updateChapter(Chapter charter) {

        chapterDao.updateByPrimaryKeySelective(charter);
    }

    @Override
    public List<Chapter> selectByIds(String[] id) {
        return chapterDao.selectByIdList(Arrays.asList(id));
    }
}
