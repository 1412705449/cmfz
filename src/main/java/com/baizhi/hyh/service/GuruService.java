package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    /*查所有*/
    List<Guru> selectAll();

    /*查所有分页*/
    Map selectByPage(Integer page, Integer rows);

    /*添加*/
    public void insertGuru(Guru guru);

    /*假删除*/
    public void deleteGuru(String id);

    /*修改*/
    public void updateGuru(Guru guru);

    /*查一个*/
    public Guru selectById(String id);
}
