package com.lezhi.address.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;

/**
 * Created by Cuill on 2017/1/18.
 */
public interface OfResidenceMapper {
    List<OfResidence> selectResidenceByName(@Param("keyword") String keyword);
    
    OfResidence selectResidenceDetailByResidenceId(@Param("residenceId") Integer residenceId);

    List<ResidenceBoundary> selectResiBoundaryById(@Param("residenceId")String residenceId);

    List<ResidenceBoundary> selectOfResidenceCenter(@Param("residenceId")String residenceId);
    
    Integer updateOfResidenceInfo(@Param("ofResidence")OfResidence ofResidence);

    Integer updateOfResidenceCoordinate(@Param("residenceId")String residenceId, @Param("newLon")String newLon,
                                        @Param("newLat")String newLat);
}
