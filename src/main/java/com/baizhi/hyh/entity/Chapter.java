package com.baizhi.hyh.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapter {
    @Id
    private String id;
    private String title;
    private String url;
    private String sizes;
    private String time;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "album_id")
    private String albumId;
    @Column(name = "file_name")
    private String filename;

}
