package com.address.controller;

import java.util.HashMap;
import java.util.Map;

import com.address.service.StdService;
import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.address.model.StdModel;
import com.address.util.AddressExtractor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Cuill on 2016/12/12.
 */
@Controller
@RequestMapping(value = "/")
public class PageController {

    @Autowired
    private StdService stdService;

    // 执行性能跟踪
    @RequestMapping("std")
    public String taskPerformance() {
        return "std";
    }

    // 执行性能跟踪
    @RequestMapping("index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> analysis(@RequestParam("address") String address) {
        Map<String, Object> result = new HashMap<>();
        StdModel stdModel = stdService.analysis(address);
        if (stdModel != null) {
            result.put("qx", stdModel.getDistrict());
            result.put("jd", stdModel.getStreet());
            result.put("jw", stdModel.getCommitte());
            result.put("ln", stdModel.getRoad());
            result.put("h", stdModel.getBuilding());
            result.put("s", stdModel.getHouseNum());
            result.put("f", stdModel.getFlag());
        }
        return result;

    }

}
