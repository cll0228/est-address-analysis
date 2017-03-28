package com.lezhi.statistics.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.service.DataPlatformService;

/**
 * Created by Cuill on 2017/3/13. 数据平台
 */
@Controller
@RequestMapping
public class DataPlatformController {

    @Autowired
    private DataPlatformService dataPlatformService;

    /**
     * 8.机顶盒收视历史
     * 
     * @param channelNo
     * @param startTime
     * @param span
     * @param districtId
     * @param blockId
     * @param residenceId
     * @return
     */
    @RequestMapping(value = "mac/visit/history")
    @ResponseBody
    private MacVisit vistHis(@RequestParam(value = "channelNo") String channelNo,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "span") Long span,
            @RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "blockId", required = false) Integer blockId,
            @RequestParam(value = "residenceId", required = false) Integer residenceId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (null == channelNo || "".equals(channelNo) || null == span) {
            // 提示必填参数不能为空
            return new MacVisit("failed", new ArrayList<MacVisit>(), "必填参数不能为空");
        }
        if (null == span) {
            span = System.currentTimeMillis() / 1000;// unix时间戳
        }
        if (null == pageNo) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize <= 0) {
            pageSize = 20;
        }

        return dataPlatformService.vistHis(channelNo, startTime, span, districtId, blockId, residenceId,
                pageNo, pageSize);

    }

    /**
     * 3.	实时概况
     * @param channelNo
     * @param period
     * @param districtId
     * @param blockId
     * @param residenceId
     * @return
     */
    @RequestMapping(value = "realtime/summary")
    @ResponseBody
    public RealTimeSummary realtime(@RequestParam(value = "channelNo") String channelNo,
            @RequestParam(value = "period") Long period,
            @RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "blockId", required = false) Integer blockId,
            @RequestParam(value = "residenceId", required = false) Integer residenceId) {
        if (period != 60 && period != 300 && period != 900) {
            return new RealTimeSummary("failed", new ArrayList<RealTimeSummaryObj>(), "参数不正确");
        }
        return dataPlatformService.realtime(channelNo, period, districtId, blockId, residenceId);
    }

    /**
     * 走势统计(4)
     * @param channelNo
     * @param startTime
     * @param contrastiveStartTime
     * @param span
     * @param scale
     * @param districtId
     * @param blockId
     * @param residenceId
     * @return
     */
    @RequestMapping(value = "trend")
    @ResponseBody
    public Trend trend(@RequestParam(value = "channelNo", required = false) String channelNo,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "contrastiveStartTime", required = false) Long contrastiveStartTime,
            @RequestParam(value = "span") Long span, @RequestParam(value = "scale") Long scale,
            @RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "blockId", required = false) Integer blockId,
            @RequestParam(value = "residenceId", required = false) Integer residenceId) {
        if (null == span || null == scale) {
            return new Trend("failed", new ArrayList<TrendObj>(), "参数不正确");
        }
        return dataPlatformService.trend(channelNo, startTime, contrastiveStartTime, span, scale, districtId,
                blockId, residenceId);
    }

    /**
     * 9.	频道访问量统计
     * @param channelNo
     * @param startTime
     * @param span
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "channel/summary")
    @ResponseBody
    public Summary summary(@RequestParam(value = "channelNo", required = false) String channelNo,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "span") Long span, @RequestParam(value = "pageNo",required = false) Integer pageNo,
            @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        if (null == span) {
            return new Summary("failed", new ArrayList<ChannelSummaryObj>(), "参数不正确");
        }
        if (null == pageNo || pageNo <= 0) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize <= 0) {
            pageSize = 20;
        }
        if(null == startTime){
            startTime = System.currentTimeMillis()/1000;
        }
        return dataPlatformService.summary(channelNo, startTime, span, pageNo, pageSize);
    }

}
