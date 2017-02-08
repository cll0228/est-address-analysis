package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.DbServer;
import com.lezhi.address.admin.pojo.TaskManageInfo;
import com.lezhi.address.admin.service.AnalyAddrService;
import com.lezhi.address.admin.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chendl on 2017/2/07.
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("all")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "dataSourceManage", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "dataSourceManage";
    }

    @RequestMapping(value = "getDataSourceList", method = RequestMethod.GET)
    @ResponseBody
    public List<DbServer> getDataSourceList(HttpServletRequest request, HttpServletResponse response) {
        List<DbServer> dbServers = dataSourceService.getDataSourceList();
        if(null == dbServers){
            return null;
        }
        for(DbServer dbServer: dbServers){
            dbServer.setCreateTime(dbServer.getCreateTime().replace(".0",""));
        }

        return dbServers;
    }

    @RequestMapping(value = "addServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addServer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String serverIp = request.getParameter("newServerIp");
        String userName = request.getParameter("newUserName");
        String password = request.getParameter("newPassword");
        String alias = request.getParameter("newAlias");
        String addStaff = String.valueOf(session.getAttribute("username"));

        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.addServer(serverIp, userName, password, alias, addStaff);
        System.out.println("serverIp:"+serverIp);
        result.put("status", success ? "success" : "failed");
        List<DbServer> dbServers = dataSourceService.getDataSourceList();
        for(DbServer dbServer: dbServers){
            dbServer.setCreateTime(dbServer.getCreateTime().replace(".0",""));
        }
        result.put("dbServerList", dbServers);
        return result;
    }

    @RequestMapping(value = "editServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editServer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String serverIp = request.getParameter("sip");
        String userName = request.getParameter("uname");
        String password = request.getParameter("pword");
        String alias = request.getParameter("bieming");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String operatorStaff = String.valueOf(session.getAttribute("username"));

        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.editServer(serverIp, userName, password, alias, operatorStaff, id);
        System.out.println("serverIp:"+serverIp);
        result.put("status", success ? "success" : "failed");
        List<DbServer> dbServers = dataSourceService.getDataSourceList();
        for(DbServer dbServer: dbServers){
            dbServer.setCreateTime(dbServer.getCreateTime().replace(".0",""));
        }
        result.put("dbServerList", dbServers);
        return result;
    }
}
