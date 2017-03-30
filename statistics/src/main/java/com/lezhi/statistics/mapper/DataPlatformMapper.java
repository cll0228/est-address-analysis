package com.lezhi.statistics.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.*;

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

    List<ChannelSummaryObj> summary(@Param("channelNo") String channelNo, @Param("startTime") Long startTime,
            @Param("span") Long span);

    List<TrendObj> his_current(@Param("channelNo") String channelNo, @Param("startTime") Long startTime,
                               @Param("span") Long span, @Param("scale") Long scale, @Param("districtId") Integer districtId,
                               @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

    List<TrendObj> his_contrastive(@Param("channelNo") String channelNo, @Param("contrastiveStartTime") Long contrastiveStartTime,
                                   @Param("span") Long span, @Param("scale") Long scale, @Param("districtId") Integer districtId,
                                   @Param("blockId") Integer blockId, @Param("residenceId") Integer residenceId);

    void inert(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    void inert_trend(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    void inert_trend_day(@Param("Map") Map<String, Object> Map, @Param("type") String type);

    List<Log> selectLog(@Param("startTime")Long startTime,@Param("endTime")Long endTime);

    List<Log> selectLogBySession(@Param("log") Log log,@Param("startTime")Long startTime,@Param("endTime")Long endTime);

    Integer selectUv(@Param("log") Log log, @Param("startTime") Long startTime,
            @Param("endTime") Long endTime, @Param("type") String type);

    Integer selectNv(@Param("log") Log log, @Param("startTime") Long startTime,
            @Param("endTime") Long endTime, @Param("type") String type);

    List<String> selectPv(@Param("log") Log log, @Param("startTime") Long startTime,
            @Param("endTime") Long endTime, @Param("type") String type);

    Integer selectDistrictId(@Param("log")Log log);

    List<Log> selectSameSession(@Param("log")Log log);

    Integer selectBlockId(@Param("log")Log log);

    Integer selectResidenceId(@Param("log")Log log);

    List<Log> selectTimeBySameMacAndSession(@Param("log")Log log,@Param("startTime") Long startTime);

    void saveDis(@Param("his") HisTrendInfo his,@Param("type") String type);
}

