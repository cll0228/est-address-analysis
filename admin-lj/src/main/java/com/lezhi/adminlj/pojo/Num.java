package com.lezhi.adminlj.pojo;

/**
 * Created by Cuill on 2017/2/23.
 */
public class Num {
    private Integer hdUserNum;
    private Integer districtId;
    private String cenLon;
    private String cenLat;
    private String houseCount;
    private String rate;
    public String getHouseCount() {
        return houseCount;
    }
    public void setHouseCount(String houseCount) {
        this.houseCount = houseCount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCenLat() {
        return cenLat;
    }

    public void setCenLat(String cenLat) {
        this.cenLat = cenLat;
    }

    public String getCenLon() {
        return cenLon;
    }

    public void setCenLon(String cenLon) {
        this.cenLon = cenLon;
    }

    public Integer getDistrictId() {
        return districtId;
    }
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getHdUserNum() {
        return hdUserNum;
    }

    public void setHdUserNum(Integer hdUserNum) {
        this.hdUserNum = hdUserNum;
    }
}
