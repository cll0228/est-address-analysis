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
	
	/**
	 * 根据行政区获取机顶盒列表
	 * @param type
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 */
	public List<MacInfoObj> getMacInfoList(Integer type, Integer id,
			Integer start, Integer size) {
		List<MacInfoObj> macInfoList = macMapper.getMacInfoList(type, id,
				start, size);
		return macInfoList;
	}
	
	/**
	 * 校验id是否存在
	 * 1区县（districtId）2板块（blockId）3小区（residenceId）
	 * @param type
	 * @param id
	 * @return
	 */
	public Integer checkId(Integer type, Integer id) {
		Integer count = macMapper.checkId(type, id);
		return count;
	}
	
	/**
	 * 查询满足条件下总共有多少条数据
	 * @param type
	 * @param id
	 * @return
	 */
	public Integer totalCount(Integer type, Integer id) {
		Integer count = macMapper.totalCount(type, id);
		return count;
	}
	
	/**
	 * 根据mac地址查询机顶盒信息，如果有多条则取第一条
	 * @param mac
	 * @return
	 */
	public MacInfoObj getMacInfo(String mac) {
		MacInfoObj macInfo = macMapper.getMacInfo(mac);
		return macInfo;
	}
}
