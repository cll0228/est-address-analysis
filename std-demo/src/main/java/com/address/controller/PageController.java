package com.address.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Cuill on 2016/12/12.
 */
@Controller
@RequestMapping(value = "/")
public class PageController {
    // 执行性能跟踪
    @RequestMapping("std")
    public String taskPerformance(HttpServletRequest request, HttpServletResponse response) {

        return "std";
    }

}
