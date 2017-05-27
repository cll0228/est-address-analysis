package com.lezhi.statistics.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by Cuill on 2017/3/21.
 */
public class TrendResult extends CommonResult {
    private Map<String, List<TrendObj>> trend;

    public TrendResult(String status, Map<String, List<TrendObj>> trend, String errMsg) {
        super(status, errMsg);
        this.trend = trend;
    }

    public TrendResult() {

    }

    public Map<String, List<TrendObj>> getTrend() {
        return trend;
    }

    public void setTrend(Map<String, List<TrendObj>> trend) {
        this.trend = trend;
    }

}
