package com.lezhi.statistics.service;

import java.util.ArrayList;
import java.util.List;

import com.lezhi.statistics.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.util.ListPageUtil;

/**
 * Created by Cuill on 2017/3/13.
 */
@Service
@SuppressWarnings("all")
public class DataPlatformService {

    @Autowired
    private DataPlatformMapper dataPlatformMapper;

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000);
    }

    public MacVisit vistHis(String channelNo, Long startTime, Long span, Integer districtId, Integer blockId,
            Integer residenceId, Integer pageNo, Integer pageSize) {
        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null == residenceId && null != blockId) {
            districtId = null;
        }
        List<MacVisitHistoryInfo> infos = dataPlatformMapper.selectVistHis(channelNo, startTime, span,
                districtId, blockId, residenceId);
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
        obj.setPageSize(totalPage);
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
        RealTimeSummary summary = new RealTimeSummary();
        summary.setRealtimeSummary(obj);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }
}
