package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/21.
 */
public class Trend extends StateInfo {
    private Object trend;

    public Trend(String status, Object trend, String errMsg) {
        super(status, errMsg);
        this.trend = trend;
    }

    public Trend() {

    }

    public Object getTrend() {
        return trend;
    }

    public void setTrend(Object trend) {
        this.trend = trend;
    }

}
