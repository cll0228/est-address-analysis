package com.address.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String taskPerformance() {
        return "std";
    }

    // 执行性能跟踪
    @RequestMapping("index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> analysis(@RequestParam("address") String address) {
        Map<String, Object> result = new HashMap<>();
        result.put("aaa", address);
        return result;

    }

}
