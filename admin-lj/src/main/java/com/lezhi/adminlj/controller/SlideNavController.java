package com.lezhi.adminlj.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.adminlj.pojo.District;
import com.lezhi.adminlj.service.SlideNavService;


/**
 * Created by Wangyh on 2017/2/14.
 */
@Controller
@RequestMapping("/")
public class SlideNavController {
	@Autowired
	private SlideNavService slideNavService;
    /** 
     * 登录 
     * @param session 
     *          HttpSession 
     * @param username 
     *          用户名 
     * @param password 
     *          密码 
     * @return 
     */  
    @RequestMapping(value = "district", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> district(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	ArrayList<District> dList = slideNavService.districtList();
    	result.put("list", dList);
		return result;
    }  
}
