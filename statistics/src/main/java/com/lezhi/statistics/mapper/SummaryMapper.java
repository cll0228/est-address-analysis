package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.BlockSummaryInfo;
import com.lezhi.statistics.pojo.DistrictSummaryInfo;
import com.lezhi.statistics.pojo.ResidenceSummaryInfo;

/**
 * Created by wangyh on 2017/3/13.
 */
public interface SummaryMapper {
	List<DistrictSummaryInfo> getDistrictSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Long startTime, @Param("endTime") Long endTime);

	List<BlockSummaryInfo> getBlockSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Long startTime, @Param("endTime") Long endTime,
			@Param("districtId") Integer districtId);

	List<ResidenceSummaryInfo> getResidenceSummaryList(
			@Param("channelNo") String channelNo,
			@Param("startTime") Long startTime, @Param("endTime") Long endTime,
			@Param("blockId") Integer blockId,
			@Param("start") Integer start, @Param("size") Integer size);

	Integer totalCount(@Param("channelNo") String channelNo,
			@Param("startTime") Long startTime, @Param("endTime") Long endTime,
			@Param("blockId") Integer blockId);

}
