package com.lezhi.statistics.pojo;

import java.util.List;

/**
 * Created by Cuill on 2017/3/22.
 */
public class SummaryResult extends PageResult {
    private List<ChannelSummaryObj> channelSummaries;

    public SummaryResult(String status, List<ChannelSummaryObj> channelSummaries, String errMsg) {
        setStatus(status);
        setErrMsg(errMsg);
        this.channelSummaries = channelSummaries;
    }

    public SummaryResult() {

    }

    public List<ChannelSummaryObj> getChannelSummaries() {
        return channelSummaries;
    }

    public void setChannelSummaries(List<ChannelSummaryObj> channelSummaries) {
        this.channelSummaries = channelSummaries;
    }
}
