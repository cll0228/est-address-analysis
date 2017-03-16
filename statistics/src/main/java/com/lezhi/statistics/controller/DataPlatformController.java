package com.lezhi.statistics.controller;

import java.util.ArrayList;

import com.lezhi.statistics.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "mac/visit/history", method = RequestMethod.GET)
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

        return dataPlatformService.vistHis(channelNo, startTime, span, districtId, blockId, residenceId,
                pageNo, pageSize);

    }

    @RequestMapping(value = "realtime/summary", method = RequestMethod.GET)
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

}
