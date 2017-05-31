package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.ChannelVisitSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wangyh on 2017/3/29.
 */
public interface LogMapper {

	void insertSummary(@Param("buildings") List<ChannelVisitSummary> channelVisitSummary);
	
}
