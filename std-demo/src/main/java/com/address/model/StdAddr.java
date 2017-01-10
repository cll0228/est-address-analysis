package com.address.model;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class StdAddr {

    private String residence;
    private String building;
    private String room;

    private Integer residenceId;
    private Double area;
    private Integer roomId;

    private Integer status;
    private Integer buildingId;
    private Integer totalFloor;
    private String src;
    private String oriAddress;

    public String getOriAddress() {
		return oriAddress;
	}

	public void setOriAddress(String oriAddress) {
		this.oriAddress = oriAddress;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public StdAddr() {
    }

    public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public StdAddr(String residence, String building, String room) {
        this.residence = residence;
        this.building = building;
        this.room = room;
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

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }
}
