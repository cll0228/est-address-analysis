package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/16.
 */
public class RealTimeSummary extends StateInfo{
    private Object realtimeSummary;

    public Object getRealtimeSummary() {
        return realtimeSummary;
    }

    public void setRealtimeSummary(Object realtimeSummary) {
        this.realtimeSummary = realtimeSummary;
    }

    public RealTimeSummary(String status, Object realtimeSummary, String errMsg) {
        super(status, errMsg);
        this.realtimeSummary = realtimeSummary;
    }

    public RealTimeSummary() {

    }
}
