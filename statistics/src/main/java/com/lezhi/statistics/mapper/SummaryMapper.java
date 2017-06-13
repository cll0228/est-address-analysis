package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.SummaryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyh on 2017/3/13.
 */
public interface SummaryMapper {
	List<SummaryInfo> getDistrictSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	List<SummaryInfo> getBlockSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("districtId") Integer districtId);

	List<SummaryInfo> getResidenceSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("blockId") Integer blockId,
			@Param("start") Integer start, @Param("size") Integer size);

	Integer totalCount(@Param("channelNo") String channelNo,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("blockId") Integer blockId);

}
