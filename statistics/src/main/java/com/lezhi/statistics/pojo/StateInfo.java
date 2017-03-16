package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/13.
 */
public class StateInfo extends PageResult {
    private String status;

    private String errMsg;

    public StateInfo(String status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public StateInfo() {
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
