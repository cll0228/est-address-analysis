package com.lezhi.adminlj.mapper;

import com.lezhi.adminlj.pojo.Neighborhood;
import com.lezhi.adminlj.pojo.Residence;
import com.lezhi.adminlj.pojo.ShDistrict;
import com.lezhi.adminlj.pojo.Town;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2017/2/21.
 */
public interface MapMapper {
    List<ShDistrict> getDistrictCenterTude();

    List<ShDistrict> getDistrictBoundryInfo(@Param("districtId") Integer districtId);

    List<Town> showtown(@Param("districtId")Integer districtId);

    List<Neighborhood> neighborhood(@Param("townId")Integer townId);

    List<Residence> residence(@Param("neighborhoodId")String neighborhoodId);
}
