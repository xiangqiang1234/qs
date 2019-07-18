package com.building.ipi.common.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 文件服务器工具
 *
 * @author Harry Tian
 * @date 2016.07.11
 */
public class FileServerToolUtil {

    //配置
    static Properties properties = null;
    //文件服务器地址
    private static String fileServerUrl = null;
    //文件服务器物理路径
    private static String fileServerFilePath = null;

    static {
        try {
            InputStream in = FileServerToolUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(in);
            fileServerUrl = properties.getProperty("fileserver.url");
            fileServerFilePath = properties.getProperty("fileserver.filepath");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFileServerUrl() {
        return fileServerUrl;
    }

    public static void setFileServerUrl(String fileServerUrl) {
        FileServerToolUtil.fileServerUrl = fileServerUrl;
    }

    public static String getFileServerFilePath() {
        return fileServerFilePath;
    }

    public static void setFileServerFilePath(String fileServerFilePath) {
        FileServerToolUtil.fileServerFilePath = fileServerFilePath;
    }
}
