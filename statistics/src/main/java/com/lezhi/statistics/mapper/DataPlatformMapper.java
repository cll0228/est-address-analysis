package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.MacVisitHistoryInfo;
import com.lezhi.statistics.pojo.RealTimeSummaryObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2017/3/14.
 */
public interface DataPlatformMapper {
    List<MacVisitHistoryInfo> selectVistHis(@Param("channelNo") String channelNo,
            @Param("startTime") Long startTime, @Param("span") Long span,
            @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
            @Param("residenceId") Integer residenceId);

    RealTimeSummaryObj realtime(@Param("channelNo") String channelNo, @Param("period") Long period,
            @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
            @Param("residenceId") Integer residenceId);
}
