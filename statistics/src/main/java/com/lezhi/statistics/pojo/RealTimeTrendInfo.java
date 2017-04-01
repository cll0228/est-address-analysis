package com.lezhi.statistics.pojo;

import java.util.Date;

/**
 * Created by chendl on 2017/3/29.
 */
public class RealTimeTrendInfo extends StateInfo{
    private Integer id;
    private String channelNo;
    private Date beginTime;
    private Date endTime;
    private Integer uv;
    private Integer pv;
    private Integer nv;
    private Integer avgTop;
    private Integer districtId;
    private Integer blockId;
    private Integer residenceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getAvgTop() {
        return avgTop;
    }

    public void setAvgTop(Integer avgTop) {
        this.avgTop = avgTop;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }
}
