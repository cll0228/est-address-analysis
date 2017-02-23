package com.lezhi.adminlj.service;

import java.util.ArrayList;

import com.lezhi.adminlj.pojo.ParamInfo;
import org.apache.ibatis.annotations.Param;

import com.lezhi.adminlj.pojo.CountParam;
import com.lezhi.adminlj.pojo.District;

/**
 * Created by Wangyh on 2017/2/14.
 */
public interface SlideNavService {
	
	ArrayList<District> districtList();
    
	ArrayList<CountParam> districtCount();
	
	ArrayList<CountParam>levelOneCount(String districtId);
	
	ArrayList<CountParam> levelTwoCount(String townId);
	
	ArrayList<CountParam> levelThreeCount(String neighborhoodId);

	ArrayList<CountParam> searchKeyword(ParamInfo paramInfo);
}
