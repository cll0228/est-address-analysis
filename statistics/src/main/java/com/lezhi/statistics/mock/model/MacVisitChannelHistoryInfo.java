package com.lezhi.statistics.mock.model;

import com.lezhi.statistics.pojo.MacVisitHistoryInfo;

import java.util.List;

/**
 * Created by Colin Yan on 2017/5/25.
 */
public class MacVisitChannelHistoryInfo extends JsonPageResult {

    private List<MacVisitHistoryInfo> histories;

    public List<MacVisitHistoryInfo> getHistories() {
        return histories;
    }

    public void setHistories(List<MacVisitHistoryInfo> histories) {
        this.histories = histories;
    }
}
