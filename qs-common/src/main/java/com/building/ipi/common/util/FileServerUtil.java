package com.building.ipi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @author: yuzj
 * @Date: 2019/07/02 14:27
 */
public class FileServerUtil {
    static Properties properties = null;
    public static String fileServerUrl = null;
    public static String fileServerFilePath = null;

    public FileServerUtil() {
    }

    static {
        try {
            InputStream in = com.building.ipi.common.util.FileServerUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties = new Properties();
            properties.load(in);
            fileServerUrl = properties.getProperty("fileserver.url");
            fileServerFilePath = properties.getProperty("fileserver.filepath");
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}
