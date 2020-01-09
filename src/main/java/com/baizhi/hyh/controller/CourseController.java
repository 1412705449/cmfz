package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.Course;
import com.baizhi.hyh.entity.User;
import com.baizhi.hyh.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseService courseService;

    /* //根据用户id查询对应功课*/
    @RequestMapping("selectByUid")
    public Map selectByUid(String userId) {
        HashMap hashMap = new HashMap();
        List<Course> course = courseService.selectByUid(userId);
        hashMap.put("course", course);
        hashMap.put("status", "200");
        return hashMap;
    }

    /*添加功课*/
    @RequestMapping("insertCourse")
    public Map insertCourse(Course course, HttpSession session) {
        User user = (User) session.getAttribute("user");
        HashMap hashMap = new HashMap();
        course.setId(UUID.randomUUID().toString().replace("-", ""));
        //course.setUserId(user.getId());
        course.setUserId("1");
        course.setCreateDate(new Date());
        courseService.insertCourse(course);
        hashMap.put("course", course);
        hashMap.put("status", "200");
        return hashMap;
    }

    /*删除功课*/
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String id) {
        HashMap hashMap = new HashMap();
        courseService.deleteById(id);
        hashMap.put("id", id);
        hashMap.put("status", "200");
        return hashMap;
    }
}
