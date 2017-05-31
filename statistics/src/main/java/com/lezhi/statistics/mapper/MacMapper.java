package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.MacInfoObj;
import com.lezhi.statistics.pojo.MacVisitLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by wangyh on 2017/3/15.
 */
public interface MacMapper {
	List<MacInfoObj> getMacInfoList(@Param("type") Integer type,
			@Param("id") Integer id, @Param("start") Integer start,
			@Param("size") Integer size);

	Integer checkId(@Param("type") Integer type, @Param("id") Integer id);
	
	Integer totalCount(@Param("type") Integer type, @Param("id") Integer id);
	
	MacInfoObj getMacInfo(@Param("mac") String mac);

	/**
	 *
	 * @param mac
	 * @param channelNo
	 * @param startTime 单位秒
	 * @param endTime 单位秒
	 * @param districtId
	 * @param blockId
	 * @param residenceId
	 * @return
	 */
	List<MacVisitLogInfo> getMacVisitLog(@Param("mac")String mac, @Param("channelNo") String channelNo,
										 @Param("startTime") Long startTime, @Param("endTime") Long endTime,
										 @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
										 @Param("residenceId") Integer residenceId);

	List<MacVisitLogInfo> selectForRealtimeSummary(@Param("channelNo") String channelNo, @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
												   @Param("residenceId") Integer residenceId, @Param("timeFrom") Date timeFrom, @Param("now") Date now);

	Integer nvForRealtimeSummary(@Param("channelNo") String channelNo, @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
								 @Param("residenceId") Integer residenceId, @Param("timeFrom") Date timeFrom, @Param("now") Date now);
}
