package com.baizhi.hyh.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.hyh.entity.*;
import com.baizhi.hyh.service.AlbumService;
import com.baizhi.hyh.service.ArticleService;
import com.baizhi.hyh.service.UserService;
import com.baizhi.hyh.util.SmsUtils;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;


    Jedis jedis = new Jedis("192.168.153.15", 6379);

    /*地区分布*/
    @RequestMapping("selectBySexAndLocation")
    public Map selectBySexAndLocation() {
        HashMap hashMap = new HashMap();
        List<MapDto> man = userService.selectBySexAndLocation("1");
        List<MapDto> women = userService.selectBySexAndLocation("0");
        hashMap.put("man", man);
        hashMap.put("women", women);
        return hashMap;
    }

    //统计用户活跃
    @RequestMapping("selectBySexAndDay")
    public Map selectBySexAndDay() {
        HashMap map = new HashMap();
        ArrayList manlist = new ArrayList();
        manlist.add(userService.selectBySexAndDay("0", 1));
        manlist.add(userService.selectBySexAndDay("0", 7));
        manlist.add(userService.selectBySexAndDay("0", 30));
        manlist.add(userService.selectBySexAndDay("0", 356));
        ArrayList womanlist = new ArrayList();
        womanlist.add(userService.selectBySexAndDay("1", 1));
        womanlist.add(userService.selectBySexAndDay("1", 7));
        womanlist.add(userService.selectBySexAndDay("1", 30));
        womanlist.add(userService.selectBySexAndDay("1", 356));
        map.put("man", manlist);
        map.put("woman", womanlist);
        return map;
    }

    /*测试添加用户*/
    @RequestMapping("addUser")
    public void addUser(User user) {
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setSex("0");
        user.setLocation("河北");
        user.setRigestDate(new Date());
        userService.addUser(user);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-12a269729da540b9aae5c52b7a206f55");
        Map map = selectBySexAndDay();
        String s = JSONUtils.toJSONString(map);
        goEasy.publish("cmfz", s);
    }


    /*查所有*/
    @RequestMapping("selectByPage")
    public Map selectByPage(Integer page, Integer rows) {
        Map map = userService.selectByPage(page, rows);
        return map;
    }

    /*修改状态*/
    @RequestMapping("updateStates")
    public void updateStates(User user) {
        User user1 = userService.selectOne(user.getId());
        if (user1.getStatus().equals("1")) {
            user1.setStatus("0");
        } else {
            user1.setStatus("1");
        }
        userService.updateStatus(user1);
    }


    /*前台*/
    //登陆
    @RequestMapping("login")
    public Map login(String phone, String password) {
        HashMap hashMap = new HashMap();
        User login = userService.login(phone, password);
        if (login == null) {
            hashMap.put("status", "-200");
        } else {
            hashMap.put("user", login);
            hashMap.put("status", "200");
        }
        return hashMap;
    }

    /*发送验证码*/
    @RequestMapping("sendCode")
    public Map sendCode(String phone) {
        HashMap hashMap = new HashMap();
        try {
            String s = UUID.randomUUID().toString();
            //String substring = s.substring(0, 4);
            String substring = "xiaopangzi";
            SmsUtils.send(phone, substring);
            /*将验证码填进redise*/
            jedis.set("code", substring);
            jedis.setex("code", 120, substring);
            hashMap.put("status", "200");
            hashMap.put("message", "短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
            hashMap.put("message", "短信未发送成功");
        }
        return hashMap;
    }

    /*注册*/
    @RequestMapping("addUserByCode")
    public Map addUserByCode(String codes) {
        HashMap hashMap = new HashMap();
        String code = jedis.get("code");
        if (codes.equals(code)) {
            hashMap.put("status", "200");
            hashMap.put("message", "注册成功");
        } else {
            hashMap.put("status", "-200");
            hashMap.put("message", "注册失败");
        }
        return hashMap;
    }

    /*补充个人信息接口*/
    @RequestMapping("information")
    public Map information(User user) {
        HashMap hashMap = new HashMap();
        try {
            userService.information(user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
        }
        return hashMap;
    }

    /*一级页面*/
    /*查5条轮播图*/
    /*查询专辑*/
    /*查询文章*/
    @RequestMapping("selectAll")
    public Map selectBanner(String uid, String type, String sub_type) {
        HashMap hashMap = new HashMap();
        if ("all".equals(type)) {
            List<Banner> banners = userService.selectBanner();
            List<Album> albums = userService.selectAlbum(uid);
            hashMap.put("banners", banners);
            hashMap.put("albums", albums);
            hashMap.put("status", "200");
        } else if ("wen ".equals(type)) {
            List<Album> albums = albumService.selectAllAlbum();
            hashMap.put("albums", albums);
            hashMap.put("status", "200");
        } else if ("si".equals(type)) {
            if ("ssyj".equals(sub_type)) {
                List<Article> articles = userService.selectArticle(uid);
                hashMap.put("articles", articles);
                hashMap.put("status", "200");
            } else {
                List<Article> articles = articleService.selectAllArticle();
                hashMap.put("articles", articles);
                hashMap.put("status", "200");
            }
        }
        return hashMap;
    }


    /*修改个人信息*/
    @RequestMapping("updateUserInformation")
    public Map updateUserInformation(User user) {
        HashMap hashMap = new HashMap();
        try {
            userService.updateUserInformation(user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
        }
        return hashMap;
    }

    /*金刚道友*/
    @RequestMapping("selectRandom")
    public Map selectRandom(HttpSession session) {
        HashMap hashMap = new HashMap();
        User user1 = (User) session.getAttribute("user");
        try {
            //List<User> user = userService.selectRandom(user1.getId());
            List<User> user = userService.selectRandom("1");
            hashMap.put("user", user);
            hashMap.put("status", "200");
        } catch (Exception e) {
            e.printStackTrace();
            hashMap.put("status", "-200");
        }
        return hashMap;
    }

}