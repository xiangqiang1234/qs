package com.building.ipi.common.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Administrator
 */
public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class);

    private DateUtil() {
    }

    /**
     * 将原数据sourceDate前补零，补后的总长度为指定的长度，以字符串的形式返回
     * 0 指前面补充零
     * 字符总长度为 formatLength
     * d 代表为正数
     *
     * @return
     */
    public static String frontCompWithZore(int formatLength, int sourceDate) {
        return String.format("%0" + formatLength + "d", sourceDate);
    }

    /**
     * 时间格式化 默认
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date changeDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
    }

    /**
     * 时间格式化 自定义
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date changeDate(String date, String formater) {
        SimpleDateFormat format = new SimpleDateFormat(formater);
        Date currentTime = null;
        try {
            if (date != null && !date.equals("")) {
                currentTime = format.parse(date);
            }
        } catch (ParseException e) {
            logger.info(e);
        }
        return currentTime;
    }


    //时间格式化
    public static String changeDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    //时间格式化
    public static String changeDateByDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取过去第几天的日期
     *
     * @param day
     * @return
     */
    public static Date todayAddDay(int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        Date nextDay = new Date();
        try {
            nextDay = format.parse(format.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nextDay;
    }
}
