package com.lezhi.statistics.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cuill on 2017/3/16.
 * 實時概括
 */
public class RealTimeSummaryObj {
    private Integer id;

    private Integer uv;//独立访客（unique visitor）
    private Integer pv;//访问量（page view）
    private Integer nv;//第一次访问东方电视云平台频道内容的独立用户数（new visitor）
    private Date refreshTime;//unix time, 单位ms，统计刷新时间

    public String getRefreshTimeUTC_8() {
        if (refreshTime == null)
            return null;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(refreshTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getNv() {
        return nv;
    }

    public void setNv(Integer nv) {
        this.nv = nv;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }
}
