package com.lezhi.address.admin.webapp.controller;

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

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.TUser;
import com.lezhi.address.admin.service.LoginService;
import com.lezhi.address.admin.service.TaskService;
import com.lezhi.address.admin.util.Iputil;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TaskService taskService;

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
//    	int id = taskService.createAnalysisTask(1, "id", "bbc_address", "address", "ocn_address", "BBC地址", true, 1);
//    	System.out.println(id);
    	boolean success = false;
    	if(username!=null) {
    		TUser tUser= loginService.userLogin(username);
    		if(tUser!=null) {
    			if(null!=tUser.getPassword()) {
    				if(tUser.getPassword().equals(password)) {
    					//在Session里保存信息  
    		            session.setAttribute("username", username);
    		            session.setAttribute("userId", tUser.getId());
    		    		success = true;
    		    		result.put("status", success);
    		    		//插入t_user表最后登录时间和ip
    		    		String lastLoginIp = Iputil.getIpAddr(request);
    		    		tUser.setLastLoginIp(lastLoginIp);
    		    		loginService.updateLoginTime(tUser);
    				} else {
    					success = false;
    	        		result.put("status", success);
    				}
    			}
    		} else {
    			success = false;
        		result.put("status", success);
    		}
    	} else {
			success = false;
    		result.put("status", success);
		}
		return result;
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
          
        return "redirect:search.do";  
    }
}
