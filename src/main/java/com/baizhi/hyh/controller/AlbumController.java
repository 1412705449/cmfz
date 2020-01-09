package com.baizhi.hyh.controller;

import com.baizhi.hyh.entity.Album;
import com.baizhi.hyh.entity.Chapter;
import com.baizhi.hyh.service.AlbumService;
import com.baizhi.hyh.service.ChapterService;
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
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;

    /*查所有*/
    @RequestMapping("selectByPage")
    public Map selectByPage(Integer page, Integer rows) {
        Map map = albumService.selectAll(page, rows);
        return map;
    }

    /*增删改*/
    @RequestMapping("album")
    public Map album(String oper, String[] id, Album album, HttpSession session) {
        HashMap hashMap = new HashMap();
        if (oper.equals("del")) {
            /*删除所有有关章节*/
            for (String s : id) {
                List<Chapter> chapters = chapterService.selectById(s);
                for (Chapter chapter : chapters) {
                    /*删除文件中的数据*/
                    String realPath = session.getServletContext().getRealPath("/load/chapterAudio");
                    String fileName = chapter.getFilename();
                    File file = new File(realPath + "/" + fileName);
                    file.delete();
                    String id1 = chapter.getId();
                    chapterService.deleteChapter(id1);
                }
            }
            /*删除文件中的数据*/
            for (String s : id) {
                Album album1 = albumService.selectById(s);
                String realPath = session.getServletContext().getRealPath("load/albumImg");
                String fileName = album1.getFilename();
                File file = new File(realPath + "/" + fileName);
                file.delete();
            }
            albumService.deleteByIdsAlbum(id);

        } else if (oper.equals("add")) {
            String albumId = UUID.randomUUID().toString().replace("-", "");
            album.setId(albumId);
            //默认专辑的数量
            album.setCount(0);
            albumService.insertAlbum(album);
            hashMap.put("albumId", albumId);
        } else if (oper.equals("edit")) {
            hashMap.put("albumId", album.getId());
            String status = album.getStatus();
            if (status.equals("")) {
            } else {
                String filename = album.getFilename();
                //删除文件中的数据
                String realPath = session.getServletContext().getRealPath("/load/albumImg");
                File file1 = new File(realPath + "/" + filename);
                file1.delete();
            }
            albumService.updateAlbum(album);
        }
        return hashMap;
    }

    /*文件上传*/
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(HttpServletRequest request, MultipartFile status, String albumId, HttpSession session) {
        HashMap map = new HashMap();
        /*获取真实路径*/
        String realPath = session.getServletContext().getRealPath("/load/albumImg");
        /*判断文件夹是否存在*/
        File file = new File(realPath);
        if (!file.exists()) {
            /*多级创建*/
            file.mkdirs();
        }

        if (status.getOriginalFilename().equals("")) {
            /*未修改图片*/
            String oldurl = (String) session.getAttribute("oldurl");
            String filename = (String) session.getAttribute("oldfilename");
            Album album = new Album();
            album.setId(albumId);
            album.setStatus(oldurl);
            album.setFilename(filename);
            albumService.updateAlbum(album);
            session.setAttribute("oldurl", album.getStatus());
            session.setAttribute("oldfilename", album.getFilename());

            map.put("code", 200);
            return map;
        } else {
            //防止文件重名
            /*修改图片*/
            String filename = new Date().getTime() + "_" + status.getOriginalFilename();
            try {
                status.transferTo(new File(realPath, filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String httpUrl = HttpUtil.getHttpUrl(request, "/load/albumImg/");
            Album album = new Album();
            album.setId(albumId);
            album.setStatus(httpUrl + filename);
            album.setFilename(filename);
            album.setCreateDate(new Date());
            albumService.updateAlbum(album);
            session.setAttribute("oldurl", album.getStatus());
            session.setAttribute("oldfilename", album.getFilename());
            map.put("code", 200);
            return map;
        }
    }


    //前台
    /*专辑详情*/
    @RequestMapping("selectById")
    public Map selectById(String id) {
        HashMap hashMap = new HashMap();
        Album album = albumService.selectById(id);
        hashMap.put("album", album);
        hashMap.put("status", "200");
        return hashMap;
    }
}