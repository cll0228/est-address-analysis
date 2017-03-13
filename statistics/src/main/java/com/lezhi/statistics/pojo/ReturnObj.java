package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/13.
 */
public class ReturnObj extends PageResult {
    private String status;

    private Object obj;

    private String errMsg;

    public ReturnObj(String status, Object obj, String errMsg) {
        this.status = status;
        this.obj = obj;
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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
