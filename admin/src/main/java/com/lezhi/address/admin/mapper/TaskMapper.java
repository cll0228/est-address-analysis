package com.lezhi.address.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.TAnalysisTask;
import com.lezhi.address.admin.pojo.Task;

/**
 * Created by wangyh on 2017/2/8.
 */
public interface TaskMapper {
	Task getTaskInfo(@Param("id") Integer id);
	
	int insertTAnalysisTask(TAnalysisTask tAnalysisTask);
	
	void updateTAnalysisTask(TAnalysisTask tAnalysisTask);
	
}
