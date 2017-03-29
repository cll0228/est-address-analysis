package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/28.
 */
public class HisTrendInfo extends TrendObj{
    private Integer districtId;
    private Integer blockId;
    private Integer id;
    private Integer channelNo;

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
}
