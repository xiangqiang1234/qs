package com.building.ipi.common.util;

import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Toning
 * @Description: 附件转换为Base64字节
 * @date 2019/5/25
 */
public class ArmUtil {
    private static Logger logger = Logger.getLogger(ArmUtil.class);

    public static void main(String[] args) {
        String path = "F://fileserver/mes//upload/import/3.amr";
        String s = armToBase(path);
        System.out.println(s);
    }

    /**
     * 对字节数组Base64编码
     *
     * @param path
     * @return 返回Base64编码过的字节数组字符串
     */
    public static String armToBase(String path) {
        File source = new File(path);
        byte[] data = {};
        try {
            InputStream in = new FileInputStream(source);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            logger.info(e);
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data).replaceAll("[\\s*\t\n\r]", "");
    }

}
