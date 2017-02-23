package com.lezhi.adminlj.controller;

import java.util.List;

import com.lezhi.adminlj.pojo.Neighborhood;
import com.lezhi.adminlj.pojo.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.lezhi.adminlj.pojo.ShDistrict;
import com.lezhi.adminlj.service.impl.MapService;

/**
 * Created by Cuill on 2017/2/21.
 */
@Controller
public class MapController {

    @Autowired
    private MapService mapService;

    @RequestMapping(value = "/districtcentertude", method = RequestMethod.GET)
    @ResponseBody
    public List<ShDistrict> getDistrictCenterTude() {
        List<ShDistrict> shDistricts = mapService.getDistrictCenterTude();
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
    public List<Town> showtown(@RequestParam(value = "districtId",required = false) Integer districtId) {
        List<Town> shDistricts = mapService.showtown(districtId);
        if (null == shDistricts) {
            return null;
        }
        return shDistricts;
    }


    @RequestMapping(value = "neighborhood", method = RequestMethod.GET)
    @ResponseBody
    public List<Neighborhood> neighborhood(@RequestParam(value = "townId",required = false) Integer townId) {
        List<Neighborhood> shDistricts = mapService.neighborhood(townId);
        if (null == shDistricts) {
            return null;
        }
        return shDistricts;
    }

}
