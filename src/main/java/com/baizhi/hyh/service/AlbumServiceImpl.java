package com.baizhi.hyh.service;

import com.baizhi.hyh.aspect.Log;
import com.baizhi.hyh.dao.AlbumDao;
import com.baizhi.hyh.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map selectAll(Integer page, Integer rows) {
        HashMap map = new HashMap();
        /*添加总条数*/
        int records = albumDao.selectCount(null);
        map.put("records", records);
        /*添加总页数*/
        int total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        /*添加当前页数*/
        map.put("page", page);
        List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        map.put("rows", albums);
        return map;
    }

    @Override
    @Log(value = "专辑的添加")
    public void insertAlbum(Album album) {
        albumDao.insert(album);
    }

    @Override
    @Log(value = "专辑的修改")
    public void updateAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public Album selectById(String id) {
        Album album = new Album();
        album.setId(id);
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    @Log(value = "专辑的批量删除")
    public void deleteByIdsAlbum(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    public List<Album> selectByIds(String[] id) {
        return albumDao.selectByIdList(Arrays.asList(id));
    }


    /*前台*/

    @Override
    public List<Album> selectAllAlbum() {
        return albumDao.selectAll();
    }
}
