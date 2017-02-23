package com.lezhi.adminlj.mapper;

import java.util.ArrayList;

import com.lezhi.adminlj.pojo.ParamInfo;
import org.apache.ibatis.annotations.Param;

import com.lezhi.adminlj.pojo.CountParam;
import com.lezhi.adminlj.pojo.District;

/**
 * Created by Wangyh on 2017/2/14.
 */
public interface SlideNavMapper {
	ArrayList<District> districtList();
	
	ArrayList<CountParam> districtCount();
	
	ArrayList<CountParam> levelOneCount(@Param("districtId")String districtId);
	
	ArrayList<CountParam> levelTwoCount(@Param("townId")String townId);
	
	ArrayList<CountParam> levelThreeCount(@Param("neighborhoodId")String neighborhoodId);

	ArrayList<CountParam> searchKeyword(@Param("paramInfo")ParamInfo paramInfo);
}
