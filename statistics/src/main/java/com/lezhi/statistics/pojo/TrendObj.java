package com.lezhi.statistics.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cuill on 2017/3/21.
 */
public class TrendObj {
    private Date timeBegin;
    private Date timeEnd;
    private Integer uv;
    private Integer pv;
    private Integer nv;
    private Long avgTop;

    public String getTimeBeginUTC_8() {
        if (timeBegin == null)
            return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeBegin);
    }
    public String getTimeEndUTC_8() {
        if (timeEnd == null)
            return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeEnd);
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getNv() {
        return nv;
    }

    public void setNv(Integer nv) {
        this.nv = nv;
    }

    public Long getAvgTop() {
        return avgTop;
    }

    public void setAvgTop(Long avgTop) {
        this.avgTop = avgTop;
    }
}
