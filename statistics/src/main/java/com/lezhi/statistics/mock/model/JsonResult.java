package com.lezhi.statistics.mock.model;

/**
 * Created by Colin Yan on 2017/5/25.
 */
public class JsonResult {

    private String status="success";
    private String errMsg="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}