package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/13.
 */
public class ReturnObj extends PageResult {
    private String status;

    private Object histories;

    private String errMsg;

    public ReturnObj(String status, Object obj, String errMsg) {
        this.status = status;
        this.histories = obj;
        this.errMsg = errMsg;
    }

    public ReturnObj() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getHistories() {
        return histories;
    }

    public void setHistories(Object histories) {
        this.histories = histories;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
