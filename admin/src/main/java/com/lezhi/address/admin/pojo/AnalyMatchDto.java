package com.lezhi.address.admin.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.crypto.Data;

/**
 * Created by Cuill on 2017/2/7.
 */
public class AnalyMatchDto {
    private Integer id;// 任務id

    private String name;// 解析任务名称

    private Integer addressId;

    private Integer analTaskId;

    private String dataName;

    private String tableName;

    private String address;

    private Integer ifAnalySis;// 是否解析成功

    private String parsedTime;

    private String roadLane;

    private String building;

    private String house;

    private Integer ifMatch;// 是否

    private String matchTime;

    public String getParsedTime() {
        return parsedTime;
    }

    public void setParsedTime(String parsedTime) {
        this.parsedTime = parsedTime;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    private Integer totalPage;// 地址总条数
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getAnalTaskId() {
        return analTaskId;
    }

    public void setAnalTaskId(Integer analTaskId) {
        this.analTaskId = analTaskId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIfAnalySis() {

        return ifAnalySis;
    }

    public void setIfAnalySis(Integer ifAnalySis) {
        this.ifAnalySis = ifAnalySis;
    }




    public String getRoadLane() {
        return roadLane;
    }

    public void setRoadLane(String roadLane) {
        this.roadLane = roadLane;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public Integer getIfMatch() {
        return ifMatch;
    }

    public void setIfMatch(Integer ifMatch) {
        this.ifMatch = ifMatch;
    }

}
