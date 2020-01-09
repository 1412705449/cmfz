package com.baizhi.hyh.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtil {
    public static String getHttpUrl(HttpServletRequest request, String path) {

        /*获取http协议*/
        String scheme = request.getScheme();
        /*获取ip */
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        /*获取端口号*/
        int serverPort = request.getServerPort();
        /*获取项目名*/
        String contextPath = request.getContextPath();
        String uri = scheme + "://" + ip + ":" + serverPort + contextPath + path;
        return uri;
    }
}
