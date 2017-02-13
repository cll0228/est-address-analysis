package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.TaskManageInfo;
import com.lezhi.address.admin.service.AnalyAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("all")
public class AnalysisTaskController {

    @Autowired
    private AnalyAddrService analyAddrService;

    @RequestMapping(value = "analysisTask", method = RequestMethod.GET)
    public String toAnalysisTask(HttpServletRequest request, HttpServletResponse response) {
        return "analysisTask";
    }

    @RequestMapping(value = "getAnalysisTask", method = RequestMethod.GET)
    @ResponseBody
    public List<TaskManageInfo> getAnalysisTask(HttpServletRequest request, HttpServletResponse response) {
        List<TaskManageInfo> taskManageInfos =analyAddrService.getAnalyAddrTaskList();
        if(null == taskManageInfos){
            return null;
        }
        return taskManageInfos;
    }
}
