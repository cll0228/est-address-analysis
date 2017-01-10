package com.address.model;

/**
 * Created by Cuill on 2017/1/10.
 */
public class OfBuilding {
    private Integer id;

    private String buildingNo;

    private Integer residenceId;

    private Integer stdAddrId;

    private Integer houseCount;

    private Integer totalFloor;

    private String lat;

    private String lon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    public Integer getStdAddrId() {
        return stdAddrId;
    }

    public void setStdAddrId(Integer stdAddrId) {
        this.stdAddrId = stdAddrId;
    }

    public Integer getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Integer houseCount) {
        this.houseCount = houseCount;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
