package com.address.controller;

import java.util.HashMap;
import java.util.Map;

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
    // 执行性能跟踪
    @RequestMapping("std")
    public String taskPerformance(HttpServletRequest request, HttpServletResponse response) {

        return "std";
    }
    
 // 执行性能跟踪
    @RequestMapping("index")
    public String indexPage(HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }
    
    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
	public Map<?, ?> analysis(@RequestParam("address")String address) {
        Map<String, Object> result = new HashMap<>();
        StdModel stdModel = AddressExtractor.parseAll(address);
        if(stdModel!=null) {
        	result.put("qx", stdModel.getDistrict());
            result.put("jd", stdModel.getStreet());
            result.put("jw", stdModel.getCommitte());
            result.put("ln", stdModel.getLane());
            result.put("h", stdModel.getBuilding());
            result.put("s", stdModel.getHouseNum());
            result.put("bm", stdModel.getCode());
            result.put("f", stdModel.getFlag());
        }
        return result;

	}

}
