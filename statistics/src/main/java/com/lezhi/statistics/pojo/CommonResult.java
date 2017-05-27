package com.lezhi.statistics.pojo;

/**
 * Created by Colin Yan on 2017/5/27.
 */
public class CommonResult {
    private String status;

    private String errMsg;

    public CommonResult(String status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public CommonResult() {
    }

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
