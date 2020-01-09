package com.baizhi.hyh.service;

import com.baizhi.hyh.aspect.Log;
import com.baizhi.hyh.dao.BannerDao;
import com.baizhi.hyh.entity.Banner;
import com.baizhi.hyh.entity.PageDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageDto selectByPage(Integer page, Integer rows) {
        PageDto pageDto = new PageDto();
        int totle = bannerDao.selectCount(new Banner());
        int lastpage = totle % rows == 0 ? totle / rows : totle / rows + 1;
        pageDto.setRecords(totle);//总行数
        pageDto.setTotal(lastpage);//总页数
        pageDto.setPage(page);//当前页数
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        pageDto.setRows(banners);
        return pageDto;
    }

    @Override
    @Log(value = "轮播图的添加")
    public void insertBanner(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    @Log(value = "轮播图的修改状态即删除")
    public void deleteBanner(String id) {
        bannerDao.updateByStatus(id);
    }

    @Override
    @Log(value = "轮播图的修改")
    public void updateBanner(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public Banner selectById(String id) {
        return bannerDao.selectByPrimaryKey(id);
    }
}
