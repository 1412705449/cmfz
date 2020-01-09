package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.User;
import com.baizhi.hyh.entity.Userandguru;
import com.baizhi.hyh.service.UserandGuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userandguru")
public class UserandGuruController {
    @Autowired
    UserandGuruService userandGuruService;

    /*添加灌输上师*/
    @RequestMapping("insert")
    public Map insert(HttpSession session, Userandguru userandguru) {
        HashMap hashMap = new HashMap();
        User user = (User) session.getAttribute("user");
        // userandguru.setUid(user.getId());
        userandguru.setUid("1");
        userandGuruService.insert(userandguru);
        hashMap.put("userandguru", userandguru);
        hashMap.put("status", 200);
        return hashMap;
    }
}
