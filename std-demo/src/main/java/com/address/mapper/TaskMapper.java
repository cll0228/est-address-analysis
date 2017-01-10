package com.address.mapper;

import com.address.model.LianjiaResidenceInfo;
import com.address.model.OfResidence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2017/1/4.
 */
public interface TaskMapper {
    List<LianjiaResidenceInfo> getLianjiaResidenceList();

    List<LianjiaResidenceInfo> selcetResidenceByAddr(@Param("addr") String addr);

    List<OfResidence> selcetOfResidence();

    Integer selectBuildingCount(@Param("id") Integer id);

    Integer selectHouseCount(@Param("id") Integer id);

    void updateOfResidenceBuildingCount(@Param("buildingCount") Integer buildingCount, @Param("id") Integer id);

    void updateOfResidenceHouseCount(@Param("houseCount") Integer houseCount, @Param("id") Integer id);
}
