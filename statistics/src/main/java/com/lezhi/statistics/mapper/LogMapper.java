package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.LogGeneratorBlock;
import com.lezhi.statistics.pojo.LogGeneratorCountSpan;
import com.lezhi.statistics.pojo.LogGeneratorDistrict;
import com.lezhi.statistics.pojo.LogGeneratorResidence;

/**
 * Created by wangyh on 2017/3/29.
 */
public interface LogMapper {
	List<LogGeneratorDistrict> getDistrictResult(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	List<LogGeneratorBlock> getBlockResult(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	List<LogGeneratorResidence> getResidenceResult(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
	LogGeneratorCountSpan getSpanResult(@Param("startTime") String startTime,@Param("session") String session);
	
	void insertSummaryDistrict(@Param("logGeneratorDistrict") LogGeneratorDistrict logGeneratorDistrict);
	
	void insertSummaryBlock(@Param("logGeneratorBlock") LogGeneratorBlock logGeneratorBlock);
	
	void insertSummaryResidence(@Param("logGeneratorResidence") LogGeneratorResidence logGeneratorResidence);
	
}
