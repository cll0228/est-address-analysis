package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.LogGenerator;
import com.lezhi.statistics.pojo.LogGeneratorCountSpan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangyh on 2017/3/29.
 */
public interface LogMapper {
	List<LogGenerator> getResult(@Param("startTime") String startTime,@Param("endTime") String endTime);

	LogGeneratorCountSpan getSpanResult(@Param("startTime") String startTime,@Param("session") String session);

	void insertSummary(@Param("logGenerator") LogGenerator logGenerator);
	
}
