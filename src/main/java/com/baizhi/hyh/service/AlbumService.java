package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    /*查所有*/
    public Map selectAll(Integer page, Integer rows);

    /*添加*/
    public void insertAlbum(Album album);

    /*修改*/
    public void updateAlbum(Album album);

    //查一个
    public Album selectById(String id);

    /*批量删除*/
    public void deleteByIdsAlbum(String[] id);

    /*批量查询*/
    public List<Album> selectByIds(String[] id);

    /*前台*/
    /*查所有*/
    public List<Album> selectAllAlbum();
}
