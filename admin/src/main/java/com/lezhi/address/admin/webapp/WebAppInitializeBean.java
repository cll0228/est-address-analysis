package com.lezhi.address.admin.webapp;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class WebAppInitializeBean implements ServletContextAware {


    @Override
    public void setServletContext(ServletContext context) {
        context.setAttribute("ctx", context.getContextPath());
    }
}
