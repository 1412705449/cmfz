package com.baizhi.hyh.service;

import com.baizhi.hyh.entity.Course;

import java.util.List;

public interface CourseService {
    //根据用户id查询对应功课
    public List<Course> selectByUid(String userId);

    //添加功课
    public void insertCourse(Course course);

    /*删除功课*/
    public void deleteById(String id);
}
