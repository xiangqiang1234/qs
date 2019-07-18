package com.building.ipi.common.util;

import org.apache.log4j.Logger;

/**
 * @Description:
 * @author: yuzj
 * @Date: 2019/07/02 14:23
 */
public class Log4jUtil {
    public static Logger log = Logger.getLogger(Log4jUtil.class);

    public Log4jUtil() {
    }

    public static Logger getLogger() {
        return log;
    }

    public static void debug(String className, String msg) {
        log.debug(className + " - " + msg);
    }

    public static void info(String className, String msg) {
        log.info(className + " - " + msg);
    }

    public static void warn(String className, String msg) {
        log.warn(className + " - " + msg);
    }

    public static void error(String className, String msg) {
        log.error(className + " - " + msg);
    }

    public static void fatal(String className, String msg) {
        log.fatal(className + " - " + msg);
    }

    private void test() {
        Log4jUtil log = new Log4jUtil();
        info(this.getClass().getName(), "Method: execute start");
    }

    public static void main(String[] args) {
        Log4jUtil log = new Log4jUtil();
        log.test();
    }
}
