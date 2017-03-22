package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/22.
 */
public class ChannelSummaryObj {
    private Integer channelNo;//频道号
    private Integer uv;//独立访客（unique visitor）
    private Integer nv;//访问量（page view）
    private Integer pv;//第一次访问东方电视云平台频道内容的独立用户数（new visitor）

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getNv() {
        return nv;
    }

    public void setNv(Integer nv) {
        this.nv = nv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }
}
