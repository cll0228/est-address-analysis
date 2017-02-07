package com.lezhi.address.admin.pojo;

import java.util.Date;

/**
 * Created by Cuill on 2017/1/23.
 */
public class Address {
    private Integer id;
    private String address;
    private String roadLane;
    private String buildingNo;
    private String houseNo;
    private String pareseVersion;
    private String parsedTime;
    private Integer status;
    private Integer matchStatus;
    private Date matchTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoadLane() {
        return roadLane;
    }

    public void setRoadLane(String roadLane) {
        this.roadLane = roadLane;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getPareseVersion() {
        return pareseVersion;
    }

    public void setPareseVersion(String pareseVersion) {
        this.pareseVersion = pareseVersion;
    }

    public String getParsedTime() {
        return parsedTime;
    }

    public void setParsedTime(String parsedTime) {
        this.parsedTime = parsedTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }
}
