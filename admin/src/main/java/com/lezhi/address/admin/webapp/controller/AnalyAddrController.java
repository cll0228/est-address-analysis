package com.lezhi.address.admin.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lezhi.address.admin.pojo.AnalyMatchDto;
import com.lezhi.address.admin.service.AnalyAddrService;

/**
 * Created by chendl on 2017/1/18.
 */
@Controller
@RequestMapping("/")
@SuppressWarnings("all")
public class AnalyAddrController {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AnalyAddrService analyAddrService;

    @RequestMapping(value = "addr_list", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request, HttpServletResponse response) {
        return "addr_list";
    }

    @RequestMapping(value = "editAddr", method = RequestMethod.GET)
    public ModelAndView toPage1(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "addressId", required = false) Integer addressId,
            @RequestParam(value = "dataName", required = false) String dataName,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "name", required = false) String name) {
        AnalyMatchDto dto = analyAddrService.selectAddressById(addressId, dataName, tableName);
        String name1 = request.getParameter("name");
        try {
            name1 = new String(name1.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dto.setName(name1);
        ModelAndView mv = new ModelAndView();
        mv.addObject("analyMatchDto", dto);
        mv.addObject("dataName", dataName);
        mv.addObject("tableName", tableName);
        mv.addObject("id", id);
        mv.setViewName("editAddr");
        return mv;
    }

    @RequestMapping(value = "addrQuery", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "analySisTaskId", required = false) Integer analysisTaskId,
            @RequestParam(value = "page", required = false) Integer page) {
        ModelAndView view = new ModelAndView();
        System.out.println(analysisTaskId);
        // List<Address> addresses = analyAddrService.getParsedFailedAddr();
        List<AnalyMatchDto> analyMatchDtos = analyAddrService.getAnalyMatchList(analysisTaskId,page);
        if (null == analyMatchDtos) {
            view.addObject("analyMatchDtos",null);
        }
        try {
            view.addObject("analyMatchDtos",mapper.writeValueAsString(analyMatchDtos));
        } catch (JsonProcessingException e) {
            System.out.println("com.lezhi.address.admin.webapp.controller.AnalyAddrController解析失败"+e);
        }
        view.setViewName("addr_list");
        return view;
    }

    @RequestMapping(value = "addrQueryPage", method = RequestMethod.GET)
    @ResponseBody
    public List<AnalyMatchDto> list1(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "analySisTaskId", required = false) Integer analysisTaskId,
                             @RequestParam(value = "page", required = false) Integer page) {
        ModelAndView view = new ModelAndView();
        System.out.println(analysisTaskId);
        // List<Address> addresses = analyAddrService.getParsedFailedAddr();
        List<AnalyMatchDto> analyMatchDtos = analyAddrService.getAnalyMatchList(analysisTaskId,page);
        if (null == analyMatchDtos) {
            return null;
        }
        return analyMatchDtos;
    }
}
