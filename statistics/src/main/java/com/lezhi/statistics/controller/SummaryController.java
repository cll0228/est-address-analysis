package com.lezhi.statistics.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.statistics.pojo.BlockSummaryInfo;
import com.lezhi.statistics.pojo.DistrictSummaryInfo;
import com.lezhi.statistics.pojo.ResidenceSummaryInfo;
import com.lezhi.statistics.service.SummaryService;

/**
 * Created by wangyh on 2017/3/15.
 */
@Controller
@RequestMapping("/summary/")
public class SummaryController {
	@Autowired
	private SummaryService summaryService;

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
	@RequestMapping(value = "district")
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
	@RequestMapping(value = "block")
	@ResponseBody
	public Map<String, Object> getMacInfoByDistrict(
			@RequestParam(value = "channelNo", required = false) String channelNo,
			@RequestParam(value = "startTime", required = false) Long startTime,
			@RequestParam(value = "span", required = true) Long span,
			@RequestParam(value = "districtId", required = true) Integer districtId) {
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
		List<BlockSummaryInfo> summaryInfoList = summaryService
				.getBlockSummaryList(channelNo, start, end, districtId);
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
	
	/**
	 * 按时间段统计小区概况，结果以小区为单位显示数据
	 * 
	 * @param channelNo
	 *            频道号，不做存在检验
	 * @param startTime
	 *            unix time, 单位ms；实时起点
	 * @param span
	 *            统计区间跨度，单位ms
	 * @return
	 */
	@RequestMapping(value = "residence")
	@ResponseBody
	public Map<String, Object> getMacInfoByBlock(
			@RequestParam(value = "channelNo", required = false) String channelNo,
			@RequestParam(value = "startTime", required = false) Long startTime,
			@RequestParam(value = "span", required = true) Long span,
			@RequestParam(value = "blockId", required = true) Integer blockId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		Map<String, Object> result = new HashMap<>();
		Long start = 0L;
		Long end = 0L;
		// 实际返回记录数
		int realPageSize = -1;
		// 总页数
		int totalPageCount = -1;
		// 总记录数
		int totalCount = -1;
		// 是否第一页
		boolean isFirstPage = false;
		// 是否最后页
		boolean isLastPage = false;
		// 默认页码
		int defaultPageNo = 1;
		// 默认每页大小
		int defaultPageSize = 20;
		if (pageSize != null && pageSize > 0) {
			defaultPageSize = pageSize;
		}
		if (pageNo != null && pageNo > 1) {
			defaultPageNo = (pageNo - 1) * defaultPageSize;
		}
		if (pageNo == null || pageNo == 1) {
			isFirstPage = true;
		}
		if (defaultPageNo == 1) {
			defaultPageNo = 0;
		}
		if(startTime!=null&&!"".equals(startTime)) {
			start = startTime - span;
			end = startTime;
		} else {
			start = System.currentTimeMillis()/1000 - span;
			end = System.currentTimeMillis()/1000;
		}
		List<ResidenceSummaryInfo> residenceInfoList = summaryService
				.getResidenceSummaryList(channelNo, start, end, blockId, defaultPageNo, defaultPageSize);
		if (residenceInfoList != null && residenceInfoList.size() > 0) {
			realPageSize = residenceInfoList.size();
			// 查询总记录数计算总页数等信息
			totalCount = summaryService.totalCount(channelNo, start, end, blockId);
			int mod = totalCount % defaultPageSize;
			totalPageCount = mod > 0 ? (totalCount / defaultPageSize) + 1
					: (totalCount / defaultPageSize);
			if(totalPageCount == 1) {
				pageNo = totalPageCount;
			}
			if (pageNo != null && pageNo == totalPageCount) {
				isLastPage = true;
			}
		} else {
			result.put("status", "failed");
			result.put("summaries", new ArrayList<DistrictSummaryInfo>());
			result.put("errMsg", "未找到记录");
			return result;
		}
		// 封装返回对象
		result.put("status", "success");
		result.put("datalist", residenceInfoList);
		result.put("errMsg", "");
		result.put("pageNo", pageNo);
		result.put("pageSize", defaultPageSize);
		result.put("realPageSize", realPageSize);
		result.put("totalPageCount", totalPageCount);
		result.put("isFirstPage", isFirstPage);
		result.put("isLastPage", isLastPage);
		return result;
	}
}
