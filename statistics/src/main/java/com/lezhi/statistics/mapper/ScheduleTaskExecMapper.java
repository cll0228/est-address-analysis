package com.lezhi.statistics.mapper;

import com.lezhi.statistics.pojo.ScheduleTaskExec;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Colin Yan on 2017/5/31.
 */
public interface ScheduleTaskExecMapper {

    ScheduleTaskExec findById(@Param("id") Integer id);

    int updateById(@Param("scheduleTaskExec") ScheduleTaskExec p);
}
