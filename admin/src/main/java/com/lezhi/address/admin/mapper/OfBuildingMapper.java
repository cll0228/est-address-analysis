package com.lezhi.address.admin.mapper;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by chendl on 2017/1/18.
 */
public interface OfBuildingMapper {
    List<OfResidence> selectResidenceByName(@Param("keyword") String keyword);

    OfBuilding getBuilidngInfoById(@Param("buildingId")String buildingId);

    List<OfBuilding> selectBuildById(@Param("id") String id, @Param("page") Integer page);

    Integer updateOfBuildingInfo(@Param("buildingId")String buildingId, @Param("buildingNo")String buildingNo,
                             @Param("houseCount")String houseCount, @Param("totalFloor")String totalFloor);

    Integer updateOfBuildingCoordinate(@Param("buildingId")String buildingId, @Param("newLon")String newLon,
                                       @Param("newLat")String newLat);
}