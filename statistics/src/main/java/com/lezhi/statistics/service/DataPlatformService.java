package com.lezhi.statistics.service;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.ListPageUtil;
import com.lezhi.statistics.util.PropertyUtil;
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

    public HistoryListResult<MacVisitHistoryInfo> vistHis(String channelNo, long startTime, long span, Integer districtId, Integer blockId,
                                                          Integer residenceId, Integer pageNo, Integer pageSize) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        List<MacVisitHistoryInfo> infos = dataPlatformMapper.selectVistHis(channelNo, startTime / 1000, span / 1000,
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
        obj.setRefreshTime(now.getTime());
        obj.setPv(logs.size());
        Set<String> macs = new HashSet<>();
        for (MacVisitLogInfo info : logs) {
            macs.add(info.getMac());
        }
        obj.setUv(macs.size());
        obj.setNv(nv);
        obj.setId(1);
        // TODO persist to db
        return obj;
    }

    private RealTimeSummaryObj checkRealtimeSummary(String channelNo, Long period, Integer districtId, Integer blockId, Integer residenceId, RealTimeSummaryObj obj) {
        Date now = DateUtils.truncate(new Date(), Calendar.MINUTE);
        if (obj != null) {
            if (obj.getRefreshTime() > now.getTime()) {
                dataPlatformMapper.putDownRealtimeSummary(obj.getId(), now);
                return obj;
            } else if (obj.getRefreshTime() == now.getTime()) {
                return obj;
            } else {
                return refreshRealtimeSummary(channelNo, period, districtId, blockId, residenceId, now);
            }
        }
        return refreshRealtimeSummary(channelNo, period, districtId, blockId, residenceId, now);
    }

    public RealTimeSummaryResult realtimeSummary(String channelNo, Long period, Integer districtId, Integer blockId,
                                                 Integer residenceId) {
        RealTimeSummaryObj obj = dataPlatformMapper.realtime(channelNo, period, districtId, blockId, residenceId);

        RealTimeSummaryObj result = checkRealtimeSummary(channelNo, period, districtId, blockId, residenceId, obj);
        if (null == result)
            return new RealTimeSummaryResult("success", null, "");

        RealTimeSummaryResult summary = new RealTimeSummaryResult();
        summary.setRealtimeSummary(result);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public TrendResult trend(String channelNo, Long startTime, Long contrastiveStartTime, final long span, Long scale,
                             Integer districtId, Integer blockId, Integer residenceId) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        Map<String, List<TrendObj>> map = new HashMap<>();
        List<TrendObj> current = null;
        List<TrendObj> contrastive = null;

        boolean isRealTime = false;

        if (null == startTime) {
            startTime = System.currentTimeMillis() - span;
            isRealTime = true;
        }
        if (null == contrastiveStartTime) {
            contrastiveStartTime = startTime - span;
        }

        if (isRealTime) {// 当前走势 包括分钟 和 小时 两种刻度
            current = dataPlatformMapper.current(channelNo, startTime / 1000, span / 1000, scale, districtId, blockId,
                    residenceId);
            contrastive = dataPlatformMapper.contrastive(channelNo, contrastiveStartTime / 1000, span / 1000, scale,
                    districtId, blockId, residenceId);
        } else {
            current = dataPlatformMapper.his_current(channelNo, startTime / 1000, span / 1000, scale, districtId, blockId,
                    residenceId);
            contrastive = dataPlatformMapper.his_contrastive(channelNo, contrastiveStartTime / 1000, span / 1000, scale,
                    districtId, blockId, residenceId);
        }
        // 本周期走势数据节点
        if (null == current) {
            map.put("current", new ArrayList<>());
        } else
            map.put("current", current);

        if (null == contrastive) {
            map.put("contrastive", new ArrayList<>());
        } else
            map.put("contrastive", contrastive);
        return new TrendResult("success", map, "");
    }

    public SummaryResult summary(String channelNo, long startTime, long span, Integer pageNo, Integer pageSize) {
        List<ChannelSummaryObj> summaryObjs = dataPlatformMapper.summary(channelNo, startTime / 1000, span / 1000);
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

    public void updateRealTimeTrendInfoByMinute() throws Exception {
        Date date = new Date();
        // 一个小时前时间
        String stime = PropertyUtil.getBeforeHour(date);
        Date startDate = null;
        String startTime = null;
        String endTime = null;
        for (int i = 0; i < 60; i++) {
            if (i == 0) {
                startTime = stime;
            } else {
                startTime = PropertyUtil.getNextMinute(startDate);
            }
            startDate = df.parse(startTime);
            endTime = PropertyUtil.getEndMinute(startDate);
            // 更新区县实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByMinuteBlock(startTime, endTime);
            // 更新板块实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByMinuteDistrict(startTime, endTime);
            // 更新小区实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByMinuteResidence(startTime, endTime);
        }
        // 删除一个小时前数据
        dataPlatformMapper.deleteRealTimeTrendInfoByMinute(stime);

    }

    public void updateRealTimeTrendInfoByHour() throws Exception {
        Date date = new Date();
        // 一天前时间
        String stime = PropertyUtil.getLastDay(date);
        Date startDate = null;
        String startTime = null;
        String endTime = null;
        for (int i = 0; i < 24; i++) {
            if (i == 0) {
                startTime = stime;
            } else {
                startTime = PropertyUtil.getNextHour(startDate);
            }
            startDate = df.parse(startTime);
            endTime = PropertyUtil.getEndHour(startDate);
            System.out.println("stime=" + startTime + ", " + "etime=" + endTime);
            // 更新区县实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByHourBlock(startTime, endTime);
            // 更新板块实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByHourDistrict(startTime, endTime);
            // 更新小区实时统计数据
            dataPlatformMapper.updateRealTimeTrendInfoByHourResidence(startTime, endTime);
        }
        // 删除一天前数据
        dataPlatformMapper.deleteRealTimeTrendInfoByHour(stime);
    }
}
