package com.lezhi.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.SummaryMapper;
import com.lezhi.statistics.pojo.DistrictSummaryInfo;

/**
 * Created by wangyh on 2017/3/15.
 */
@Service
public class SummaryService {
	@Autowired
	private SummaryMapper summaryMapper;

	public List<DistrictSummaryInfo> getDistrictSummaryList(String channelNo,
			Long startTime, Long endTime){
		return summaryMapper.getDistrictSummaryList(channelNo, startTime, endTime);
	}
}
