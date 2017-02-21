package com.lezhi.adminlj.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.lezhi.adminlj.pojo.CountParam;
import com.lezhi.adminlj.pojo.District;

/**
 * Created by Wangyh on 2017/2/14.
 */
public interface SlideNavMapper {
	ArrayList<District> districtList();
	
	ArrayList<CountParam> districtCount();
	
	ArrayList<CountParam> levelOneCount(@Param("districtId")Integer districtId);
}
