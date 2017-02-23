package com.lezhi.adminlj.service.impl;

import com.lezhi.adminlj.mapper.MapMapper;
import com.lezhi.adminlj.pojo.Neighborhood;
import com.lezhi.adminlj.pojo.ShDistrict;
import com.lezhi.adminlj.pojo.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cuill on 2017/2/21.
 */
@Service
public class MapService {

    @Autowired
    private MapMapper mapMapper;

    private static List<ShDistrict> shDistricts = null;

    public List<ShDistrict> getDistrictCenterTude() {

        if (null != shDistricts) {
            return shDistricts;
        }
        shDistricts = mapMapper.getDistrictCenterTude();
        return shDistricts;
    }

    public List<ShDistrict> getDistrictBoundryInfo(Integer districtId) {
        return mapMapper.getDistrictBoundryInfo(districtId);
    }

    public List<Town> showtown(Integer districtId) {
        return mapMapper.showtown(districtId);
    }

    public List<Neighborhood> neighborhood(Integer townId) {
        return mapMapper.neighborhood(townId);
    }
}
