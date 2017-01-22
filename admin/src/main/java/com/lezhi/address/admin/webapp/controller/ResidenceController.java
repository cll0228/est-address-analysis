package com.lezhi.address.admin.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.ResidenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Cuill on 2017/1/17.
 */
@Controller
@RequestMapping("/")
public class ResidenceController {

    @Autowired
    private ResidenceService residenceService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "search";
    }
    
    @RequestMapping(value = "residenceDetail", method = RequestMethod.GET)
    public ModelAndView residenceDetail(@RequestParam(value = "residenceId",required = false)Integer residenceId) throws JsonProcessingException {
    	ModelAndView mv = new ModelAndView();
    	OfResidence ofResidences = residenceService.selectResidenceDetailByResidenceId(residenceId);
        //查询边界
        List<ResidenceBoundary> residenceBoundaries = residenceService.selectResiBoundaryById(residenceId.toString());
        if(null != residenceBoundaries && residenceBoundaries.size() != 0){
            ofResidences.setResidenceBoundaries(objectMapper.writeValueAsString(residenceBoundaries));
        }
        //封装要显示到视图的数据
        mv.addObject("ofResidences",ofResidences);
        //视图名
        mv.setViewName("residenceDetail");
        return mv;
    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    public List<OfResidence> queryResidence(@RequestParam(value = "keyword", required = false) String keyword) {
        if (null == keyword || "".equals(keyword)) {
            return null;
        }
        List<OfResidence> ofResidences = residenceService.selectResidenceByName(keyword);
        if (null == ofResidences) {
            return null;
        }
        return ofResidences;
    }

    @RequestMapping(value = "build", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryBuild(@RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false) Integer page) {
        if (null == id || "".equals(id)) {
            return null;
        }
        return residenceService.selectBuildById(id, page);
    }
}
