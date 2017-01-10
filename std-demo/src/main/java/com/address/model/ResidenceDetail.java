package com.address.model;

import java.util.Date;

/**
 * Created by Wangyh on 2017/1/10.
 */
public class ResidenceDetail {
    private Integer id;

    private String residenceName;

    private String aliases;

    private String residenceAddr;

    private String propertyType;

    private Double vp;

    private Double gp;
    
    private Integer buildingCount;
    
    private Integer houseCount;
    
    private String houseType;
    
    private Date accomplishDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResidenceName() {
		return residenceName;
	}

	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getResidenceAddr() {
		return residenceAddr;
	}

	public void setResidenceAddr(String residenceAddr) {
		this.residenceAddr = residenceAddr;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Double getVp() {
		return vp;
	}

	public void setVp(Double vp) {
		this.vp = vp;
	}

	public Double getGp() {
		return gp;
	}

	public void setGp(Double gp) {
		this.gp = gp;
	}

	public Integer getBuildingCount() {
		return buildingCount;
	}

	public void setBuildingCount(Integer buildingCount) {
		this.buildingCount = buildingCount;
	}

	public Integer getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(Integer houseCount) {
		this.houseCount = houseCount;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public Date getAccomplishDate() {
		return accomplishDate;
	}

	public void setAccomplishDate(Date accomplishDate) {
		this.accomplishDate = accomplishDate;
	}

	@Override
	public String toString() {
		return "ResidenceDetail [id=" + id + ", residenceName=" + residenceName
				+ ", aliases=" + aliases + ", residenceAddr=" + residenceAddr
				+ ", propertyType=" + propertyType + ", vp=" + vp + ", gp="
				+ gp + ", buildingCount=" + buildingCount + ", houseCount="
				+ houseCount + ", houseType=" + houseType + ", accomplishDate="
				+ accomplishDate + "]";
	}

}
