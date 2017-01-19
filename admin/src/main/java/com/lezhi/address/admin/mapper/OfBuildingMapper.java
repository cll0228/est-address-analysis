package com.lezhi.address.admin.mapper;

import com.lezhi.address.admin.pojo.OfBuilding;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfBuildingMapper {

    List<OfBuilding> selectBuildById(@Param("id") String id, @Param("page") Integer page);
}