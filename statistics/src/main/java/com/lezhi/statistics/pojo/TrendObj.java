package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/21.
 */
public class TrendObj {
    private Long timeBegin;
    private Long timeEnd;
    private Integer uv;
    private Integer pv;
    private Integer nu;
    private Long avgTop;

    public Long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
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

    public Integer getNu() {
        return nu;
    }

    public void setNu(Integer nu) {
        this.nu = nu;
    }

    public Long getAvgTop() {
        return avgTop;
    }

    public void setAvgTop(Long avgTop) {
        this.avgTop = avgTop;
    }
}
