package com.lezhi.statistics.service;

import com.lezhi.statistics.mapper.SummaryMapper;
import com.lezhi.statistics.pojo.SummaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyh on 2017/3/15.
 */
@Service
public class SummaryService {
	@Autowired
	private SummaryMapper summaryMapper;

	public List<SummaryInfo> getDistrictSummaryList(String channelNo,
													long startTime, long endTime) {
		return summaryMapper.getDistrictSummaryList(channelNo, new Date(startTime),
				new Date(endTime));
	}

	public List<SummaryInfo> getBlockSummaryList(String channelNo,
													  long startTime, long endTime, Integer districtId) {
		return summaryMapper.getBlockSummaryList(channelNo, new Date(startTime), new Date(endTime),
				districtId);
	}

	public List<SummaryInfo> getResidenceSummaryList(String channelNo,
															  long startTime, long endTime, Integer blockId, Integer start,
			Integer size) {
		return summaryMapper.getResidenceSummaryList(channelNo, new Date(startTime),
				new Date(endTime), blockId, start, size);
	}

	public Integer totalCount(String channelNo, long startTime, long span,
			Integer blockId) {
		Integer count = summaryMapper.totalCount(channelNo, new Date(startTime), new Date(startTime + span),
				blockId);
		return count;
	}
}
