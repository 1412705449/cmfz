package com.baizhi.hyh;


import com.baizhi.hyh.dao.BannerDao;
import com.baizhi.hyh.entity.Banner;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    BannerDao bannerDao;

    /*导出*/
    @Test
    public void test1() {
        List<Banner> banners = bannerDao.selectAll();
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作蒲
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        String str[] = {"ID", "标题", "图片", "超链接", "创建时间", "描述", "状态", "图片名"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        //通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //通过workbook对象获取数据格式化处理对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy月MM日dd天");
        //为样式对象设置格式化处理
        cellStyle.setDataFormat(format);
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getHref());
            row1.createCell(3).setCellValue(banner.getDescs());
            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreateDate());
            row1.createCell(5).setCellValue(banner.getUrl());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("E:\\第三阶段\\后期项目\\day7-poiEasyExcel\\示例\\" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导入

    @Test
    public void test2() {

    }
}
