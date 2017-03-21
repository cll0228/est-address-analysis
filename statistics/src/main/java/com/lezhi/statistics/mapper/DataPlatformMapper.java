package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.MacVisitHistoryInfo;
import com.lezhi.statistics.pojo.RealTimeSummaryObj;
import com.lezhi.statistics.pojo.TrendObj;

/**
 * Created by Cuill on 2017/3/14.
 */
public interface DataPlatformMapper {
    List<MacVisitHistoryInfo> selectVistHis(@Param("channelNo") String channelNo,
            @Param("startTime") Long startTime, @Param("span") Long span,
            @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
            @Param("residenceId") Integer residenceId) throws Exception;;

    RealTimeSummaryObj realtime(@Param("channelNo") String channelNo, @Param("period") Long period,
            @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
            @Param("residenceId") Integer residenceId) throws Exception;

    List<TrendObj> current(@Param("channelNo") String channelNo, @Param("startTime") Long startTime,
            @Param("span") Long span, @Param("scale") Long scale, @Param("districtId") Integer districtId,
            @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

    List<TrendObj> contrastive(@Param("channelNo") String channelNo, @Param("contrastiveStartTime") Long contrastiveStartTime,
                               @Param("span") Long span, @Param("scale") Long scale, @Param("districtId") Integer districtId,
                               @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

}
