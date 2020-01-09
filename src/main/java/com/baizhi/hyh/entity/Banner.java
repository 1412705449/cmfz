package com.baizhi.hyh.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.baizhi.hyh.util.ImageConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner implements Serializable {
    @Id
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("标题")
    private String title;
    @ExcelProperty(value = "图片", converter = ImageConverter.class)
    private String url;
    @ExcelProperty("超链接")
    private String href;
    @ExcelProperty("创建日期")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @ExcelProperty("介绍")
    private String descs;
    @ExcelProperty("状态")
    private String status;
    @ExcelIgnore
    @Column(name = "file_name")
    private String filename;

}
