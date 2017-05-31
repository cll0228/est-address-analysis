package com.lezhi.statistics.pojo;

import java.util.Date;

/**
 * Created by Colin Yan on 2017/5/31.
 */
public class ScheduleTaskExec {

    private Integer id;
    private Date lastExecTime;
    private Integer refId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Date lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }
}
