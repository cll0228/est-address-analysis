package com.address.controller;

import com.address.mapper.StdMapper;
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
    public List<ResidenceBoundary> boundary(@RequestParam(value = "residenceId",required = false) String residenceId) {
        // 小区边界信息
        List<ResidenceBoundary> boundaries = stdMapper.selectResiBoundaryById(residenceId);
        if (null == boundaries) {
            return null;
        }
        return boundaries;
    }
}
