package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.ChannelSummaryObj;
import com.lezhi.statistics.pojo.MacVisitHistoryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Cuill on 2017/3/14.
 */
public interface DataPlatformMapper {
    List<MacVisitHistoryInfo> selectVisitHis(@Param("channelNo") String channelNo,
                                             @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                             @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
                                             @Param("residenceId") Integer residenceId);

    List<ChannelSummaryObj> channelSummary(@Param("channelNo") String channelNo, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);

}

