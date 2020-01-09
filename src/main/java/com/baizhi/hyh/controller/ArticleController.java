package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.Article;
import com.baizhi.hyh.service.ArticleService;
import com.baizhi.hyh.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /*内容中图片上传*/
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request) {
        HashMap map = new HashMap();
        String realPath = session.getServletContext().getRealPath("/load/articleImg");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 网络路径
        try {
            String httpUrl = HttpUtil.getHttpUrl(request, "/load/articleImg/");
            String originalFilename = imgFile.getOriginalFilename();
            String filename = new Date().getTime() + "_" + originalFilename;
            imgFile.transferTo(new File(realPath, filename));
            String http = httpUrl + filename;
            map.put("error", 0);
            map.put("url", http);
        } catch (Exception e) {
            map.put("error", 1);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request, HttpSession session) {
        HashMap hashMap = new HashMap();
        hashMap.put("current_url", request.getContextPath() + "/load/articleImg/");
        String realPath = session.getServletContext().getRealPath("/load/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count", files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir", false);
            fileMap.put("has_file", false);
            fileMap.put("filesize", file1.length());
            fileMap.put("is_photo", true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype", extension);
            fileMap.put("filename", name);
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime", format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list", arrayList);
        return hashMap;
    }

    /*查所有*/
    @RequestMapping("selectByPage")
    public Map selectByPage(Integer page, Integer rows) {
        Map map = articleService.selectAll(page, rows);
        return map;
    }

    /*删除*/
    @RequestMapping("delete")
    public void delete(String oper, String[] id, HttpSession session) {
        if (oper.equals("del")) {
            List<Article> articles = articleService.selectByIds(id);
            for (Article article : articles) {
                String oldfilename = article.getFilename();
                //删除文件中的数据
                /*获取真实路径*/
                String realPath = session.getServletContext().getRealPath("/load/article");
                File file1 = new File(realPath + "/" + oldfilename);
                file1.delete();
            }
            articleService.deleteByIdsArticle(id);
        }
    }

    /*增修改*/
    @RequestMapping("article")
    public void album(Article article, MultipartFile fileImg, HttpSession session, HttpServletRequest request) {
        if (article.getId().equals("")) {
            /*添加*/
            /*id*/
            article.setId(UUID.randomUUID().toString().replace("-", ""));
            /*创建日期*/
            article.setCreateDate(new Date());
            /*发布日期*/
            article.setPublishDate(new Date());
            /*上传文件*/
            /*获取真实路径*/
            String realPath = session.getServletContext().getRealPath("/load/article");
            File file = new File(realPath);
            /*判断文件是否存在*/
            if (!file.exists()) {
                file.mkdirs();
            }
            String filename = new Date().getTime() + "_" + fileImg.getOriginalFilename();
            try {
                fileImg.transferTo(new File(realPath, filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String httpUrl = HttpUtil.getHttpUrl(request, "/load/article/");
            article.setImgfile(httpUrl + filename);
            article.setFilename(filename);
            articleService.insertArticle(article);
        } else {
            /*获取真实路径*/
            String realPath = session.getServletContext().getRealPath("/load/article");
            //修改
            if (fileImg.getOriginalFilename().equals("")) {
                /*未修改图片*/
                String id = article.getId();
                Article article1 = articleService.selectById(id);
                String imgfile = article1.getImgfile();
                String filename = article1.getFilename();
                article.setImgfile(imgfile);
                article.setFilename(filename);
            } else {
                /*修改图片*/
                String id = article.getId();
                Article article1 = articleService.selectById(id);
                String oldfilename = article1.getFilename();
                //删除文件中的数据
                File file1 = new File(realPath + "/" + oldfilename);
                if (file1 != null) {
                    if (file1.exists()) {
                        file1.delete();
                    }
                }

                String filename = new Date().getTime() + "_" + fileImg.getOriginalFilename();
                try {
                    fileImg.transferTo(new File(realPath, filename));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String httpUrl = HttpUtil.getHttpUrl(request, "/load/article/");
                article.setImgfile(httpUrl + filename);
                article.setFilename(filename);
            }
            if (article.getContent().equals("")) {
                /*不修改内容*/
                Article article1 = articleService.selectById(article.getId());
                article.setContent(article1.getContent());
            } else {
                /*修改内容*/
            }
            articleService.updateArticle(article);
        }
    }


    //前台
    /*文章详情*/
    @RequestMapping("selectById")
    public Map selectById(String id) {
        HashMap hashMap = new HashMap();
        Article article = articleService.selectById(id);
        hashMap.put("article", article);
        hashMap.put("status", "200");
        return hashMap;
    }
}