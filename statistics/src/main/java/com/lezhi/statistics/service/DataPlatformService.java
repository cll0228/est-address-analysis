package com.lezhi.statistics.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.ListPageUtil;
import com.lezhi.statistics.util.PropertyUtil;

/**
 * Created by Cuill on 2017/3/13.
 */
@Service
@SuppressWarnings("all")
public class DataPlatformService {

    @Autowired
    private DataPlatformMapper dataPlatformMapper;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MacVisit vistHis(String channelNo, Long startTime, Long span, Integer districtId, Integer blockId,
            Integer residenceId, Integer pageNo, Integer pageSize) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        List<MacVisitHistoryInfo> infos = dataPlatformMapper.selectVistHis(channelNo, startTime, span,
                districtId, blockId,
                    residenceId);
        if (null == infos || infos.size() == 0) {
            return new MacVisit("success", new ArrayList<MacVisit>(), "");
        }
        // 分页
        MacVisit obj = new MacVisit();
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

    public RealTimeSummary realtime(String channelNo, Long period, Integer districtId, Integer blockId,
            Integer residenceId) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        RealTimeSummaryObj obj = dataPlatformMapper.realtime(channelNo, period, districtId, blockId,
                residenceId);
        if (null == obj)
            return new RealTimeSummary("success", null, "");

        RealTimeSummary summary = new RealTimeSummary();
        summary.setRealtimeSummary(obj);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public Trend trend(String channelNo, Long startTime, Long contrastiveStartTime, Long span, Long scale,
            Integer districtId, Integer blockId, Integer residenceId) {
        span = span / 1000;
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        Map<String, Object> map = new HashedMap();
        List<TrendObj> current = null;
        List<TrendObj> contrastive = null;
        if (null == startTime) {// 当前走势 包括分钟 和 小时 两种刻度
            if (scale == 3600000 * 24) {// 天刻度
                return new Trend("failed", new ArrayList<TrendObj>(), "参数不正确");
            }
            startTime = System.currentTimeMillis() / 1000;
            current = dataPlatformMapper.current(channelNo, startTime, span, scale, districtId, blockId,
                    residenceId);
            // 对比周期走势数据节点
            if (null == contrastiveStartTime) {
                contrastiveStartTime = startTime - span;
            }
            contrastive = dataPlatformMapper.contrastive(channelNo, contrastiveStartTime, span, scale,
                    districtId, blockId, residenceId);
        } else {
            startTime = startTime / 1000;
            // 非当前走势
            if (scale == 60000) {
                return new Trend("failed", new ArrayList<TrendObj>(), "参数不正确");
            }
            current = dataPlatformMapper.his_current(channelNo, startTime, span, scale, districtId, blockId,
                    residenceId);
            if (null == contrastiveStartTime) {
                contrastiveStartTime = startTime - span;
            }
            contrastive = dataPlatformMapper.his_contrastive(channelNo, contrastiveStartTime, span, scale,
                    districtId, blockId, residenceId);
        }
        // 本周期走势数据节点
        if (null == current) {
            map.put("current", new ArrayList<>());
        } else
            map.put("current", current.toArray());

        if (null == contrastive) {
            map.put("contrastive", new ArrayList<>());
        } else
            map.put("contrastive", contrastive.toArray());
        return new Trend("success", map, "");
    }

    public Summary summary(String channelNo, Long startTime, Long span, Integer pageNo, Integer pageSize) {
        List<ChannelSummaryObj> summaryObjs = dataPlatformMapper.summary(channelNo, startTime, span);
        if(null == summaryObjs || summaryObjs.size() == 0){
            return new Summary("Success", new ArrayList<ChannelSummaryObj>(), "");
        }
        // 分页
        Summary obj = new Summary();
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

    public void updateRealTimeTrendInfoByMinute() {
        Date date = new Date();
        String startTime = PropertyUtil.getStartMinute(date);
        String endTime = PropertyUtil.getEndMinute(date);
        // 更新区县实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByMinuteBlock(startTime, endTime);
        // 更新板块实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByMinuteDistrict(startTime, endTime);
        // 更新小区实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByMinuteResidence(startTime, endTime);
        // 一个小时前时间
        String stime = PropertyUtil.getBeforeHour(date);
        // 删除一个小时前数据
        dataPlatformMapper.deleteRealTimeTrendInfoByMinute(stime);

    }

    public void updateRealTimeTrendInfoByHour() {
        Date date = new Date();
        String startTime = PropertyUtil.getStartHour(date);
        String endTime = PropertyUtil.getEndHour(date);
        // 更新区县实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByHourBlock(startTime, endTime);
        // 更新板块实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByHourDistrict(startTime, endTime);
        // 更新小区实时统计数据
        dataPlatformMapper.updateRealTimeTrendInfoByHourResidence(startTime, endTime);
        // 一天前时间
        String stime = PropertyUtil.getLastDay(date);
        // 删除一天前数据
        dataPlatformMapper.deleteRealTimeTrendInfoByHour(stime);
    }
}
