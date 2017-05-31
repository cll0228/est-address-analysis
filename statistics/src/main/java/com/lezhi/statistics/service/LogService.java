package com.lezhi.statistics.service;

import com.lezhi.statistics.mapper.LogMapper;
import com.lezhi.statistics.pojo.ChannelVisitSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangyh on 2017/3/29.
 */
@Service
public class LogService {
	@Autowired
	private LogMapper logMapper;

	public void insertSummary(List<ChannelVisitSummary> channelVisitSummaries) {
		logMapper.insertSummary(channelVisitSummaries);
	}
	
}
