package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.DistrictSummaryInfo;

/**
 * Created by wangyh on 2017/3/13.
 */
public interface SummaryMapper {
	List<DistrictSummaryInfo> getDistrictSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Long startTime, @Param("endTime") Long endTime);

}
