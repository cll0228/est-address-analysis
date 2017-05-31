package com.lezhi.statistics.schedule;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.Log;
import com.lezhi.statistics.service.HisTrendTask;
import com.lezhi.statistics.util.DateUtil;
import com.lezhi.statistics.util.PropertyUtil;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Cuill on 2017/3/30.
 */
@SuppressWarnings("all")
@Component
public class HisHourSchedule {

    @Autowired
    private HisTrendTask hisTrendTask;

    @Autowired
    private DataPlatformMapper dataPlatformMapper;

    static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 计算走势统计-历史 按小时
    @Scheduled(cron = "0 0 * * * ?")
    public void method() {
        // 获取当前时间
        Long startTime = null;
        Long endTime = null;
        try {
            startTime = format1.parse(DateUtil.reduce1Hour(format1.format(new Date()), -1)).getTime() / 1000;// 秒单位
//              startTime = 1496210400L;
            System.out.println("当前开始时间startTime= " + format1.format(new Date(startTime * 1000)));
            // 当前计算结束时间
            endTime = format1.parse(DateUtil.reduce1Second(format1.format(new Date()))).getTime() / 1000;
//              endTime = 1496213999L;
        } catch (Exception e) {
            System.out.println("获取时间失败time = " + new Date());
        }
        System.out.println("当前统计结束时间endTime=" + format1.format(new Date(endTime * 1000)));

        // 当前时间段内日志
        List<Log> logs = dataPlatformMapper.selectLog(startTime, endTime);
        if (null == logs || logs.size() == 0)
            return;

        try {
            hisTrendTask.calcHour("district", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算区县数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calcHour("block", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算板块数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calcHour("residence", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算小区数据失败");
            e.printStackTrace();
        }

    }

    /**
     * 按照天计算走势统计-历史
     */
    @Scheduled(cron = "0 0 0 * * ?") // 每天0点触发
    public void method1() {
        // 获取当前时间
        Long startTime = null;
        Long endTime = null;
        try {
            startTime = format1.parse(DateUtil.updateDay(new Date(), -1)).getTime() / 1000;// 秒单位
            System.out.println("当前开始时间startTime= " + format1.format(new Date(startTime * 1000)));
            // 当前计算结束时间
            endTime = format1.parse(DateUtil.reduce1Second(format1.format(new Date()))).getTime() / 1000;
            System.out.println("当前统计结束时间endTime=" + format1.format(new Date(endTime * 1000)));
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("解析日期失败！");
            return;
        }

        // 当前时间段内日志
        List<Log> logs = dataPlatformMapper.selectLog(startTime, endTime);
        if (null == logs || logs.size() == 0)
            return;

        try {
            hisTrendTask.calcDay("district", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按天计算区县数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calcDay("block", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按天计算板块数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calcDay("residence", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按天计算小区数据失败");
            e.printStackTrace();
        }

    }

}
