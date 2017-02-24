package com.lezhi.adminlj.controller;

import java.util.List;

import com.lezhi.adminlj.pojo.*;
import com.lezhi.adminlj.util.MyUtli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.lezhi.adminlj.service.impl.MapService;

import javax.naming.directory.SearchControls;

/**
 * Created by Cuill on 2017/2/21.
 */
@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @Autowired
    private SearchController controller;

    @RequestMapping(value = "/districtcentertude", method = RequestMethod.GET)
    @ResponseBody
    public List<ShDistrict> getDistrictCenterTude(
            @RequestParam(value = "districtId", required = false) Integer districtId) {
        List<ShDistrict> shDistricts = mapService.getDistrictCenterTude(districtId);
        shDistricts = MyUtli.changData(shDistricts, controller.myDataList);
        if (null == shDistricts) {
            return null;
        }
        return shDistricts;
    }

    @RequestMapping(value = "/districtboundaydata/{districtId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ShDistrict> getDistrictBoundryInfo(@PathVariable(value = "districtId") Integer districtId) {
        List<ShDistrict> shDistricts = mapService.getDistrictBoundryInfo(districtId);
        if (null == shDistricts) {
            return null;
        }
        return shDistricts;
    }

    @RequestMapping(value = "showtown", method = RequestMethod.GET)
    @ResponseBody
    public List<Town> showtown(@RequestParam(value = "districtId", required = false) Integer districtId,
            @RequestParam(value = "townId", required = false) Integer townId) {
        List<Town> towns = mapService.showtown(districtId, townId);
        towns = MyUtli.changData1(towns, controller.myDataList);
        if (null == towns) {
            return null;
        }
        return towns;
    }

    @RequestMapping(value = "neighborhood", method = RequestMethod.GET)
    @ResponseBody
    public List<Neighborhood> neighborhood(@RequestParam(value = "townId", required = false) Integer townId,
            @RequestParam(value = "neighborhoodId", required = false) String neighborhoodId) {
        List<Neighborhood> neighborhoods = mapService.neighborhood(townId, neighborhoodId);
        neighborhoods = MyUtli.changData2(neighborhoods, controller.myDataList);
        if (null == neighborhoods) {
            return null;
        }
        return neighborhoods;
    }

    @RequestMapping(value = "/getResidence", method = RequestMethod.GET)
    @ResponseBody
    public List<Residence> residence(
            @RequestParam(value = "neighborhoodId", required = false) String neighborhoodId,
            @RequestParam(value = "residenceId", required = false) String residenceId) {
        List<Residence> Residence = mapService.residence(neighborhoodId, residenceId);
        if (null == Residence) {
            return null;
        }
        return Residence;
    }

}
