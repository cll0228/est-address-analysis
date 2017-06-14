package com.lezhi.statistics.service;

import com.lezhi.statistics.BusinessException;
import com.lezhi.statistics.enums.TrendScaleType;
import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.ListPageUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cuill on 2017/3/13.
 */
@Service
@SuppressWarnings("all")
public class DataPlatformService {

    @Autowired
    private DataPlatformMapper dataPlatformMapper;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MacMapper macMapper;

    public HistoryListResult<MacVisitHistoryInfo> vistHis(String channelNo, long startTime, long endTime, Integer districtId, Integer blockId,
                                                          Integer residenceId, Integer pageNo, Integer pageSize) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        List<MacVisitHistoryInfo> infos = dataPlatformMapper.selectVisitHis(channelNo, new Date(startTime), new Date(endTime),
                districtId, blockId,
                    residenceId);
        if (null == infos || infos.size() == 0) {
            return new HistoryListResult("success", null, "");
        }
        // 分页
        HistoryListResult<MacVisitHistoryInfo> obj = new HistoryListResult<MacVisitHistoryInfo>();
        ListPageUtil<MacVisitHistoryInfo> listPageUtil = new ListPageUtil<>(infos, pageNo, pageSize);
        List<MacVisitHistoryInfo> pagedList = listPageUtil.getPagedList();
        obj.setHistories(pagedList);
        obj.setStatus("success");
        obj.setErrMsg("");
        // 分页信息
        if (pageNo == 1) {// 是否第一页
            obj.setIsFirstPage(true);
        } else {
            obj.setIsFirstPage(false);
        }
        // 是否最后一页
        int totalPage = listPageUtil.getTotalPage();
        obj.setTotalPageCount(totalPage);
        obj.setTotalCount(infos.size());
        if (pageNo == totalPage) {
            obj.setIsLastPage(true);
        } else {
            obj.setIsLastPage(false);
        }
        obj.setPageNo(pageNo);
        obj.setPageSize(pageSize);
        obj.setRealPageSize(pagedList.size());
        return obj;
    }

    private synchronized RealTimeSummaryObj refreshRealtimeSummary(String channelNo, Long period, Integer districtId, Integer blockId, Integer residenceId, Date now) {
        Date timeFrom = new Date(now.getTime() - period);
        List<MacVisitLogInfo> logs = this.macMapper.selectForRealtimeSummary(channelNo, districtId, blockId, residenceId, timeFrom, now);
        Integer nv = macMapper.nvForRealtimeSummary(channelNo, districtId, blockId, residenceId, timeFrom, now);
        if (logs == null)
            logs = new ArrayList<>();

        RealTimeSummaryObj obj = new RealTimeSummaryObj();
        obj.setRefreshTime(now);
        obj.setPv(logs.size());
        Set<String> macs = new HashSet<>();
        for (MacVisitLogInfo info : logs) {
            macs.add(info.getMac());
        }
        obj.setUv(macs.size());
        obj.setNv(nv);
        obj.setId(1);
        return obj;
    }

    private RealTimeSummaryObj checkRealtimeSummary(String channelNo, Long period, Integer districtId, Integer blockId, Integer residenceId, RealTimeSummaryObj obj) {
        Date now = DateUtils.truncate(new Date(), Calendar.MINUTE);
        if (obj != null) {
            if (obj.getRefreshTime().getTime() > now.getTime()) {
                return obj;
            } else if (obj.getRefreshTime().getTime() == now.getTime()) {
                return obj;
            } else {
                return refreshRealtimeSummary(channelNo, period, districtId, blockId, residenceId, now);
            }
        }
        return refreshRealtimeSummary(channelNo, period, districtId, blockId, residenceId, now);
    }

    public RealTimeSummaryResult realtimeSummary(String channelNo, Long period, Integer districtId, Integer blockId,
                                                 Integer residenceId) {
        RealTimeSummaryObj result = checkRealtimeSummary(channelNo, period, districtId, blockId, residenceId, null);
        if (null == result)
            return new RealTimeSummaryResult("success", null, "");

        RealTimeSummaryResult summary = new RealTimeSummaryResult();
        summary.setRealtimeSummary(result);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public TrendResult realtimeTrend(String channelNo, final TrendScaleType type,
                             Integer districtId, Integer blockId, Integer residenceId) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }

        Map<String, List<TrendObj>> trendData = new HashMap<>();

        // 当前走势 包括分钟 和 小时 两种刻度

        if (type == TrendScaleType.minute) {
            trendData = calcTrendInfoByMinute(channelNo, districtId, blockId, residenceId);
        } else {
            Date startTime = DateUtils.addHours(DateUtils.truncate(new Date(), Calendar.HOUR_OF_DAY), -24);
            Date contrastiveStartTime = DateUtils.addDays(startTime, -1);

            trendData = calcTrendInfoByHour(startTime, contrastiveStartTime, 24, channelNo, districtId, blockId, residenceId);
        }

        return new TrendResult("success", trendData, "");
    }

    public TrendResult historyTrend(String channelNo, long _startTime, Long _contrastiveStartTime, final long span, TrendScaleType type,
                                    Integer districtId, Integer blockId, Integer residenceId) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }

        Map<String, List<TrendObj>> trendData = new HashMap<>();

        // 当前走势 包括分钟 和 小时 两种刻度

        if (type == TrendScaleType.hour) {
            Date startTime = DateUtils.truncate(new Date(_startTime), Calendar.HOUR_OF_DAY);
            int hours = (int) (span / 1000 / 60 / 60);
            Date endTime = DateUtils.addHours(startTime, hours);
            if (endTime.getTime() <= startTime.getTime()) {
                throw new BusinessException("没有跨小时");
            }
            Date contrastiveStartTime;
            if (null == _contrastiveStartTime) {
                contrastiveStartTime = DateUtils.addHours(startTime, -hours);
            } else {
                contrastiveStartTime = DateUtils.truncate(new Date(_contrastiveStartTime), Calendar.HOUR_OF_DAY);
            }

            Date contrastiveEndTime = DateUtils.addHours(contrastiveStartTime, hours);
            trendData = calcTrendInfoByHour(startTime, contrastiveStartTime, hours, channelNo, districtId, blockId, residenceId);
        } else {
            Date startTime = DateUtils.truncate(new Date(_startTime), Calendar.DAY_OF_MONTH);
            int days = (int) (span / 1000 / 60 / 60 / 24);
            Date endTime = DateUtils.addDays(startTime, days);
            if (endTime.getTime() <= startTime.getTime()) {
                throw new BusinessException("没有跨day");
            }
            Date contrastiveStartTime;
            if (null == _contrastiveStartTime) {
                contrastiveStartTime = DateUtils.addDays(startTime, -days);
            } else {
                contrastiveStartTime = DateUtils.truncate(_contrastiveStartTime, Calendar.DAY_OF_MONTH);
            }

            Date contrastiveEndTime = DateUtils.addDays(contrastiveStartTime, days);

            trendData = calcTrendInfoByDay(startTime, contrastiveStartTime, days, channelNo, districtId, blockId, residenceId);
        }
        return new TrendResult("success", trendData, "");
    }

    public SummaryResult channelSummary(String channelNo, long startTime, long endTime, Integer pageNo, Integer pageSize) {
        List<ChannelSummaryObj> summaryObjs = dataPlatformMapper.channelSummary(channelNo, new Date(startTime), new Date(endTime));
        if (null == summaryObjs || summaryObjs.size() == 0) {
            return new SummaryResult("success", new ArrayList<ChannelSummaryObj>(), "");
        }
        // 分页
        SummaryResult obj = new SummaryResult();
        ListPageUtil<ChannelSummaryObj> listPageUtil = new ListPageUtil<>(summaryObjs, pageNo, pageSize);
        List<ChannelSummaryObj> pagedList = listPageUtil.getPagedList();
        obj.setChannelSummaries(pagedList);
        obj.setStatus("success");
        obj.setErrMsg("");
        // 分页信息
        if (pageNo == 1) {// 是否第一页
            obj.setIsFirstPage(true);
        } else {
            obj.setIsFirstPage(false);
        }
        // 是否最后一页
        int totalPage = listPageUtil.getTotalPage();
        obj.setTotalPageCount(totalPage);
        obj.setTotalCount(summaryObjs.size());
        if (pageNo == totalPage) {
            obj.setIsLastPage(true);
        } else {
            obj.setIsLastPage(false);
        }
        obj.setPageNo(pageNo);
        obj.setPageSize(pageSize);
        obj.setRealPageSize(pagedList.size());
        return obj;
    }

    private void fillIn(Map<Integer, List<MacVisitLogInfo>> map, int size) {
        for (int i = 0; i < size; i++) {
            map.put(i, new ArrayList<>());
        }
    }

    private void fillUvAndNv(List<MacVisitLogInfo> logs, TrendObj t) {
        Set<String> macs = new HashSet<>();
        int nv = 0;
        for (MacVisitLogInfo log : logs) {
            if (!macs.contains(log.getMac())) {
                macs.add(log.getMac());
                if (log.getFirstVisitIdIfEverVisited()!=null && log.getFirstVisitIdIfEverVisited() > 0)
                    nv++;
            }
        }
        t.setUv(macs.size());
        t.setNv(nv);
    }

    private void box(Map<Integer, List<MacVisitLogInfo>> map, List<MacVisitLogInfo> logs, Date from, long scale, int maxIndexExclude) {
        for (MacVisitLogInfo log : logs) {
            long dis = log.getTime().getTime() - from.getTime();
            int index = (int) (dis / scale);
            if (index >= maxIndexExclude) {
                throw new RuntimeException();
            }
            map.get(index).add(log);
        }
    }

    public Map<String, List<TrendObj>> calcTrendInfoByMinute(String channelNo, Integer districtId, Integer blockId,
                                                             Integer residenceId) {
        Date now = DateUtils.truncate(new Date(), Calendar.MINUTE);
        Date from = DateUtils.addHours(now, -2);

        List<MacVisitLogInfo> logs = this.macMapper.getMacVisitLog(null, channelNo, from, now,
                districtId, blockId, residenceId, null);

        Map<Integer, List<MacVisitLogInfo>> map = new HashMap<>();
        fillIn(map, 120);
        for (MacVisitLogInfo log : logs) {
            long dis = log.getTime().getTime() - from.getTime();
            int index = (int )(dis / 1000 / 60);
            if (index >= 120) {
                throw new RuntimeException();
            }
            map.get(index).add(log);
        }

        List<TrendObj> current = new ArrayList<>();
        List<TrendObj> contrastive = new ArrayList<>();

        Map<String, List<TrendObj>> result = new HashMap<>();
        result.put("current", current);
        result.put("contrastive", contrastive);

        for (int i = 0; i < 120 ; i ++) {
            Date begin = DateUtils.addMinutes(from, i);   // 0 ~ 59
            Date end = DateUtils.addMinutes(from, i + 1);   // 0 ~ 59

            TrendObj t = new TrendObj();
            t.setTimeBegin(begin);
            t.setTimeEnd(end);
            t.setPv(map.get(i).size());
            t.setAvgTop(0L);

            fillUvAndNv(map.get(i), t);

            if (i < 60) {
                contrastive.add(t);
            } else {
                current.add(t);
            }
        }

        return result;
    }

    public Map<String, List<TrendObj>> calcTrendInfoByHour(Date startTime, Date contrastiveStartTime, int hours,
                                                           String channelNo, Integer districtId, Integer blockId,
                                                           Integer residenceId) {
        if (hours < 1) {
            throw new BusinessException("span没有跨小时");
        }
        if (startTime == null || contrastiveStartTime == null)
            throw new IllegalArgumentException();

        startTime = DateUtils.truncate(startTime, Calendar.HOUR_OF_DAY);
        Date endTime = DateUtils.addHours(startTime, hours);
        contrastiveStartTime = DateUtils.truncate(contrastiveStartTime, Calendar.HOUR_OF_DAY);
        Date contrastiveEndTime = DateUtils.addHours(contrastiveStartTime, hours);

        List<MacVisitLogInfo> currentLogs = this.macMapper.getMacVisitLog(null, channelNo, startTime, endTime, districtId, blockId, residenceId, null);
        List<MacVisitLogInfo> contrastiveLogs = this.macMapper.getMacVisitLog(null, channelNo, contrastiveStartTime, contrastiveEndTime, districtId, blockId, residenceId, null);

        Map<Integer, List<MacVisitLogInfo>> currentMap = new HashMap<>();
        Map<Integer, List<MacVisitLogInfo>> contrastiveMap = new HashMap<>();

        this.fillIn(currentMap, hours);
        this.fillIn(contrastiveMap, hours);

        box(currentMap, currentLogs, startTime, (long )(1000 * 60 * 60), hours);
        box(contrastiveMap, contrastiveLogs, contrastiveStartTime, (long )(1000 * 60 * 60), hours);

        List<TrendObj> current = new ArrayList<>();
        List<TrendObj> contrastive = new ArrayList<>();

        Map<String, List<TrendObj>> result = new HashMap<>();
        result.put("current", current);
        result.put("contrastive", contrastive);

        for (int i = 0; i < hours ; i ++) {
            Date begin = DateUtils.addHours(startTime, i);   // 0 ~ 59
            Date end = DateUtils.addHours(startTime, i + 1);   // 0 ~ 59

            TrendObj t = new TrendObj();
            t.setTimeBegin(begin);
            t.setTimeEnd(end);
            t.setPv(currentMap.get(i).size());
            t.setAvgTop(0L);    //TODO
            fillUvAndNv(currentMap.get(i), t);
            current.add(t);
        }
        for (int i = 0; i < hours ; i ++) {
            Date begin = DateUtils.addHours(contrastiveStartTime, i);   // 0 ~ 59
            Date end = DateUtils.addHours(contrastiveStartTime, i + 1);   // 0 ~ 59

            TrendObj t = new TrendObj();
            t.setTimeBegin(begin);
            t.setTimeEnd(end);
            t.setPv(contrastiveMap.get(i).size());
            t.setAvgTop(0L);    //TODO
            fillUvAndNv(contrastiveMap.get(i), t);
            contrastive.add(t);
        }

        return result;
    }


    public Map<String, List<TrendObj>> calcTrendInfoByDay(Date startTime, Date contrastiveStartTime, int days, String channelNo, Integer districtId, Integer blockId,
                                                           Integer residenceId) {
        if (days < 1) {
            throw new BusinessException("没有跨天");
        }
        if (startTime == null || contrastiveStartTime == null)
            throw new IllegalArgumentException();

        startTime = DateUtils.truncate(startTime, Calendar.DAY_OF_MONTH);
        Date endTime = DateUtils.addDays(startTime, days);
        contrastiveStartTime = DateUtils.truncate(contrastiveStartTime, Calendar.DAY_OF_MONTH);
        Date contrastiveEndTime = DateUtils.addDays(contrastiveStartTime, days);

        List<MacVisitLogInfo> currentLogs = this.macMapper.getMacVisitLog(null, channelNo, startTime, endTime, districtId, blockId, residenceId, null);
        List<MacVisitLogInfo> contrastiveLogs = this.macMapper.getMacVisitLog(null, channelNo, contrastiveStartTime, contrastiveEndTime, districtId, blockId, residenceId, null);

        Map<Integer, List<MacVisitLogInfo>> currentMap = new HashMap<>();
        Map<Integer, List<MacVisitLogInfo>> contrastiveMap = new HashMap<>();

        this.fillIn(currentMap, days);
        this.fillIn(contrastiveMap, days);

        box(currentMap, currentLogs, startTime, (long )(1000 * 60 * 60 * 24), days);
        box(contrastiveMap, contrastiveLogs, contrastiveStartTime, (long )(1000 * 60 * 60 * 24), days);

        List<TrendObj> current = new ArrayList<>();
        List<TrendObj> contrastive = new ArrayList<>();

        Map<String, List<TrendObj>> result = new HashMap<>();
        result.put("current", current);
        result.put("contrastive", contrastive);

        for (int i = 0; i < days ; i ++) {
            Date begin = DateUtils.addDays(startTime, i);   // 0 ~ 59
            Date end = DateUtils.addDays(startTime, i + 1);   // 0 ~ 59

            TrendObj t = new TrendObj();
            t.setTimeBegin(begin);
            t.setTimeEnd(end);
            t.setPv(currentMap.get(i).size());
            t.setAvgTop(0L);    //TODO
            fillUvAndNv(currentMap.get(i), t);
            current.add(t);
        }
        for (int i = 0; i < days ; i ++) {
            Date begin = DateUtils.addDays(contrastiveStartTime, i);   // 0 ~ 59
            Date end = DateUtils.addDays(contrastiveStartTime, i + 1);   // 0 ~ 59

            TrendObj t = new TrendObj();
            t.setTimeBegin(begin);
            t.setTimeEnd(end);
            t.setPv(contrastiveMap.get(i).size());
            t.setAvgTop(0L);    //TODO
            fillUvAndNv(contrastiveMap.get(i), t);
            contrastive.add(t);
        }

        return result;
    }

}
