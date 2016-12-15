package com.address.model;

/**
 * Created by Cuill on 2016/12/15.
 */
public class HouseDeal {
    private Integer id;

    private String address;

    private String residence;

    private String building;

    private String room;

    private String analyAddr;

    private String noMappingType;

    public String getAnalyAddr() {
        return analyAddr;
    }

    public void setAnalyAddr(String analyAddr) {
        this.analyAddr = analyAddr;
    }

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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNoMappingType() {
        return noMappingType;
    }

    public void setNoMappingType(String noMappingType) {
        this.noMappingType = noMappingType;
    }
}
