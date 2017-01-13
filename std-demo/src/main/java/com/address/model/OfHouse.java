package com.address.model;

/**
 * Created by Cuill on 2016/12/15.
 */
	
public class OfHouse {
    private Integer id;

    private String roomNo;

    private Integer placeFloor;

    private Double area;

    private Integer roomNum;

    private Integer hallNum;

    private Integer planeType;
    
    private Integer propertyType;
    
    private Integer stdAddrId;
    
    private String towards;
    
    private Integer residenceId;
    
    private Integer totalFloor;
    
    private Integer delResId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public Integer getPlaceFloor() {
		return placeFloor;
	}

	public void setPlaceFloor(Integer placeFloor) {
		this.placeFloor = placeFloor;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getHallNum() {
		return hallNum;
	}

	public void setHallNum(Integer hallNum) {
		this.hallNum = hallNum;
	}

	public Integer getPlaneType() {
		return planeType;
	}

	public void setPlaneType(Integer planeType) {
		this.planeType = planeType;
	}

	public Integer getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}

	public Integer getStdAddrId() {
		return stdAddrId;
	}

	public void setStdAddrId(Integer stdAddrId) {
		this.stdAddrId = stdAddrId;
	}

	public String getTowards() {
		return towards;
	}

	public void setTowards(String towards) {
		this.towards = towards;
	}

	public Integer getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}

	public Integer getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(Integer totalFloor) {
		this.totalFloor = totalFloor;
	}

	public Integer getDelResId() {
		return delResId;
	}

	public void setDelResId(Integer delResId) {
		this.delResId = delResId;
	}

	@Override
	public String toString() {
		return "OfHouse [id=" + id + ", roomNo=" + roomNo + ", placeFloor="
				+ placeFloor + ", area=" + area + ", roomNum=" + roomNum
				+ ", hallNum=" + hallNum + ", planeType=" + planeType
				+ ", propertyType=" + propertyType + ", stdAddrId=" + stdAddrId
				+ ", towards=" + towards + ", residenceId=" + residenceId
				+ ", totalFloor=" + totalFloor + ", delResId=" + delResId + "]";
	}
}
