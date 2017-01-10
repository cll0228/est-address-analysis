package com.address.util;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by Cuill on 2017/1/5.
 */
public class WebAppInitializeBean implements ServletContextAware {
    private File cacheFile;

    @Override
    public void setServletContext(ServletContext context) {
        context.setAttribute("ctx", context.getContextPath());
        cacheFile = new File(context.getRealPath("/"), "stat.cache");
    }

    public File getCacheFile() {
        return cacheFile;
    }

    public void setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
    }
}
