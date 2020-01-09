package com.baizhi.hyh.dao;

import com.baizhi.hyh.cache.MybatiesCache;
import com.baizhi.hyh.entity.Course;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace(implementation = MybatiesCache.class)
public interface CourseDao extends Mapper<Course> {
    //根据用户id查询对应功课
    public List<Course> selectByUid(String userId);
}
