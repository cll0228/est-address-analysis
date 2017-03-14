package com.lezhi.statistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.pojo.MacInfoObj;

/**
 * Created by wangyh on 2017/3/13.
 */
@Service
public class MacService {
	@Autowired
	private MacMapper macMapper;

	public List<MacInfoObj> getMacInfoList(Integer type, Integer id,
			Integer start, Integer end) {
		List<MacInfoObj> macInfoList = macMapper.getMacInfoList(type, id,
				start, end);
		return macInfoList;
	}
}
