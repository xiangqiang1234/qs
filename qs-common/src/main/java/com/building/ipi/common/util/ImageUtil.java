package com.building.ipi.common.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Toning
 * @Description: 图片与十六进制 互相转换
 * @date 2019/4/24
 */
public class ImageUtil {

    public static void main(String[] args) {
        //将图片转换成十六进制字符串
        String hexString = imageToHex("f:/fileserver/1.jpg", "jpg");

        //将十六进制字符串转化成图片
        hexToImage("f://fileserver/2.jpg", hexString);
    }

    /**
     * 将图片转换成十六进制字符串
     */
    static String imageToHex(String filePath, String suffix) {
        File f = new File(filePath);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, suffix, baos);
            byte[] bytes = baos.toByteArray();
            return new BigInteger(1, bytes).toString(16);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将十六进制字符串转化成图片
     */
    static void hexToImage(String filePath, String hexString) {
        byte[] bytes = stringToByte(hexString);
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(filePath));
            imageOutput.write(bytes, 0, bytes.length);
            imageOutput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] stringToByte(String s) {
        int length = s.length() / 2;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) ((Character.digit(s.charAt(i * 2), 16) << 4) | Character.digit(s.charAt((i * 2) + 1), 16));
        }
        return bytes;
    }
}
