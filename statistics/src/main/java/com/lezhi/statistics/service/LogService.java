package com.lezhi.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.LogMapper;
import com.lezhi.statistics.pojo.LogGeneratorBlock;
import com.lezhi.statistics.pojo.LogGeneratorCountSpan;
import com.lezhi.statistics.pojo.LogGeneratorDistrict;

/**
 * Created by wangyh on 2017/3/29.
 */
@Service
public class LogService {
	@Autowired
	private LogMapper logMapper;
	
	/**
	 * 根据开始和结束时间查询区县log记录
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LogGeneratorDistrict> getDistrictResult(String startTime, String endTime) {
		List<LogGeneratorDistrict> logList = logMapper.getDistrictResult(startTime, endTime);
		for (LogGeneratorDistrict logGeneratorDistrict : logList) {
			LogGeneratorCountSpan countSpan = getSpanResult(startTime, logGeneratorDistrict.getSession());
			if (countSpan!=null&&countSpan.getCount()>0) {
				logGeneratorDistrict.setTotalTop((logGeneratorDistrict.getEnd()-countSpan.getSpanTime())*10);;
			}
		}
		return logList;
	}
	
	/**
	 * 根据开始和结束时间查询板块log记录
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LogGeneratorBlock> getBlockResult(String startTime, String endTime) {
		List<LogGeneratorBlock> logList = logMapper.getBlockResult(startTime, endTime);
		for (LogGeneratorBlock logGeneratorBlock : logList) {
			LogGeneratorCountSpan countSpan = getSpanResult(startTime, logGeneratorBlock.getSession());
			if (countSpan!=null&&countSpan.getCount()>0) {
				logGeneratorBlock.setTotalTop((logGeneratorBlock.getEnd()-countSpan.getSpanTime())*10);;
			}
		}
		return logList;
	}
	
	public LogGeneratorCountSpan getSpanResult(String startTime, String session) {
		LogGeneratorCountSpan countSpan = logMapper.getSpanResult(startTime, session);
		return countSpan;
	}
	
	public void insertSummaryDistrict(LogGeneratorDistrict logGeneratorDistrict) {
		logMapper.insertSummaryDistrict(logGeneratorDistrict);
	}
	
	public void insertSummaryBlock(LogGeneratorBlock logGeneratorBlock) {
		logMapper.insertSummaryBlock(logGeneratorBlock);
	}
	
	
	
}
