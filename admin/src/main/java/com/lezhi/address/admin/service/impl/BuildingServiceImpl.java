package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.OfBuildingMapper;
import com.lezhi.address.admin.mapper.OfResidenceMapper;
import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.BuildingService;
import com.lezhi.address.admin.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chendl on 2017/1/18.
 */
@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private OfBuildingMapper ofBuildingMapper;

    @Override
    public OfBuilding getBuilidngInfoById(String buildingId) {
       return ofBuildingMapper.getBuilidngInfoById(buildingId);
    }

    public Integer updateOfBuildingInfo(String buildingId, String buildingNo, String houseCount, String totalFloor){
        return ofBuildingMapper.updateOfBuildingInfo(buildingId, buildingNo, houseCount, totalFloor);
    }
    public Integer updateOfBuildingCoordinate(String buildingId, String newLon, String newLat) {
        return ofBuildingMapper.updateOfBuildingCoordinate(buildingId, newLon, newLat);
    }
}
