package com.lezhi.statistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Colin Yan on 2017/3/27.
 */
@Controller
@RequestMapping
public class IndexController extends BaseController  {

    @RequestMapping(value = "/")
    public void home(HttpServletResponse response) throws IOException {
        response.getWriter().write("statistics index");
    }
}
