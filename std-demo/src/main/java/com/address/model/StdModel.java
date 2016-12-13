package com.address.model;

/**
 * Created by Cuill on 2016/12/12.
 */
public class StdModel {

    private String id;

    private String address;

    private String residence;

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    private String city;

    private String district;

    private String block;

    private String street;// 街道

    private String committe;// 居委

    private String road;// /路

    private String lane;// 弄

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    private String building;// 楼栋

    private String houseNum;// 房屋室号

    private String code;// 标准地址编码

    private String flag;// 状态 1：解析成功 ，2： 区县不对应， 3：解析失败 ,4:未查到资源

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StdModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", residence='").append(residence).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", block='").append(block).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", committe='").append(committe).append('\'');
        sb.append(", road='").append(road).append('\'');
        sb.append(", lane='").append(lane).append('\'');
        sb.append(", building='").append(building).append('\'');
        sb.append(", houseNum='").append(houseNum).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", flag='").append(flag).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public StdModel(String address) {
        this.address = address;
    }

    public StdModel() {

    }
}
