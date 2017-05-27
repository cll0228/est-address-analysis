package com.lezhi.statistics.service;

import com.lezhi.statistics.mapper.SummaryMapper;
import com.lezhi.statistics.pojo.SummaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyh on 2017/3/15.
 */
@Service
public class SummaryService {
	@Autowired
	private SummaryMapper summaryMapper;

	public List<SummaryInfo> getDistrictSummaryList(String channelNo,
													long startTime, long span) {
		return summaryMapper.getDistrictSummaryList(channelNo, startTime / 1000,
				(startTime + span) / 1000);
	}

	public List<SummaryInfo> getBlockSummaryList(String channelNo,
													  long startTime, long span, Integer districtId) {
		return summaryMapper.getBlockSummaryList(channelNo, startTime / 1000, (startTime + span) / 1000,
				districtId);
	}

	public List<SummaryInfo> getResidenceSummaryList(String channelNo,
															  long startTime, long span, Integer blockId, Integer start,
			Integer size) {
		return summaryMapper.getResidenceSummaryList(channelNo, startTime / 1000,
				(startTime + span) / 1000, blockId, start, size);
	}

	public Integer totalCount(String channelNo, long startTime, long span,
			Integer blockId) {
		Integer count = summaryMapper.totalCount(channelNo, startTime / 1000, (startTime + span) / 1000,
				blockId);
		return count;
	}
}
