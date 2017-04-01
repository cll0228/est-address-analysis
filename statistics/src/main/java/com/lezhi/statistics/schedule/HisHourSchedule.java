package com.lezhi.statistics.schedule;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.lezhi.statistics.pojo.NewVisitorInfo;
import com.lezhi.statistics.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.Log;
import com.lezhi.statistics.task.HisTrendTask;

/**
 * Created by Cuill on 2017/3/30.
 */
@Component
public class HisHourSchedule {

    @Autowired
    private HisTrendTask hisTrendTask;

    @Autowired
    private DataPlatformMapper dataPlatformMapper;

    // 计算走势统计-历史 按小时
    @Scheduled(cron = "0 0 * * * ?")
    public void method() {
        // 获取当前时间
        // Long startTime = format.parse(DateUtil.reduce1Hour(format.format(new Date()),
        // -1)).getTime() / 1000;// 秒单位
        Long startTime = 1489420800L;
        // 当前计算结束时间
        // Long endTime = format.parse(DateUtil.reduce1Second(format.format(new
        // Date()))).getTime()/1000;
        Long endTime = 1489424399L;

        // 当前时间段内日志
        List<Log> logs = dataPlatformMapper.selectLog(startTime, endTime);
        if (null == logs)
            return;

        try {
            hisTrendTask.calc("district", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算区县数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calc("block", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算板块数据失败");
            e.printStackTrace();
        }

        try {
            hisTrendTask.calc("residence", logs, startTime, endTime);
        } catch (ParseException e) {
            System.out.println("按小时计算小区数据失败");
            e.printStackTrace();
        }

    }

    @Scheduled(cron = "${visitor.new}")
    public void newVisitor() throws Exception {
        Date date = new Date();
        String startTime = PropertyUtil.getStartMinute(date);
        // 扫描new visitor
        List<NewVisitorInfo> newVisitorInfos = dataPlatformMapper.getApiLogsInfo();
        if(newVisitorInfos.size() == 0){
            // 获取并写入日志表所有新的独立访客首次访问信息
            dataPlatformMapper.inertAllNewVistor(startTime);
        } else {
            String lastVisitTime = newVisitorInfos.get(newVisitorInfos.size()-1).getFirstVisitTime();
            String mac = newVisitorInfos.get(newVisitorInfos.size()-1).getMac();
            dataPlatformMapper.inertNewVistor(lastVisitTime, mac);
        }
    }
}
