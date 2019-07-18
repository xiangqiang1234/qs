package com.building.ipi.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

/**
 * 编码转换
 *
 * @author yuzj
 * @date 2017-12-23
 */
public class Decode {
    private static Logger logger;

    /**
     * 编码转为 UTF-8
     *
     * @param value
     * @return
     */
    public static String getUtfCode(String value) {
        String name = "";
        if (StringUtils.isNoneBlank(value)) {
            try {
                name = java.net.URLDecoder.decode(value.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.info(e.getMessage());
            }
        } else {
            name = value;
        }
        return name;
    }

    /**
     * 编码转为 UTF-8
     *
     * @param value
     * @param className
     * @return
     */
    public static String getUtfCode(String value, String className) {
        String name = "";
        if (StringUtils.isNoneBlank(value)) {
            try {
                name = java.net.URLDecoder.decode(value.trim(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log4jUtil.info(className, e.getMessage());
            }
        } else {
            name = value;
        }
        return name;
    }

    private Decode() {

    }
}
