package com.lezhi.statistics.controller;

import com.lezhi.statistics.pojo.MacVisitHistoryInfo;
import com.lezhi.statistics.pojo.ReturnObj;
import com.lezhi.statistics.service.DataPlatformService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cuill on 2017/3/13. 数据平台
 */
@Controller
@RequestMapping(value = "mac")
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
    @RequestMapping(value = "visit/history")
    @ResponseBody
    private ReturnObj vistHis(@RequestParam(value = "channelNo") String channelNo,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "span") Long span,
            @RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "blockId", required = false) Integer blockId,
            @RequestParam(value = "residenceId", required = false) Integer residenceId,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (null == channelNo || "".equals(channelNo) || null == span) {
            // 提示必填参数不能为空
            return new ReturnObj("failed", new ArrayList<MacVisitHistoryInfo>(), "必填参数不能为空");
        }
        if (null == span) {
            span = new Date().getTime();
        }
        ReturnObj obj =  dataPlatformService.vistHis();
        return null;
    }
}
