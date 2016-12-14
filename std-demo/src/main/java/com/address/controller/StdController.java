package com.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StdController.class);

    @RequestMapping("index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> analysis(@RequestParam("address") String address) {
        LOGGER.info("正在执行请求address=" + address);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<ReturnParam> paramList = stdService.analysis(address);
        LOGGER.info("返回resultList条数=" + paramList.size());
        if (paramList != null) {
            for (ReturnParam returnParam : paramList) {
                Map<String, Object> result = new HashMap<>();
                result.put("qx", returnParam.getDistrict());
                result.put("jd", returnParam.getStreet());
                result.put("jw", returnParam.getCommitte());
                result.put("ln", returnParam.getRoadLane());
                result.put("h", returnParam.getBuilding());
                result.put("s", returnParam.getHouseNo());
                result.put("bm", returnParam.getAddrCode());
                result.put("f", returnParam.getFlag());
                LOGGER.info("返回状态码 flag = " + returnParam.getFlag());
                resultList.add(result);
            }
        }
        return resultList;

    }

}
