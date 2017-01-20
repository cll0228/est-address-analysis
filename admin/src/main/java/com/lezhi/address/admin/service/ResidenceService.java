package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;

import java.util.List;
import java.util.Map;

/**
 * Created by Cuill on 2017/1/18.
 */
public interface ResidenceService {
    List<OfResidence> selectResidenceByName(String keyword);

    Map<String, Object> selectBuildById(String id, Integer page);

    OfResidence selectResidenceDetailByResidenceId(Integer residenceId);

    List<ResidenceBoundary> selectResiBoundaryById(String residenceId);

    List<ResidenceBoundary> selectOfResidenceCenter(String residenceId);
}
