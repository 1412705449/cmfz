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
public class Album {
    @Id
    private String id;
    private String title;
    private String score;
    private String author;
    private String broadcast;
    @Column(name = "count")
    private int count;
    private String descs;
    private String status;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "file_name")
    private String filename;

}
