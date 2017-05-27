package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/16.
 */
public class RealTimeSummaryResult extends CommonResult{
    private RealTimeSummaryObj realtimeSummary;

    public RealTimeSummaryObj getRealtimeSummary() {
        return realtimeSummary;
    }

    public void setRealtimeSummary(RealTimeSummaryObj realtimeSummary) {
        this.realtimeSummary = realtimeSummary;
    }

    public RealTimeSummaryResult(String status, RealTimeSummaryObj realtimeSummary, String errMsg) {
        super(status, errMsg);
        this.realtimeSummary = realtimeSummary;
    }

    public RealTimeSummaryResult() {

    }
}
