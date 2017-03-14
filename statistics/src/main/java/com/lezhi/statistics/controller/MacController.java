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

import com.lezhi.statistics.pojo.MacInfoObj;
import com.lezhi.statistics.service.MacService;

/**
 * Created by wangyh on 2017/3/13.
 */
@Controller
@RequestMapping("/mac/")
public class MacController {
	@Autowired MacService macService;
	
	/**
	 * 根据行政区获取机顶盒列表
	 * @param districtId
	 * @param blockId
	 * @param residenceId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Map<String, Object> loginSubmit(
			@RequestParam(value = "districtId", required = false) Integer districtId,
			@RequestParam(value = "blockId", required = false) Integer blockId,
			@RequestParam(value = "residenceId", required = false) Integer residenceId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		Map<String, Object> result = new HashMap<>();
		int type = -1;
		int id = -1;
		//实际返回记录数
		int realPageSize = -1;
		//总页数
		int totalPageCount = -1;
		//总记录数
		int totalCount = -1;
		//是否第一页
		boolean isFirstPage = false;
		//是否最后页
		boolean isLastPage = false;
		//默认页码
		int defaultPageNo = 1;
		//默认每页大小
		int defaultPageSize = 20;
		if(pageSize>0) {
			defaultPageSize = pageSize;
		}
		if(pageNo>0) {
			defaultPageNo = pageNo*defaultPageSize;
			if(pageNo==1) {
				isFirstPage = true;
			}
		}
		//判断优先级
		if(districtId!=null||blockId!=null||residenceId!=null) {
			if(residenceId!=null) {
				type = 3;
				id = residenceId;
			} else if(blockId!=null) {
				type = 2;
				id = blockId;
			} else if(districtId!=null) {
				type = 1;
				id = districtId;
			}
		}
		//验证id是否存在
		if(id!=-1) {
			int count = macService.checkId(type, id);
			if(count<=0) {
				result.put("status", "failed");
				result.put("datalist", new ArrayList<MacInfoObj>());
				result.put("errMsg", "id验证失败");
				return result;
			}
		}
		//根据条件查询记录
		List<MacInfoObj> macInfoList = macService.getMacInfoList(type, id, defaultPageNo, defaultPageSize);
		if(macInfoList!=null) {
			realPageSize = macInfoList.size();
			//查询总记录数计算总页数等信息
			totalCount = macService.totalCount(type, id);
			totalPageCount = totalCount/pageSize;
			if(pageNo==totalPageCount) {
				isLastPage = true;
			}
		} else {
			result.put("status", "failed");
			result.put("datalist", new ArrayList<MacInfoObj>());
			result.put("errMsg", "未找到记录");
			return result;
		}
		//封装返回对象
		result.put("status", "success");
		result.put("datalist", macInfoList);
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
