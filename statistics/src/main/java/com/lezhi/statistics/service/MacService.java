package com.lezhi.statistics.service;

import java.util.ArrayList;
import java.util.List;

import com.lezhi.statistics.pojo.MacVisit;
import com.lezhi.statistics.pojo.MacVisitHistoryInfo;
import com.lezhi.statistics.pojo.MacVisitLogInfo;
import com.lezhi.statistics.util.ListPageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.pojo.MacInfoObj;

/**
 * Created by wangyh on 2017/3/13.
 */
@Service
public class MacService {
	@Autowired
	private MacMapper macMapper;
	
	/**
	 * 根据行政区获取机顶盒列表
	 * @param type
	 * @param id
	 * @param start
	 * @return
	 */
	public List<MacInfoObj> getMacInfoList(Integer type, Integer id,
			Integer start, Integer size) {
		List<MacInfoObj> macInfoList = macMapper.getMacInfoList(type, id,
				start, size);
		return macInfoList;
	}
	
	/**
	 * 校验id是否存在
	 * 1区县（districtId）2板块（blockId）3小区（residenceId）
	 * @param type
	 * @param id
	 * @return
	 */
	public Integer checkId(Integer type, Integer id) {
		Integer count = macMapper.checkId(type, id);
		return count;
	}
	
	/**
	 * 查询满足条件下总共有多少条数据
	 * @param type
	 * @param id
	 * @return
	 */
	public Integer totalCount(Integer type, Integer id) {
		Integer count = macMapper.totalCount(type, id);
		return count;
	}
	
	/**
	 * 根据mac地址查询机顶盒信息，如果有多条则取第一条
	 * @param mac
	 * @return
	 */
	public MacInfoObj getMacInfo(String mac) {
		MacInfoObj macInfo = macMapper.getMacInfo(mac);
		return macInfo;
	}

	/**
	 * 按时间段、频道、mac查日志详情列表
	 * @param mac,channelNo..
	 * @return
	 */
	public MacVisit getMacVisitLog(String mac, String channelNo, Long startTime, long span, Integer districtId,
									 Integer blockId, Integer residenceId,Integer pageNo, Integer pageSize) {
		if (null != residenceId) {
			districtId = null;
			blockId = null;
		} else if (null != blockId) {
			districtId = null;
		}

		if (startTime == null || startTime <= 0) {
			startTime = System.currentTimeMillis() - span;
		}

		long start = startTime / 1000;
		long end = (startTime + span) / 1000;

		if (null == pageNo) {
			pageNo = 1;
		}
		if (null == pageSize || pageSize <= 0) {
			pageSize = 20;
		}
		List<MacVisitLogInfo> macInfoList = macMapper.getMacVisitLog(mac,channelNo,start,end,districtId,blockId,residenceId);
		if (null == macInfoList || macInfoList.size() == 0) {
			return new MacVisit("success", new ArrayList<MacVisit>(), "");
		}
		// 分页
		MacVisit obj = new MacVisit();
		ListPageUtil<MacVisitLogInfo> listPageUtil = new ListPageUtil<>(macInfoList, pageNo, pageSize);
		List<MacVisitLogInfo> pagedList = listPageUtil.getPagedList();
		obj.setHistories(pagedList);
		obj.setStatus("success");
		obj.setErrMsg("");
		obj.setTotalCount(macInfoList.size());
		// 分页信息
		if (pageNo == 1) {// 是否第一页
			obj.setIsFirstPage(true);
		} else {
			obj.setIsFirstPage(false);
		}
		// 是否最后一页
		int totalPage = listPageUtil.getTotalPage();
		obj.setTotalPageCount(totalPage);
		obj.setTotalCount(macInfoList.size());
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
}
