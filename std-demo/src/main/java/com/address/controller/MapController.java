package com.address.controller;

import com.address.mapper.StdMapper;
import com.address.model.OfBuilding;
import com.address.model.ResidenceBoundary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Cuill on 2017/1/10.
 */
@Controller
@RequestMapping(value = "/")
public class MapController {
    @Autowired
    private StdMapper stdMapper;

    @RequestMapping(value = "/boundary", method = RequestMethod.GET)
    @ResponseBody
    public List<ResidenceBoundary> boundary(
            @RequestParam(value = "roadLan", required = false) String roadLan) {
        // 小区边界信息
        List<ResidenceBoundary> boundaries = stdMapper.selectResiBoundaryById(roadLan);
        if (null == boundaries || boundaries.size() == 0) {
           //没有边界查询小区中心点
            boundaries = stdMapper.selectOfResidenceCenter(roadLan);
        }
        return boundaries;
    }

    @RequestMapping(value = "/building", method = RequestMethod.POST)
    @ResponseBody
    public OfBuilding building(
            @RequestParam(value = "roadLan", required = false) String roadLan,
            @RequestParam(value = "buildingNo", required = false) String buildingNo) {
        // 小区边界信息
        OfBuilding ofBuilding = stdMapper.selectBuilding(roadLan,buildingNo);
        if (null == ofBuilding) {
            return null;
        }
        return ofBuilding;
    }
}
