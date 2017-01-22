package com.lezhi.address.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.address.admin.mapper.OfBuildingMapper;
import com.lezhi.address.admin.mapper.OfResidenceMapper;
import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.ResidenceService;

/**
 * Created by Cuill on 2017/1/18.
 */
@Service
public class ResidenceServiceImpl implements ResidenceService {

    @Autowired
    private OfResidenceMapper ofResidenceMapper;

    @Autowired
    private OfBuildingMapper ofBuildingMapper;

    @Override
    public List<OfResidence> selectResidenceByName(String keyword) {
        return ofResidenceMapper.selectResidenceByName(keyword);
    }

    @Override
    public Map<String, Object> selectBuildById(String id, Integer page) {
        Map<String, Object> map = new HashedMap();
        List<OfBuilding> ofBuildings = ofBuildingMapper.selectBuildById(id, null);
        if (null != ofBuildings && 0 != ofBuildings.size()) {
            Integer totalPage = ofBuildings.size() / 10 + 1;
            map.put("totalPage", totalPage);
        }
        List<OfBuilding> data;
        if (null == page) {
            page = 1;
            data = ofBuildingMapper.selectBuildById(id, (page - 1) * 10 + 1);
        } else
            data = ofBuildingMapper.selectBuildById(id, (page - 1) * 10 + 1);

        map.put("data", data);
        return map;
    }

    @Override
    public OfResidence selectResidenceDetailByResidenceId(Integer residenceId) {
        return ofResidenceMapper.selectResidenceDetailByResidenceId(residenceId);
    }

    public List<ResidenceBoundary> selectResiBoundaryById(String residenceId){
        List<ResidenceBoundary> boundaries = ofResidenceMapper.selectResiBoundaryById(residenceId);
        return boundaries;
    }

    public List<ResidenceBoundary> selectOfResidenceCenter(String residenceId){
        List<ResidenceBoundary> boundaries = ofResidenceMapper.selectOfResidenceCenter(residenceId);
        return boundaries;
    }

	@Override
	public Integer updateOfResidenceInfo(OfResidence ofResidence) {
		return ofResidenceMapper.updateOfResidenceInfo(ofResidence);
	}
}
