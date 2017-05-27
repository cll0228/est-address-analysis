package com.lezhi.statistics.pojo;

import java.util.List;

/**
 * Created by Cuill on 2017/3/16.
 */
public class HistoryListResult<T> extends PageResult{
    private List<T> histories;

    public List<T> getHistories() {
        return histories;
    }

    public void setHistories(List<T> histories) {
        this.histories = histories;
    }

    public HistoryListResult(String status, List<T> histories, String errMsg) {
        setErrMsg(errMsg);
        setStatus(status);
        this.histories = histories;
    }

    public HistoryListResult() {

    }
}
