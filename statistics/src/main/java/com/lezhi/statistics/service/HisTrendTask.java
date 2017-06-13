package com.lezhi.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.HisTrendInfo;
import com.lezhi.statistics.pojo.Log;

/**
 * Created by Cuill on 2017/3/28.
 */
@SuppressWarnings("all")
@Service
public class HisTrendTask {

    protected static final org.apache.commons.logging.Log LOGGER = LogFactory.getLog(HisTrendTask.class);

    static Calendar calendar = Calendar.getInstance();

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

    static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private DataPlatformMapper mapper;

    public void calcHour(String type, List<Log> logs, Date startTime, Date endTime) throws ParseException {
        Map<String, HisTrendInfo> map = new HashedMap();
        for (Log log : logs) {
            if (log.getMac().equals("00196843A7A3")) {
                System.out.println("正在处理log=" + log.getMac());
            }
            HisTrendInfo task = new HisTrendInfo();
            // 查询两小时内是否有session相同
            List<Log> before = mapper.selectLogBySession(log, startTime, endTime);
            if (null == before || before.size() == 0) {
                // 之前无记录，查询后面
                continue;
            } else {
                LOGGER.debug("当前mac=" + log.getMac() + "在该时间段前有记录，正在处理。。");
                task.setChannelNo(log.getChannel());
                task.setUv(mapper.selectUv(log, startTime, endTime, type));
                task.setNv(mapper.selectNv(log, startTime, endTime, type));
                task.setPv(null == mapper.selectPv(log, startTime, endTime, type) ? 0
                        : mapper.selectPv(log, startTime,endTime, type).size());
                task.setDistrictId(mapper.selectDistrictId(log));
                task.setBlockId(mapper.selectBlockId(log));
                task.setResidenceId(mapper.selectResidenceId(log));
                task.setTimeBegin(startTime);
                task.setTimeEnd(endTime);
                // 查找当前mac session 在此时间段内
                List<Log> same = mapper.selectTimeBySameMacAndSession(log, startTime);
                if (null == same || same.size() == 0) {
                    // 访问时长从当前时间点算起
                    LOGGER.debug("未找到当前数据在该时间段内同一session记录" + log.getSession());
                    task.setTotalTop(log.getTimeStamp().getTime() - startTime.getTime());
                } else
                    task.setTotalTop(log.getTimeStamp().getTime()
                            - same.get(0).getTimeStamp().getTime());

                // 保存记录
                String key = null;
                if ("district".equals(type)) {
                    key = log.getChannel() + task.getDistrictId().toString();
                }
                if ("block".equals(type)) {
                    key = log.getChannel() + task.getBlockId().toString();
                }
                if ("residence".equals(type)) {
                    key = log.getChannel() + task.getResidenceId().toString();
                }

                HisTrendInfo his = map.get(key);
                if (null != his) {
                    task.setTotalTop(his.getTotalTop() + task.getTotalTop());
                }
                map.put(key, task);
            }
        }
        // 保存信息
        saveDisInfo(map, type);
    }

    private void saveDisInfo(Map<String, HisTrendInfo> district_map, String type) {
        LOGGER.debug("calcHour_正在保存type=" + type + "记录数size=" + district_map.size());
        for (String key : district_map.keySet()) {
            mapper.saveDis(district_map.get(key), type);
        }
    }

    public void calcDay(String type, List<Log> logs, Date startTime, Date endTime) throws ParseException {
        Map<String, HisTrendInfo> map = new HashedMap();
        for (Log log : logs) {
            if (log.getMac().equals("00196843A7A3")) {
                System.out.println("正在处理log=" + log.getMac());
            }
            HisTrendInfo task = new HisTrendInfo();
            // 查询两小时内是否有session相同
            List<Log> before = mapper.selectLogBySession(log, startTime, endTime);
            if (null == before || before.size() == 0) {
                // 之前无记录，查询后面
                continue;
            } else {
                System.out.println("当前mac=" + log.getMac() + "在该时间段前有记录，正在处理。。");
                task.setChannelNo(log.getChannel());
                task.setUv(mapper.selectUv(log, startTime, endTime, type));
                task.setNv(mapper.selectNv(log, startTime, endTime, type));
                task.setPv(null == mapper.selectPv(log, startTime, endTime, type) ? 0
                        : mapper.selectPv(log, startTime, endTime, type).size());
                task.setDistrictId(mapper.selectDistrictId(log));
                task.setBlockId(mapper.selectBlockId(log));
                task.setResidenceId(mapper.selectResidenceId(log));
                task.setTimeBegin(startTime);
                task.setTimeEnd(endTime);
                // 查找当前mac session 在此时间段内
                List<Log> same = mapper.selectTimeBySameMacAndSession(log, startTime);
                if (null == same || same.size() == 0) {
                    // 访问时长从当前时间点算起
                    System.out.println("未找到当前数据在该时间段内同一session记录" + log.getSession());
                    task.setTotalTop(log.getTimeStamp().getTime() - startTime.getTime());
                } else
                    task.setTotalTop(log.getTimeStamp().getTime()
                            - same.get(0).getTimeStamp().getTime());

                // 保存记录
                String key = null;
                if ("district".equals(type)) {
                    key = log.getChannel() + task.getDistrictId().toString();
                }
                if ("block".equals(type)) {
                    key = log.getChannel() + task.getBlockId().toString();
                }
                if ("residence".equals(type)) {
                    key = log.getChannel() + task.getResidenceId().toString();
                }

                HisTrendInfo his = map.get(key);
                if (null != his) {
                    task.setTotalTop(his.getTotalTop() + task.getTotalTop());
                }
                map.put(key, task);
            }
        }
        // 保存信息
        saveDisInfo_day(map, type);
    }

    private void saveDisInfo_day(Map<String, HisTrendInfo> map, String type) {
        LOGGER.debug("calcDay_正在保存type=" + type + "记录数size=" + map.size());
        for (String key : map.keySet()) {
            mapper.saveDis_day(map.get(key), type);
        }
    }

}
