package com.baizhi.hyh.dao;

import com.baizhi.hyh.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter> {
    //变更计数器
    public void updateCountById(@Param("id") String id, @Param("count") String count);
}
