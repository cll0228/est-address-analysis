package com.lezhi.address.admin.pojo;

import java.util.Date;

public class UpdateTable {
	private Integer id;
	private String parsedRoadLane;
    private Integer parsedBuildingNo;
    private String parsedHouseNo;
    private Integer parsedVersion;
    private Date parsedTime;
    private Integer parsedStatus;
    private String targetTablePk;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParsedRoadLane() {
		return parsedRoadLane;
	}
	public void setParsedRoadLane(String parsedRoadLane) {
		this.parsedRoadLane = parsedRoadLane;
	}
	public Integer getParsedBuildingNo() {
		return parsedBuildingNo;
	}
	public void setParsedBuildingNo(Integer parsedBuildingNo) {
		this.parsedBuildingNo = parsedBuildingNo;
	}
	public String getParsedHouseNo() {
		return parsedHouseNo;
	}
	public void setParsedHouseNo(String parsedHouseNo) {
		this.parsedHouseNo = parsedHouseNo;
	}
	public Integer getParsedVersion() {
		return parsedVersion;
	}
	public void setParsedVersion(Integer parsedVersion) {
		this.parsedVersion = parsedVersion;
	}
	public Date getParsedTime() {
		return parsedTime;
	}
	public void setParsedTime(Date parsedTime) {
		this.parsedTime = parsedTime;
	}
	public Integer getParsedStatus() {
		return parsedStatus;
	}
	public void setParsedStatus(Integer parsedStatus) {
		this.parsedStatus = parsedStatus;
	}
	public String getTargetTablePk() {
		return targetTablePk;
	}
	public void setTargetTablePk(String targetTablePk) {
		this.targetTablePk = targetTablePk;
	}
	@Override
	public String toString() {
		return "UpdateTable [id=" + id + ", parsedRoadLane=" + parsedRoadLane
				+ ", parsedBuildingNo=" + parsedBuildingNo + ", parsedHouseNo="
				+ parsedHouseNo + ", parsedVersion=" + parsedVersion
				+ ", parsedTime=" + parsedTime + ", parsedStatus="
				+ parsedStatus + ", targetTablePk=" + targetTablePk + "]";
	}
}
