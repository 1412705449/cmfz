package com.baizhi.hyh.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.hyh.dao.BannerDao;
import com.baizhi.hyh.entity.Banner;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<Banner> {
    List<Banner> list = new ArrayList<Banner>();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        ApplicationContentUtils applicationContentUtils = new ApplicationContentUtils();
        BannerDao bean = (BannerDao) applicationContentUtils.getBean(BannerDao.class);
        bean.insert(banner);
        list.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}