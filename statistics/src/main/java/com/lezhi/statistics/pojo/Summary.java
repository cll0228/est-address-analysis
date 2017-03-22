package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/22.
 */
public class Summary extends StateInfo {
    private Object channelSummaries;

    public Summary(String status, Object channelSummaries, String errMsg) {
        super(status, errMsg);
        this.channelSummaries = channelSummaries;
    }

    public Summary() {

    }

    public Object getChannelSummaries() {
        return channelSummaries;
    }

    public void setChannelSummaries(Object channelSummaries) {
        this.channelSummaries = channelSummaries;
    }
}
