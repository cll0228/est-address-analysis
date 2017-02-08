package com.lezhi.address.admin.webapp.controller;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.service.AnalyAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @RequestMapping(value = "addrQuery", method = RequestMethod.GET)
    @ResponseBody
    public List<Address> list(HttpServletRequest request, HttpServletResponse response) {
        List<Address> addresses = analyAddrService.getParsedFailedAddr();
        if(null == addresses){
            return null;
        }
        return addresses;
    }

}
