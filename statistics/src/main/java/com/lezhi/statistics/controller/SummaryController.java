package com.lezhi.statistics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.statistics.pojo.DistrictSummaryInfo;
import com.lezhi.statistics.service.SummaryService;

/**
 * Created by wangyh on 2017/3/15.
 */
@Controller
@RequestMapping("/summary/")
public class SummaryController {
	@Autowired
	SummaryService summaryService;

	/**
	 * 按时间段统计各区县概况，结果以区为单位显示数据
	 * 
	 * @param channelNo
	 *            频道号，不做存在检验
	 * @param startTime
	 *            unix time, 单位ms；实时起点
	 * @param span
	 *            统计区间跨度，单位ms
	 * @return
	 */
	@RequestMapping(value = "district", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMacInfoByMac(
			@RequestParam(value = "channelNo", required = false) String channelNo,
			@RequestParam(value = "startTime", required = false) Long startTime,
			@RequestParam(value = "span", required = true) Long span) {
		Map<String, Object> result = new HashMap<>();
		Long start = 0L;
		Long end = 0L;
		if(startTime!=null&&!"".equals(startTime)) {
			start = startTime - span;
			end = startTime;
		} else {
			start = System.currentTimeMillis()/1000 - span;
			end = System.currentTimeMillis()/1000;
		}
		List<DistrictSummaryInfo> summaryInfoList = summaryService
				.getDistrictSummaryList(channelNo, start, end);
		if (summaryInfoList == null||summaryInfoList.size()==0) {
			result.put("status", "failed");
			result.put("summaries", new ArrayList<DistrictSummaryInfo>());
			result.put("errMsg", "未找到记录");
			return result;
		}
		// 封装返回对象
		result.put("summaries", summaryInfoList);
		result.put("status", "success");
		result.put("errMsg", "");
		return result;
	}
}
