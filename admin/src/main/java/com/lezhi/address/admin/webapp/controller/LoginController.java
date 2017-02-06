package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.BuildingService;
import com.lezhi.address.admin.service.ResidenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private ResidenceService residenceService;

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
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	if(username.equals("dfjr")) {
    		//在Session里保存信息  
            session.setAttribute("username", username);
    		boolean success = true;
    		result.put("status", success);
    		return result;
    	} else {
    		boolean success = false;
    		result.put("status", success);
    		return result;
    	}
    }  
      
    /** 
     * 退出系统 
     * @param session 
     *          Session 
     * @return 
     * @throws Exception 
     */  
    @RequestMapping(value="/logout")  
    public String logout(HttpSession session) throws Exception{  
        //清除Session  
        session.invalidate();  
          
        return "redirect:hello.action";  
    }
}
