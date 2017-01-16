package com.address.model;

/**
 * Created by Cuill on 2016/12/14.
 */
public class ReturnParam {
    private String id;
    private String district;
    private String street;
    private String committe;
    private String roadLane;
    private String building;
    private String houseNo;
    private String addrCode;
    private String flag; // 状态 1：解析成功 ，2： 区县不对应， 3：地址不存在 ,4:未查到资源 ,5:道路不存在 ,6:房间号未找到，7：楼栋未找到
    //8:参数不合法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommitte() {
        return committe;
    }

    public void setCommitte(String committe) {
        this.committe = committe;
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

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getAddrCode() {
        return addrCode;
    }

    public void setAddrCode(String addrCode) {
        this.addrCode = addrCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
