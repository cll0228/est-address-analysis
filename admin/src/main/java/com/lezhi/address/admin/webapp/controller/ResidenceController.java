package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Cuill on 2017/1/17.
 */
@Controller
@RequestMapping("/")
public class ResidenceController {

    @Autowired
    private ResidenceService residenceService;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "search";
    }
    
    @RequestMapping(value = "residenceDetail", method = RequestMethod.GET)
    public String residenceDetail(HttpServletRequest request, HttpServletResponse response) {
        return "residenceDetail";
    }

    @RequestMapping(value = "query",method = RequestMethod.POST)
    @ResponseBody
    public List<OfResidence> queryResidence(@RequestParam(value = "keyword",required = false)String keyword){
        if(null == keyword || "".equals(keyword)){
            return null;
        }
        List<OfResidence> ofResidences = residenceService.selectResidenceByName(keyword);
        if(null == ofResidences){
            return null;
        }
        return ofResidences;
    }
}
