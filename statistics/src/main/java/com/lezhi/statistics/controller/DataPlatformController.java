package com.lezhi.statistics.controller;

import com.lezhi.statistics.enums.TrendScaleType;
import com.lezhi.statistics.mock.CommonMockService;
import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.service.DataPlatformService;
import com.lezhi.statistics.util.EnvUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cuill on 2017/3/13. 数据平台
 */
@Controller
@RequestMapping
public class DataPlatformController extends BaseController {

    @Autowired
    private DataPlatformService dataPlatformService;

    @Autowired
    private CommonMockService commonMockService;

    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

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
    public RealTimeSummaryResult realtime(@RequestParam(value = "channelNo",required = false) String channelNo,
                                          @RequestParam(value = "period") Long period,
                                          @RequestParam(value = "districtId", required = false) Integer districtId,
                                          @RequestParam(value = "blockId", required = false) Integer blockId,
                                          @RequestParam(value = "residenceId", required = false) Integer residenceId) {
        if (EnvUtil.isMockMode()) {
            return commonMockService.getRealTime();
        }
        // 1分钟，5分钟，15分钟
        if (period != 60 * 1000 && period != 300 * 1000 && period != 900 * 1000) {
            return new RealTimeSummaryResult("failed", new RealTimeSummaryObj(), "参数不正确");
        }
        if(null == channelNo){
            channelNo = "all";
        }

        if (null != residenceId) {
            districtId = null;
            blockId = null;
        } else if (null != blockId) {
            districtId = null;
        }

        return dataPlatformService.realtimeSummary(channelNo, period, districtId, blockId, residenceId);
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
    public TrendResult trend(@RequestParam(value = "channelNo", required = false) String channelNo,
                             @RequestParam(value = "startTime", required = false) Long startTime,
                             @RequestParam(value = "contrastiveStartTime", required = false) Long contrastiveStartTime,
                             @RequestParam(value = "span") long span, @RequestParam(value = "scale") long scale,
                             @RequestParam(value = "districtId", required = false) Integer districtId,
                             @RequestParam(value = "blockId", required = false) Integer blockId,
                             @RequestParam(value = "residenceId", required = false) Integer residenceId) throws Exception{
        if (EnvUtil.isMockMode()) {
            return commonMockService.trend();
        }

        //span : 跨度 ；scale : 刻度
        if (startTime == null) {
            TrendScaleType type = null;
            if (span == 60 * 60 * 1000) { //startTIme为null；跨度是一小时，刻度只能是一分钟
                // 走势统计-当前(小时)
                if (scale != 60 * 1000) { //刻度只能是一分钟
                    return new TrendResult("failed", null, "参数不正确");
                }
                type = TrendScaleType.minute;
            } else if (span == 24 * 60 * 60 * 1000) {//跨度为天 刻度只能是小时
                // 走势统计-当前（天）
                if (scale != 60 * 60 * 1000) {//小时
                    return new TrendResult("failed", null, "参数不正确");
                }
                type = TrendScaleType.hour;
            } else {
                return new TrendResult("failed", null, "参数不正确");
            }
            return dataPlatformService.realtimeTrend(channelNo, type, districtId, blockId, residenceId);
        } else {
            TrendScaleType type = null;
            if (scale == 24 * 60 * 60 * 1000) {//天
                type = TrendScaleType.day;
            } else if (scale == 60 * 60 * 1000) {//小时
                type = TrendScaleType.hour;
            } else {
                return new TrendResult("failed", null, "参数不正确");
            }
            return dataPlatformService.historyTrend(channelNo, startTime, contrastiveStartTime, span, type, districtId,
                    blockId, residenceId);
        }
    }

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
    private HistoryListResult<?> vistHis(@RequestParam(value = "channelNo", required = false) String channelNo,
                                         @RequestParam(value = "startTime", required = false) Long startTime,
                                         @RequestParam(value = "span") long span,
                                         @RequestParam(value = "districtId", required = false) Integer districtId,
                                         @RequestParam(value = "blockId", required = false) Integer blockId,
                                         @RequestParam(value = "residenceId", required = false) Integer residenceId,
                                         @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) throws ParseException {

        if (EnvUtil.isMockMode()) {
            return commonMockService.getMacVisitChannelHistoryInfo();
        }

        if(startTime==null) {
            startTime = System.currentTimeMillis() - span;
        }
        startTime = DateUtils.truncate(new Date(startTime), Calendar.DATE).getTime();

        long endTime = DateUtils.truncate(new Date(startTime + span), Calendar.DATE).getTime();

        if (null == pageNo) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize <= 0) {
            pageSize = 20;
        }

        return dataPlatformService.vistHis(channelNo, startTime, endTime, districtId, blockId, residenceId,
                pageNo, pageSize);

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
    public SummaryResult summary(@RequestParam(value = "channelNo", required = false) String channelNo,
                                 @RequestParam(value = "startTime", required = false) Long startTime,
                                 @RequestParam(value = "span") long span, @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) throws ParseException {
        if (EnvUtil.isMockMode()) {
            return commonMockService.getChannelVisitSummaryInfo();
        }

        if(startTime==null) {
            startTime = System.currentTimeMillis() - span;
        }
        startTime = DateUtils.truncate(new Date(startTime), Calendar.DATE).getTime();

        long endTime = DateUtils.truncate(new Date(startTime + span), Calendar.DATE).getTime();

        if (null == pageNo || pageNo <= 0) {
            pageNo = 1;
        }
        if (null == pageSize || pageSize <= 0) {
            pageSize = 20;
        }

        return dataPlatformService.summary(channelNo, startTime, endTime, pageNo, pageSize);
    }

}
