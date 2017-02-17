package com.lezhi.adminlj.controller.webapp;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class WebAppInitializeBean implements ServletContextAware {


    @Override
    public void setServletContext(ServletContext context) {
        context.setAttribute("ctx", context.getContextPath());
    }
}
