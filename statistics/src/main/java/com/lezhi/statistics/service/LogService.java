package com.lezhi.statistics.service;

import com.lezhi.statistics.mapper.LogMapper;
import com.lezhi.statistics.pojo.LogGenerator;
import com.lezhi.statistics.pojo.LogGeneratorCountSpan;
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

	
	/**
	 * 根据开始和结束时间查询小区log记录
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LogGenerator> getResult(String startTime, String endTime) {
		List<LogGenerator> logList = logMapper.getResult(startTime, endTime);
		for (LogGenerator logGenerator : logList) {
			LogGeneratorCountSpan countSpan = getSpanResult(startTime, logGenerator.getSession());
			if (countSpan!=null&&countSpan.getCount()>0) {
				logGenerator.setTotalTop((logGenerator.getEnd()-countSpan.getSpanTime())*10);;
			}
		}
		return logList;
	}
	
	public LogGeneratorCountSpan getSpanResult(String startTime, String session) {
		LogGeneratorCountSpan countSpan = logMapper.getSpanResult(startTime, session);
		return countSpan;
	}
	
	public void insertSummary(LogGenerator logGeneratorDistrict) {
		logMapper.insertSummary(logGeneratorDistrict);
	}
	

	
}
