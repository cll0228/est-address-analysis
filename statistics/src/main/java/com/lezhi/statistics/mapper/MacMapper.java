package com.lezhi.statistics.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.statistics.pojo.MacInfoObj;

/**
 * Created by wangyh on 2017/3/13.
 */
public interface MacMapper {
	List<MacInfoObj> getMacInfoList(@Param("type") Integer type,
			@Param("id") Integer id, @Param("start") Integer start,
			@Param("size") Integer size);

	Integer checkId(@Param("type") Integer type, @Param("id") Integer id);
	
	Integer totalCount(@Param("type") Integer type, @Param("id") Integer id);
}
