package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.BuildingService;
import com.lezhi.address.admin.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private ResidenceService residenceService;

    @RequestMapping(value = "buildingList", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "buildingList";
    }

    @RequestMapping(value = "building")
    public String queryBuildingInfo(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "buildingId")String buildingId){
        if(null == buildingId || "".equals(buildingId)){
            return null;
        }
        System.out.println("buildingId:"+buildingId);
        // 楼栋详情信息
        OfBuilding ofBuilding = buildingService.getBuilidngInfoById(buildingId);
        request.setAttribute("buildingInfo", ofBuilding);
        return "building";
    }

    @RequestMapping(value = "boundary", method = RequestMethod.POST)
    @ResponseBody
    public List<ResidenceBoundary> boundary(@RequestParam(value = "residenceId")String residenceId){
        // 小区边界信息
        List<ResidenceBoundary> boundaries = residenceService.selectResiBoundaryById(residenceId);
        if (null == boundaries || boundaries.size() == 0) {
            //没有边界查询小区中心点
            boundaries = residenceService.selectOfResidenceCenter(residenceId);
        }
        return boundaries;
    }

    @RequestMapping(value = "changeBuildingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeBuildingInfo(@RequestParam(value = "buildingId")String buildingId,
                                                  @RequestParam(value = "buildingNo")String buildingNo,
                                                  @RequestParam(value = "houseCount")String houseCount,
                                                  @RequestParam(value = "totalFloor")String totalFloor){
        Map<String, Object> result = new HashMap<>();
        buildingNo = buildingNo + "号";
        boolean success = 1 == buildingService.updateOfBuildingInfo(buildingId, buildingNo, houseCount, totalFloor);
        System.out.println("buildingId:"+buildingId);
        // 楼栋详情信息
        OfBuilding ofBuilding = buildingService.getBuilidngInfoById(buildingId);
        result.put("status", success ? "success" : "failed");
        result.put("buildingInfo", ofBuilding);
        return result;
    }

    @RequestMapping(value = "changeBuildingCoordinate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeBuildingInfo(@RequestParam(value = "buildingId")String buildingId,
                                                  @RequestParam(value = "newLon")String newLon,
                                                  @RequestParam(value = "newLat")String newLat) {
        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == buildingService.updateOfBuildingCoordinate(buildingId, newLon, newLat);
        System.out.println("buildingId:"+buildingId);
        // 楼栋详情信息
        OfBuilding ofBuilding = buildingService.getBuilidngInfoById(buildingId);
        result.put("status", success ? "success" : "failed");
        result.put("buildingInfo", ofBuilding);
        return result;
    }

/*    @RequestMapping(value = "toSearch")
    public String toSearch(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "buildingId")String buildingId){
        if(null == buildingId || "".equals(buildingId)){
            return null;
        }
        System.out.println("buildingId:"+buildingId);
        // 楼栋详情信息
        OfBuilding ofBuilding = buildingService.getBuilidngListByBuildingId(buildingId);
        request.setAttribute("buildingList",ofBuilding);
        return "search";
    }*/
}
