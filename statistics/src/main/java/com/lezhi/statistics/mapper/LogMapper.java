package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.LogGeneratorCountSpan;
import com.lezhi.statistics.pojo.LogGeneratorDistrict;

/**
 * Created by wangyh on 2017/3/29.
 */
public interface LogMapper {
	List<LogGeneratorDistrict> getDistrictResult(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	LogGeneratorCountSpan getSpanResult(@Param("startTime") String startTime,@Param("session") String session);
	
	void insertSummaryDistrict(@Param("logGeneratorDistrict") LogGeneratorDistrict logGeneratorDistrict);
}
