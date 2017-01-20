package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;

import java.util.List;

/**
 * Created by chendl on 2017/1/18.
 */
public interface BuildingService {
    OfBuilding getBuilidngInfoById(String buildingId);

    int updateOfBuildingInfo(String buildingId, String buildingNo, String houseCount, String totalFloor);
}
