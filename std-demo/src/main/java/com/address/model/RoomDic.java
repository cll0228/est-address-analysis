package com.address.model;

import java.util.Date;

/**
 * Created by Colin Yan on 2016/7/20.
 */
public class RoomDic {

    private Integer id;
    private String name;
    private Integer buildingId;
    private	 Double area;
    private Integer delStatus;
    private Integer operatorId;
    private Date modifyTime;

    private Integer residenceId;
    private String buildingName;
    private String oriAddress;
    private Long refId;
    private Long urefId;
    private String src;

    private Boolean isDeal;
    
    private String placeFloor;

    private Integer floorInt;
    private Integer roomIndex; // 304 -> 4

    public Boolean getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Boolean isDeal) {
        this.isDeal = isDeal;
    }

    public Long getUrefId() {
        return urefId;
    }

    public void setUrefId(Long urefId) {
        this.urefId = urefId;
    }

    public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;

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

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getOriAddress() {
        return oriAddress;
    }

    public void setOriAddress(String oriAddress) {
        this.oriAddress = oriAddress;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

	public String getPlaceFloor() {
		return placeFloor;
	}

	public void setPlaceFloor(String placeFloor) {
		this.placeFloor = placeFloor;
	}


    public Integer getFloorInt() {
        return floorInt;
    }

    public void setFloorInt(Integer floorInt) {
        this.floorInt = floorInt;
    }

    public Integer getRoomIndex() {
        return roomIndex;
    }

    public void setRoomIndex(Integer roomIndex) {
        this.roomIndex = roomIndex;
    }
}
