package com.lezhi.statistics.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chendl on 2017/3/31.
 */
public class NewVisitorInfo {
    private Integer id;
    private Integer channelNo;
    private String mac;
    private String firstVisitTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(String firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }
}