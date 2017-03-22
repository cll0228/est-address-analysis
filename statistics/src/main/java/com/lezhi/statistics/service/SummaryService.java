package com.lezhi.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.SummaryMapper;
import com.lezhi.statistics.pojo.BlockSummaryInfo;
import com.lezhi.statistics.pojo.DistrictSummaryInfo;
import com.lezhi.statistics.pojo.ResidenceSummaryInfo;

/**
 * Created by wangyh on 2017/3/15.
 */
@Service
public class SummaryService {
	@Autowired
	private SummaryMapper summaryMapper;

	public List<DistrictSummaryInfo> getDistrictSummaryList(String channelNo,
			Long startTime, Long endTime) {
		return summaryMapper.getDistrictSummaryList(channelNo, startTime,
				endTime);
	}

	public List<BlockSummaryInfo> getBlockSummaryList(String channelNo,
			Long startTime, Long endTime, Integer districtId) {
		return summaryMapper.getBlockSummaryList(channelNo, startTime, endTime,
				districtId);
	}

	public List<ResidenceSummaryInfo> getResidenceSummaryList(String channelNo,
			Long startTime, Long endTime, Integer blockId, Integer start,
			Integer size) {
		return summaryMapper.getResidenceSummaryList(channelNo, startTime,
				endTime, blockId, start, size);
	}

	public Integer totalCount(String channelNo, Long startTime, Long endTime,
			Integer blockId) {
		Integer count = summaryMapper.totalCount(channelNo, startTime, endTime,
				blockId);
		return count;
	}
}
