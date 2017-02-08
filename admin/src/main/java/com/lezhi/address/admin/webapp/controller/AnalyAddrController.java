package com.lezhi.address.admin.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.TaskManageInfo;
import com.lezhi.address.admin.service.AnalyAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.address.admin.pojo.OfBuilding;
import com.lezhi.address.admin.pojo.ResidenceBoundary;
import com.lezhi.address.admin.service.BuildingService;
import com.lezhi.address.admin.service.ResidenceService;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("all")
public class AnalyAddrController {

    @Autowired
    private AnalyAddrService analyAddrService;

    @RequestMapping(value = "addr_list", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "addr_list";
    }

    @RequestMapping(value = "editAddr", method = RequestMethod.GET)
    public String toPage1(HttpServletRequest request, HttpServletResponse response) {
        return "editAddr";
    }


    @RequestMapping(value = "analysisTask", method = RequestMethod.GET)
    public String toAnalysisTask(HttpServletRequest request, HttpServletResponse response) {
        return "analysisTask";
    }

    @RequestMapping(value = "addrQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<Address> list(HttpServletRequest request, HttpServletResponse response) {
        List<Address> addresses = analyAddrService.getParsedFailedAddr();
        if(null == addresses){
            return null;
        }
        return addresses;
    }

    @RequestMapping(value = "getAnalysisTask", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskManageInfo> getAnalysisTask(HttpServletRequest request, HttpServletResponse response) {
        List<TaskManageInfo> taskManageInfos = new ArrayList<>();
        TaskManageInfo taskManageInfo = new TaskManageInfo();
        taskManageInfo.setId(1);
        taskManageInfo.setDataSourceServer("192.168.201.26");
        taskManageInfo.setDataTable("ocn_address");
        taskManageInfo.setAddressColumn("address");
        taskManageInfo.setTotalCount(10000);
        taskManageInfo.setSuccessCount(8000);
        taskManageInfo.setFailCount(2000);
        taskManageInfo.setSchedule("35%");
        taskManageInfo.setStatus("进行中");
        taskManageInfo.setStartTime("2017/02/06 14:38");
        taskManageInfo.setFinishTime("2017/02/06 18:58");
        taskManageInfo.setOperator("cdl");
        taskManageInfos.add(taskManageInfo);
        if(null == taskManageInfos){
            return null;
        }
        return taskManageInfos;
    }
}
