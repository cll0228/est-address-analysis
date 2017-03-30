package com.lezhi.statistics.pojo;

/**
 * Created by Cuill on 2017/3/28.
 */
public class HisTrendInfo extends TrendObj{
    private Integer districtId;
    private Integer blockId;
    private Integer id;
    private Integer residenceId;
    private Integer channelNo;
    private Long totalTop;

    //区县
    private Integer dis_uv;
    private Integer dis_pv;
    private Integer dis_nv;

    //板块
    private Integer blo_uv;
    private Integer blo_pv;
    private Integer blo_nv;

    //小区
    private Integer resi_uv;
    private Integer resi_pv;
    private Integer resi_nv;

    public Integer getDis_uv() {
        return dis_uv;
    }

    public void setDis_uv(Integer dis_uv) {
        this.dis_uv = dis_uv;
    }

    public Integer getDis_pv() {
        return dis_pv;
    }

    public void setDis_pv(Integer dis_pv) {
        this.dis_pv = dis_pv;
    }

    public Integer getDis_nv() {
        return dis_nv;
    }

    public void setDis_nv(Integer dis_nv) {
        this.dis_nv = dis_nv;
    }

    public Integer getBlo_uv() {
        return blo_uv;
    }

    public void setBlo_uv(Integer blo_uv) {
        this.blo_uv = blo_uv;
    }

    public Integer getBlo_pv() {
        return blo_pv;
    }

    public void setBlo_pv(Integer blo_pv) {
        this.blo_pv = blo_pv;
    }

    public Integer getBlo_nv() {
        return blo_nv;
    }

    public void setBlo_nv(Integer blo_nv) {
        this.blo_nv = blo_nv;
    }

    public Integer getResi_uv() {
        return resi_uv;
    }

    public void setResi_uv(Integer resi_uv) {
        this.resi_uv = resi_uv;
    }

    public Integer getResi_pv() {
        return resi_pv;
    }

    public void setResi_pv(Integer resi_pv) {
        this.resi_pv = resi_pv;
    }

    public Integer getResi_nv() {
        return resi_nv;
    }

    public void setResi_nv(Integer resi_nv) {
        this.resi_nv = resi_nv;
    }

    public Long getTotalTop() {
        return totalTop;
    }

    public void setTotalTop(Long totalTop) {
        this.totalTop = totalTop;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
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
