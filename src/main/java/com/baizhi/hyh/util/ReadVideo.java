package com.baizhi.hyh.util;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;
import java.text.DecimalFormat;

public class ReadVideo {
    public static String getFileSize(File f) {
        // 保留两位小数
        DecimalFormat df = new DecimalFormat(".##");
        // 得到视频的长度
        Long long1 = f.length();
        String size = "";
        long G = 1024 * 1024 * 1024;
        long M = 1024 * 1024;
        long K = 1024;
        // 视频大小超过G、超过M不超过G、超过K不超过M
        if (long1 / G >= 1) {
            size = df.format((double) long1 / G) + "G";
        } else if (long1 / M >= 1) {
            size = df.format((double) long1 / M) + "M";
        } else if (long1 / K >= 1) {
            size = df.format((double) long1 / K) + "K";
        } else {
            size = long1 + "B";
        }
        return size;
    }

    /**
     * 得到视频的长度
     *
     * @param f 文件
     * @return 视频的长度
     */
    public static String getVideoTime(File f) {
        String time = "";
        //新建编码器对象
        Encoder encoder = new Encoder();
        try {
            //得到多媒体视频的信息
            MultimediaInfo m = encoder.getInfo(f);
            //得到毫秒级别的多媒体是视频长度
            long ls = m.getDuration();
            //转换为分秒
            time = ls / 60000 + "分" + (ls - (ls / 60000 * 60000)) / 1000 + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }
}
