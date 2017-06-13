package com.lezhi.statistics.pojo;

import java.util.Date;

/**
 * Created by Cuill on 2017/3/13.
 */
public class MacVisitHistoryInfo {
    private String mac;// 机顶盒地址

    private Integer pv;// 期间访问量

    private String channelNo;

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    private Date firstVisitTime;// 期间首次访问时间。 unix time, 单位ms

    private Date lastVisitTime;// 期间最后访问时间。 unix time, 单位ms

    private Long totalTop;// 期间总访问时间(time on page) 。unix time, 单位ms

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Date getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(Date firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Long getTotalTop() {
        return totalTop;
    }

    public void setTotalTop(Long totalTop) {
        this.totalTop = totalTop;
    }
}
