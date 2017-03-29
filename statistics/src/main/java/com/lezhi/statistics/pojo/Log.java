package com.lezhi.statistics.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cuill on 2017/3/28.
 */
public class Log {
    private Integer logId;
    private Integer channel;
    private String mac;
    private Date timeStamp;
    private String session;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static SimpleDateFormat getFormat1() {
        return format1;
    }

    public static void setFormat1(SimpleDateFormat format1) {
        Log.format1 = format1;
    }

    static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
