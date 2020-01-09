package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.Guru;
import com.baizhi.hyh.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    /*查所有*/
    @RequestMapping("selectAll")
    @ResponseBody
    public List<Guru> selectAll() {
        List<Guru> gurus = guruService.selectAll();
        return gurus;
    }

    /*查所有分页*/
    @RequestMapping("selectByPage")
    @ResponseBody
    public Map selectByPage(Integer page, Integer rows) {
        Map map = guruService.selectByPage(page, rows);
        return map;
    }

}