package com.building.ipi.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 公共方法类
 *
 * @author yuzj  2019-03-28
 */
public class CommonDateUtils {

    private CommonDateUtils() {

    }

    private static final Log logger = LogFactory.getLog(CommonDateUtils.class);


    /**
     * 获取两个日期之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDifferDay(Date startDate, Date endDate) {
        return (int)(endDate.getTime()/1000/60/60/24 - startDate.getTime()/1000/60/60/24);
    }

    /**
     * 计算 日期date day天之后的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        Date nowDate = (Date) date.clone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long myTime = (nowDate.getTime() / 1000);
        myTime = myTime + day * 24 * 60 * 60;
        nowDate.setTime(myTime * 1000);
        try {
            nowDate = sdf.parse(sdf.format(nowDate));
        } catch (ParseException e) {
            logger.error("dateAddDay  日期转换错误", e);
        }
        return nowDate;
    }

    /**
     * 计算 日期date num月之后的日期
     *
     * @param date
     * @param num
     * @return
     */
    public static Date dateAddMonth(Date date, int num) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        //date的月份
        int month = calendar.get(Calendar.MONTH);
        //让日期加num
        calendar.set(Calendar.MONTH,month+num);
        return calendar.getTime();
    }

    /**
     * 字符串转日期
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date stringToDate(String dateStr,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("stringToDate  日期转换错误", e);
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期比较大小  date1 > date2 返回 true
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean compareDate(Date date1,Date date2) {
        return (date1.getTime()/1000/60/60/24)>(date2.getTime()/1000/60/60/24);
    }

    /**
     * 时间比较大小  date1 > date2 返回 true
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Boolean compareDateTime(Date date1,Date date2) {
        return date1.getTime()>date2.getTime();
    }
}
