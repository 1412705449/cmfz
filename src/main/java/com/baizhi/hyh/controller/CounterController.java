package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.Counter;
import com.baizhi.hyh.entity.User;
import com.baizhi.hyh.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    CounterService counterService;

    /*展示计数器*/
    @RequestMapping("selectById")
    public Map selectById(String id) {
        HashMap hashMap = new HashMap();
        Counter counter = counterService.selectById(id);
        hashMap.put("counter", counter);
        hashMap.put("status", "200");
        return hashMap;
    }

    /*添加计数器*/
    @RequestMapping("insertCounter")
    public Map insertCounter(Counter counter, HttpSession session) {
        User user = (User) session.getAttribute("user");
        HashMap hashMap = new HashMap();
        counter.setId(UUID.randomUUID().toString().replace("-", ""));
        counter.setCreateDate(new Date());
        //counter.setUserId(user.getId());
        counter.setCount(0);
        counter.setCourseId("1");
        counter.setUserId("1");
        counterService.insertCounter(counter);
        hashMap.put("counter", counter);
        hashMap.put("status", "200");
        return hashMap;
    }

    /*删除计数器*/
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id) {
        HashMap hashMap = new HashMap();
        counterService.deleteCounter(id);
        hashMap.put("id", id);
        hashMap.put("status", "200");
        return hashMap;
    }

    /*变更计数器*/
    @RequestMapping("updateCountById")
    public Map updateCountById(String id, String count) {
        HashMap hashMap = new HashMap();
        counterService.updateCountById(id, count);
        hashMap.put("id", id);
        hashMap.put("count", count);
        hashMap.put("status", "200");
        return hashMap;
    }
}
