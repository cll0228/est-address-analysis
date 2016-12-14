package com.address.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.address.model.ReturnParam;
import com.address.service.StdService;

/**
 * Created by Cuill on 2016/12/12.
 */
@Controller
@RequestMapping(value = "/")
public class StdController {

    @Autowired
    private StdService stdService;

    @RequestMapping("index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> analysis(@RequestParam("address") String address) {
        Map<String, Object> result = new HashMap<>();
        ReturnParam param = stdService.analysis(address);
        if (param != null) {
            result.put("qx", param.getDistrict());
            result.put("jd", param.getStreet());
            result.put("jw", param.getCommitte());
            result.put("ln", param.getRoadLane());
            result.put("h", param.getBuilding());
            result.put("s", param.getHouseNo());
            result.put("bm", param.getAddrCode());
            result.put("f", param.getFlag());
        }
        return result;

    }

}
