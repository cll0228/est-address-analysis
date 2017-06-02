package com.lezhi.statistics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Colin Yan on 2017/6/1.
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler()
    @ResponseBody
    public Map<String, Object> onError(Exception ex) {
        logger.error("catch:", ex);
        Map<String, Object> result = new HashMap<>();
        result.put("status", "failed");
        result.put("errMsg", "当你看到这个提示的时候，说明系统发生了一些错误");
        return result;
    }
}
