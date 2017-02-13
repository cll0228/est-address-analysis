package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.DbServer;
import com.lezhi.address.admin.pojo.TDatasource;
import com.lezhi.address.admin.pojo.TaskManageInfo;
import com.lezhi.address.admin.service.AnalyAddrService;
import com.lezhi.address.admin.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<TDatasource> getDataSourceList(HttpServletRequest request, HttpServletResponse response) {
        List<TDatasource> tDatasources = dataSourceService.getDataSourceList();

        return tDatasources;
    }

    @RequestMapping(value = "addServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addServer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String serverIp = request.getParameter("server");
        String type = request.getParameter("type");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String alias = request.getParameter("alias");
        String addStaff = String.valueOf(session.getAttribute("userId"));

        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.addServer(serverIp, type, userName, password, alias, addStaff);
        System.out.println("serverIp:"+serverIp);
        result.put("status", success ? "success" : "failed");
        List<TDatasource> dbServers = dataSourceService.getDataSourceList();
        result.put("dbServerList", dbServers);
        return result;
    }

    @RequestMapping(value = "editServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editServer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String serverIp = request.getParameter("server");
        String type = request.getParameter("type");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String alias = request.getParameter("alias");
        Integer id = Integer.valueOf(request.getParameter("id"));
        String operateStaff = String.valueOf(session.getAttribute("userId"));

        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.editServer(serverIp, type, userName, password, alias, operateStaff, id);
        System.out.println("serverIp:"+serverIp);
        result.put("status", success ? "success" : "failed");
        List<TDatasource> dbServers = dataSourceService.getDataSourceList();
        result.put("dbServerList", dbServers);
        return result;
    }
    @RequestMapping(value = "deleteServer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteServer(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession();
        String operateStaff = String.valueOf(session.getAttribute("userId"));
        Map<String, Object> result = new HashMap<>();
        boolean success = 1 == dataSourceService.deleteServer(operateStaff, id);
        System.out.println("id:"+id);
        result.put("status", success ? "success" : "failed");
        List<TDatasource> dbServers = dataSourceService.getDataSourceList();
        result.put("dbServerList", dbServers);
        return result;
    }
}
