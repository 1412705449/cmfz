package com.baizhi.hyh.controller;


import com.baizhi.hyh.entity.Album;
import com.baizhi.hyh.entity.Chapter;
import com.baizhi.hyh.service.AlbumService;
import com.baizhi.hyh.service.ChapterService;
import com.baizhi.hyh.util.HttpUtil;
import com.baizhi.hyh.util.ReadVideo;
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
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumService albumService;

    /*查所有*/
    @RequestMapping("selectByPage")
    public Map selectByPage(Integer page, Integer rows, String albumId) {
        Map map = chapterService.selectAll(page, rows, albumId);
        return map;
    }

    /*增删改*/
    @RequestMapping("chapter")
    public Map album(String oper, String[] id, Chapter chapter, String albumId, HttpSession session) {
        HashMap hashMap = new HashMap();
        if (oper.equals("del")) {
            /*删除文件中对应的数据*/
            List<Chapter> chapters = chapterService.selectByIds(id);
            for (Chapter chapter1 : chapters) {
                String realPath = session.getServletContext().getRealPath("/load/chapterAudio");
                String fileName = chapter1.getFilename();
                File file = new File(realPath + "/" + fileName);
                file.delete();
            }
            chapterService.deleteAll(id);
            Album album = albumService.selectById(albumId);
            /*修改Album表中对应的数据*/
            album.setCount(album.getCount() - 1);
            albumService.updateAlbum(album);

        } else if (oper.equals("add")) {
            String chapterId = UUID.randomUUID().toString().replace("-", "");
            chapter.setId(chapterId);
            chapter.setAlbumId(albumId);
            chapter.setCreateTime(new Date());
            chapterService.insertChapter(chapter);
            Album album = albumService.selectById(albumId);
            album.setCount(album.getCount() + 1);
            albumService.updateAlbum(album);

            hashMap.put("chapterId", chapterId);
        } else if (oper.equals("edit")) {
            hashMap.put("chapterId", chapter.getId());
            String url = chapter.getUrl();
            if (url.equals("")) {
            } else {
                String filename = chapter.getFilename();
                //删除文件中的数据
                String realPath = session.getServletContext().getRealPath("/load/chapterAudio");
                File file1 = new File(realPath + "/" + filename);
                file1.delete();
            }
            chapter.setCreateTime(new Date());
            chapterService.updateChapter(chapter);
        }
        return hashMap;
    }

    /*文件上传*/
    @RequestMapping("uploadChapter")
    public Map uploadChapter(HttpServletRequest request, MultipartFile url, String chapterId, HttpSession session) {
        HashMap map = new HashMap();
        /*获取真实路径*/
        String realPath = session.getServletContext().getRealPath("/load/chapterAudio");
        /*判断文件夹是否存在*/
        File file = new File(realPath);
        if (!file.exists()) {
            /*多级创建*/
            file.mkdirs();
        }

        if (url.getOriginalFilename().equals("")) {
            /*不修改音频*/
            String oldurl = (String) session.getAttribute("chapterurl");
            String filename = (String) session.getAttribute("chapterfilename");
            /*传入地址*/
            File fileTimeAndSize = new File(realPath + "\\" + filename);
            /*获取上传文件的时长 大小*/
            String fileSize = ReadVideo.getFileSize(fileTimeAndSize);
            String videoTime = ReadVideo.getVideoTime(fileTimeAndSize);
            /*入库*/
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl(oldurl);
            chapter.setSizes(fileSize);
            chapter.setTime(videoTime);
            chapter.setFilename(filename);
            chapterService.updateChapter(chapter);
            session.setAttribute("chapterurl", oldurl);
            session.setAttribute("chapterfilename", filename);
        } else {

            //防止文件重名
            String filename = new Date().getTime() + "_" + url.getOriginalFilename();
            try {
                url.transferTo(new File(realPath, filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*传入地址*/
            File fileTimeAndSize = new File(realPath + "\\" + filename);
            /*获取上传文件的时长 大小*/
            String fileSize = ReadVideo.getFileSize(fileTimeAndSize);
            String videoTime = ReadVideo.getVideoTime(fileTimeAndSize);
            /*入库*/
            String httpUrl = HttpUtil.getHttpUrl(request, "/load/chapterAudio/");
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl(httpUrl + filename);
            chapter.setSizes(fileSize);
            chapter.setTime(videoTime);
            chapter.setFilename(filename);
            chapterService.updateChapter(chapter);
            session.setAttribute("chapterurl", httpUrl + filename);
            session.setAttribute("chapterfilename", filename);
        }
        map.put("code", 200);
        return map;

    }
}
