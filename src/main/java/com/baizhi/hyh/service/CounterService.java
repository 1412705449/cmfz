package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Counter;

public interface CounterService {
    // 展示计数器
    public Counter selectById(String id);

    /*添加计数器*/
    public void insertCounter(Counter counter);

    /*删除计数器*/
    public void deleteCounter(String id);

    //变更计数器
    public void updateCountById(String id, String count);
}
