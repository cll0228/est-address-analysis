package com.lezhi.address.admin.webapp.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        if (null != residenceBoundaries && residenceBoundaries.size() != 0) {
            ofResidences.setResidenceBoundaries(objectMapper.writeValueAsString(residenceBoundaries));
        } else
            ofResidences.setResidenceBoundaries(new ArrayList<>());
        //封装要显示到视图的数据
        mv.addObject("ofResidences",ofResidences);
        //视图名
        mv.setViewName("residenceDetail");
        return mv;
    }

    @RequestMapping(value = "detailModify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> detailModify(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> result = new HashMap<>();
    	OfResidence ofResidence = new OfResidence();
    	ofResidence.setResidenceName(request.getParameter("xqm"));
    	ofResidence.setResidenceAddr(request.getParameter("xqdz"));
    	ofResidence.setId(Integer.parseInt(request.getParameter("xqid")));
    	if(request.getParameter("zfws")!=null) {
    		ofResidence.setHouseCount(Integer.parseInt(request.getParameter("zfws")));
    	}
    	if(request.getParameter("zlds")!=null) {
    		ofResidence.setBuildingCount(Integer.parseInt(request.getParameter("zlds")));
    	}
    	if(request.getParameter("lhl")!=null) {
    		ofResidence.setGp(Double.parseDouble(request.getParameter("lhl")));
    	}
		if(request.getParameter("rjl")!=null) {
			ofResidence.setVp(Double.parseDouble(request.getParameter("rjl")));
		}
    	
    	boolean success = 1 == residenceService.updateOfResidenceInfo(ofResidence);
//    	ofm.setSsqx(request.getParameter("ssqx"));
//    	ofm.setSsbk(request.getParameter("ssbk"));
    	result.put("status", success);
        result.put("modifyTime", new Date());
    	return result;
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

    @RequestMapping(value = "changeResidenceCoordinate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeBuildingInfo(@RequestParam(value = "residenceId")String residenceId,
                                                  @RequestParam(value = "newLon")String newLon,
                                                  @RequestParam(value = "newLat")String newLat) {
        Map<String, Object> result = new HashMap<>();
//        boolean success = 1 == residenceService.updateOfResidenceCoordinate(residenceId, newLon, newLat);
        boolean success = true;
        result.put("status", success ? "success" : "failed");
        return result;
    }
}
