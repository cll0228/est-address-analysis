package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.DbServer;
import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.BuildingService;
import com.lezhi.address.admin.service.DataSourceService;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chendl on 2017/02/08.
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("all")
public class NewTaskController {
    @Autowired
    private DataSourceService dataSourceService;

    @RequestMapping(value = "newTask", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "newTask";
    }

    @RequestMapping(value = "getNewTaskInfo", method = RequestMethod.GET    )
    @ResponseBody
    public Map<String, Object> getNewTaskInfo(HttpServletRequest request, HttpServletResponse response) {
        List<DbServer> dbServers = dataSourceService.getDataSourceList();
        Map<String, Object> result = new HashMap<>();
        List<String> serverList = new ArrayList<>();
        if(dbServers.size()>0){
            for(DbServer dbServer: dbServers){
                serverList.add(dbServer.getServerIp());
            }
        }
/*        serverList.add("192.168.1.1");
        serverList.add("192.168.1.2");
        serverList.add("192.168.1.3");
        serverList.add("192.168.1.4");*/
        result.put("serverList",serverList);
        result.put("tableName","ocn_address");
        result.put("addressColumn","address");
        result.put("taskName","农行复评地址解析");
        result.put("status", 1==1 ? "success" : "failed");
        return result;
    }

    @RequestMapping(value = "addNewServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addNewServer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String serverIp = request.getParameter("newServerIp");
        String userName = request.getParameter("newUserName");
        String password = request.getParameter("newPassword");
        String alias = request.getParameter("newAlias");
        String addStaff = String.valueOf(session.getAttribute("username"));

        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.addServer(serverIp, userName, password, alias, addStaff);
        List<DbServer> dbServers = dataSourceService.getDataSourceList();
        List<String> serverList = new ArrayList<>();
        if(dbServers.size()>0){
            for(DbServer dbServer: dbServers){
                serverList.add(dbServer.getServerIp());
            }
        }
        result.put("serverList",serverList);
        result.put("status", success ? "success" : "failed");

        return result;
    }
}
