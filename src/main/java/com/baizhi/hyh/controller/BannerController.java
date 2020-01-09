package com.baizhi.hyh.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.hyh.dao.BannerDao;
import com.baizhi.hyh.entity.Banner;
import com.baizhi.hyh.entity.PageDto;
import com.baizhi.hyh.service.BannerService;
import com.baizhi.hyh.util.DemoDataListener;
import com.baizhi.hyh.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @Autowired
    BannerDao bannerDao;

    /*导出到Excel*/
    @RequestMapping("easyExcel")
    public void easyExcel(HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/load/easyExcel");
        File file = new File(realPath);
        if (!file.exists()) {
            /*多级创建*/
            file.mkdirs();
        }
        List<Banner> banners = bannerDao.selectAll();

        String fileName = realPath + "\\" + new Date().getTime() + ".xls";
        EasyExcel.write(fileName, Banner.class)
                .sheet("轮播")
                .doWrite(banners);
    }

    /*导入到Excel*/
    @RequestMapping("easyImport")
    public void easyImport(HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/load/easyExcel");
        String url = realPath + "\\" + "导入所用.xls";
        EasyExcel.read(url, Banner.class, new DemoDataListener()).sheet().doRead();
    }

    /*查所有*/
    @RequestMapping("selectByPage")
    public PageDto selectByPage(Integer page, Integer rows) {
        PageDto pageDto = bannerService.selectByPage(page, rows);
        return pageDto;
    }

    /*增删改查*/
    @RequestMapping("banner")
    public Map banner(String oper, String id, Banner banner, HttpSession session) {
        HashMap map = new HashMap();
        if (oper.equals("del")) {
            /*假删除*/
            bannerService.deleteBanner(id);
            return null;
        } else if (oper.equals("edit")) {
            //修改
            map.put("bannerId", banner.getId());
            String url = banner.getUrl();
            if (url.equals("")) {
            } else {
                String filename = banner.getFilename();
                //删除文件中的数据
                String realPath = session.getServletContext().getRealPath("/load/bannerImg");
                File file1 = new File(realPath + "/" + filename);
                file1.delete();
            }
            bannerService.updateBanner(banner);
            return map;
        } else if (oper.equals("add")) {
            //添加
            String ids = UUID.randomUUID().toString().replace("-", "");
            banner.setId(ids);
            banner.setCreateDate(new Date());
            bannerService.insertBanner(banner);
            map.put("bannerId", banner.getId());
            return map;
        }
        return null;
    }

    /*文件上传*/
    @RequestMapping("uploadBanner")
    public Map uploadBanner(HttpServletRequest request, MultipartFile url, String bannerId, HttpSession session) {
        HashMap map = new HashMap();
        /*获取真实路径*/
        String realPath = session.getServletContext().getRealPath("/load/bannerImg");
        /*判断文件夹是否存在*/
        File file = new File(realPath);
        if (!file.exists()) {
            /*多级创建*/
            file.mkdirs();
        }
        if (url.getOriginalFilename().equals("")) {
            /*未修改图片*/
            String oldurl = (String) session.getAttribute("bannerurl");
            String filename = (String) session.getAttribute("bannerfilename");
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setCreateDate(new Date());
            banner.setUrl(oldurl);
            banner.setFilename(filename);
            bannerService.updateBanner(banner);
            session.setAttribute("bannerurl", banner.getUrl());
            session.setAttribute("bannerfilename", banner.getFilename());
            map.put("code", 200);
            return map;
        } else {
            //防止文件重名
            /*修改图片*/
            String filename = new Date().getTime() + "_" + url.getOriginalFilename();
            try {
                url.transferTo(new File(realPath, filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String httpUrl = HttpUtil.getHttpUrl(request, "/load/bannerImg/");
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(httpUrl + filename);
            banner.setCreateDate(new Date());
            banner.setFilename(filename);
            bannerService.updateBanner(banner);
            session.setAttribute("bannerurl", banner.getUrl());
            session.setAttribute("bannerfilename", banner.getFilename());
            map.put("code", 200);
            return map;
        }
    }
}
