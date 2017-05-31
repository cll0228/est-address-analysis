package com.lezhi.statistics.schedule;

import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.mapper.ScheduleTaskExecMapper;
import com.lezhi.statistics.pojo.ChannelVisitSummary;
import com.lezhi.statistics.pojo.MacVisitLogInfo;
import com.lezhi.statistics.pojo.ScheduleTaskExec;
import com.lezhi.statistics.service.LogService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Colin Yan on 2017/5/31.
 */
@Component
public class LogParserSchedule {

    @Autowired
    private MacMapper macMapper;
    @Autowired
    private ScheduleTaskExecMapper scheduleTaskExecMapper;
    @Autowired
    private LogService logService;

    // every hour
    @Scheduled(cron = "${log.prepare}")
    @Transactional
    //@Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 0)
    public synchronized void parse() throws Exception {
        ScheduleTaskExec taskExec = scheduleTaskExecMapper.findById(1);
        Integer minIdExclude = taskExec.getRefId();
        List<MacVisitLogInfo> logs = this.macMapper.getMacVisitLog(null, null, null, null, null, null, null, minIdExclude);
        if (logs == null)
            return;

        // time, mac, channel
        Map<Date, Map<String, Map<String, List<MacVisitLogInfo>>>> map = new HashMap<>();

        int maxId = 0;
        for (MacVisitLogInfo info : logs) {
            if (info.getId() > maxId)
                maxId = info.getId();

            Map<String, Map<String, List<MacVisitLogInfo>>> partMap;
            Date hourDate = DateUtils.truncate(new Date(info.getTime()), Calendar.HOUR_OF_DAY);
            if (map.containsKey(hourDate)) {
                partMap = map.get(hourDate);
            } else {
                partMap = new HashMap<>();
                map.put(hourDate, partMap);
            }

            Map<String, List<MacVisitLogInfo>> channelMap = null;

            if (partMap.containsKey(info.getMac())) {
                channelMap = partMap.get(info.getMac());
            } else {
                channelMap = new HashMap<>();
                partMap.put(info.getMac(), channelMap);
            }

            List<MacVisitLogInfo> list;

            if (channelMap.containsKey(info.getChannelNo())) {
                list = channelMap.get(info.getChannelNo());
            } else {
                list = new ArrayList<>();
                channelMap.put(info.getChannelNo(), list);
            }

            list.add(info);
        }

        List<ChannelVisitSummary> channelVisitSummaries = new ArrayList<>();

        for (Map.Entry<Date, Map<String, Map<String, List<MacVisitLogInfo>>>> m : map.entrySet()) {
            for (Map.Entry<String, Map<String, List<MacVisitLogInfo>>> b : m.getValue().entrySet()) {
                for (Map.Entry<String, List<MacVisitLogInfo>> n : b.getValue().entrySet()) {
                    if (n.getValue().size() == 0)
                        continue;

                    MacVisitLogInfo log = n.getValue().get(0);

                    ChannelVisitSummary s = new ChannelVisitSummary();
                    s.setMac(log.getMac());
                    s.setPv(n.getValue().size());
                    s.setResidenceId(log.getResidenceId());
                    s.setBlockId(log.getBlockId());
                    s.setDistrictId(log.getDistrictId());
                    s.setChannelNo(log.getChannelNo());
                    s.setTotalTop(0L);   //TODO
                    s.setBeginTime(m.getKey());
                    s.setEndTime(DateUtils.addSeconds(DateUtils.addHours(m.getKey(), 1), -1));
                    channelVisitSummaries.add(s);
                }
            }
        }
        logService.insertSummary(channelVisitSummaries);
        taskExec.setId(1);
        taskExec.setRefId(900);
        taskExec.setLastExecTime(new Date());
        scheduleTaskExecMapper.updateById(taskExec);
    }
}
