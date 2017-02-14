package com.lezhi.adminlj.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.adminlj.mapper.SlideNavMapper;
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

}
