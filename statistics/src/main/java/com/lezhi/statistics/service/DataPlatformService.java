package com.lezhi.statistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.ListPageUtil;

/**
 * Created by Cuill on 2017/3/13.
 */
@Service
@SuppressWarnings("all")
public class DataPlatformService {

    @Autowired
    private DataPlatformMapper dataPlatformMapper;

    public MacVisit vistHis(String channelNo, Long startTime, Long span, Integer districtId, Integer blockId,
            Integer residenceId, Integer pageNo, Integer pageSize) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        List<MacVisitHistoryInfo> infos = null;
        try {
            infos = dataPlatformMapper.selectVistHis(channelNo, startTime, span, districtId, blockId,
                    residenceId);
        } catch (Exception e) {
            return new MacVisit("success", new ArrayList<MacVisit>(), "");
        }
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
            obj.setFirstPage(true);
        } else {
            obj.setFirstPage(false);
        }
        // 是否最后一页
        int totalPage = listPageUtil.getTotalPage();
        obj.setTotalPageCount(totalPage);
        if (pageNo == totalPage) {
            obj.setLastPage(true);
        } else {
            obj.setLastPage(false);
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
        RealTimeSummaryObj obj = null;
        try {
            obj = dataPlatformMapper.realtime(channelNo, period, districtId, blockId, residenceId);
        } catch (Exception e) {
            return new RealTimeSummary("success", null, "");
        }
        RealTimeSummary summary = new RealTimeSummary();
        summary.setRealtimeSummary(obj);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public Trend trend(String channelNo, Long startTime, Long contrastiveStartTime, Long span, Long scale,
            Integer districtId, Integer blockId, Integer residenceId) {
        if (null == startTime) {
            startTime = System.currentTimeMillis() / 1000;// unix时间戳
        }
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        Map<String, Object> map = new HashedMap();
        // 本周期走势数据节点
        List<TrendObj> current = dataPlatformMapper.current(channelNo, startTime, span, scale, districtId,
                blockId, residenceId);
        if (null == current) {
            map.put("current", new ArrayList<>());
        } else
            map.put("current", current.toArray());

        // 对比周期走势数据节点
        if(null == contrastiveStartTime){
            contrastiveStartTime = startTime - span;
        }
        List<TrendObj> contrastive = dataPlatformMapper.contrastive(channelNo, contrastiveStartTime, span,
                scale, districtId, blockId, residenceId);
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
            obj.setFirstPage(true);
        } else {
            obj.setFirstPage(false);
        }
        // 是否最后一页
        int totalPage = listPageUtil.getTotalPage();
        obj.setTotalPageCount(totalPage);
        if (pageNo == totalPage) {
            obj.setLastPage(true);
        } else {
            obj.setLastPage(false);
        }
        obj.setPageNo(pageNo);
        obj.setPageSize(pageSize);
        obj.setRealPageSize(pagedList.size());
        return obj;
    }
}
