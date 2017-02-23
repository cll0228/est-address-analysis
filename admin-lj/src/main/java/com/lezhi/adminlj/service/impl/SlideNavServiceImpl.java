package com.lezhi.adminlj.service.impl;

import java.util.ArrayList;

import com.lezhi.adminlj.pojo.ParamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.adminlj.mapper.SlideNavMapper;
import com.lezhi.adminlj.pojo.CountParam;
import com.lezhi.adminlj.pojo.District;
import com.lezhi.adminlj.service.SlideNavService;

/**
 * Created by wangyh on 2017/2/14.
 */
@Service
public class SlideNavServiceImpl implements SlideNavService {
	@Autowired
	private SlideNavMapper slideNavMapper;

	@Override
	public ArrayList<District> districtList() {
		return slideNavMapper.districtList();
	}

	@Override
	public ArrayList<CountParam> districtCount() {
		return slideNavMapper.districtCount();
	}

	@Override
	public ArrayList<CountParam> levelOneCount(String districtId) {
		return slideNavMapper.levelOneCount(districtId);
	}

	@Override
	public ArrayList<CountParam> levelTwoCount(String townId) {
		return slideNavMapper.levelTwoCount(townId);
	}

	@Override
	public ArrayList<CountParam> levelThreeCount(String neighborhoodId) {
		return slideNavMapper.levelThreeCount(neighborhoodId);
	}
	@Override
	public ArrayList<CountParam> searchKeyword(ParamInfo paramInfo){
		return slideNavMapper.searchKeyword(paramInfo);
	}

}
