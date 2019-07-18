package com.building.ipi.web.timer;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 定时任务
 * @author: yuzj
 * @Date: 2019/03/30 13:52
 */
@Component
public class AddTask {
    private Logger logger = Logger.getLogger(getClass());


    /**
     * 每天凌晨0点30分 执行
     */
    @Scheduled(cron = "0 30 0 ? * 1-7")
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            logger.info(sdf.format(new Date())+"    开始生成任务");
            logger.info(sdf.format(new Date())+"    结束生成任务");
        }catch (Exception e){
            logger.info("定时自动生成巡检任务失败  操作时间："+sdf.format(new Date()));
        }
    }
}
