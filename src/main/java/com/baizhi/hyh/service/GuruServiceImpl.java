package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.GuruDao;
import com.baizhi.hyh.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public Map selectByPage(Integer page, Integer rows) {
        HashMap map = new HashMap();
        /*添加总条数*/
        int records = guruDao.selectCount(null);
        map.put("records", records);
        /*添加总页数*/
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        /*添加当前页数*/
        map.put("page", page);
        List<Guru> albums = guruDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows", albums);
        return map;
    }

    @Override
    public void insertGuru(Guru guru) {

    }

    @Override
    public void deleteGuru(String id) {

    }

    @Override
    public void updateGuru(Guru guru) {

    }

    @Override
    public Guru selectById(String id) {
        return null;
    }
}
