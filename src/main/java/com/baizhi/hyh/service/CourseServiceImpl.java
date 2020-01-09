package com.baizhi.hyh.service;

import com.baizhi.hyh.dao.CourseDao;
import com.baizhi.hyh.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;

    @Override
    public List<Course> selectByUid(String userId) {
        return courseDao.selectByUid(userId);
    }

    @Override
    public void insertCourse(Course course) {
        courseDao.insertSelective(course);
    }

    @Override
    public void deleteById(String id) {
        courseDao.deleteByPrimaryKey(id);
    }
}
