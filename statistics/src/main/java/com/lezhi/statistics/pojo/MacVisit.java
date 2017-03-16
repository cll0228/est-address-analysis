package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/16.
 */
public class MacVisit extends StateInfo{
    private Object histories;

    public Object getHistories() {
        return histories;
    }

    public void setHistories(Object histories) {
        this.histories = histories;
    }

    public MacVisit(String status,Object histories, String errMsg) {
        super(status, errMsg);
        this.histories = histories;
    }

    public MacVisit() {

    }
}
