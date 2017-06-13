package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Cuill on 2017/3/14.
 */
public interface DataPlatformMapper {
    List<MacVisitHistoryInfo> selectVisitHis(@Param("channelNo") String channelNo,
                                             @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                             @Param("districtId") Integer districtId, @Param("blockId") Integer blockId,
                                             @Param("residenceId") Integer residenceId);

    List<ChannelSummaryObj> summary(@Param("channelNo") String channelNo, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime);

    List<TrendObj> his_current(@Param("channelNo") String channelNo, @Param("startTime") Date startTime,
                               @Param("endTime") Date endTime, @Param("scale") Long scale, @Param("districtId") Integer districtId,
                               @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

    List<TrendObj> his_contrastive(@Param("channelNo") String channelNo, @Param("contrastiveStartTime") Date contrastiveStartTime,
                                   @Param("contrastiveEndTime") Date contrastiveEndTime, @Param("scale") Long scale, @Param("districtId") Integer districtId,
                                   @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

    void insert(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    void insert_trend(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    void insert_trend_day(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    List<Log> selectLog(@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    List<Log> selectLogBySession(@Param("log") Log log,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    Integer selectUv(@Param("log") Log log, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime, @Param("type") String type);

    Integer selectNv(@Param("log") Log log, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime, @Param("type") String type);

    List<String> selectPv(@Param("log") Log log, @Param("startTime") Date startTime,
            @Param("endTime") Date endTime, @Param("type") String type);

    Integer selectDistrictId(@Param("log")Log log);

    List<Log> selectSameSession(@Param("log")Log log);

    Integer selectBlockId(@Param("log")Log log);

    Integer selectResidenceId(@Param("log")Log log);

    List<Log> selectTimeBySameMacAndSession(@Param("log")Log log,@Param("startTime") Date startTime);

    void saveDis(@Param("his") HisTrendInfo his,@Param("type") String type);

    void saveDis_day(@Param("his") HisTrendInfo his,@Param("type") String type);

}

