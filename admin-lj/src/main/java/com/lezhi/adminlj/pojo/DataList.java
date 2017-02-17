package com.lezhi.adminlj.pojo;


/**
 * Created by Wangyh on 2017/2/14.
 */
public class DataList {
    private String dataId;
    private Double latitude;
    private Double longitude;
    private String showName;
    private Integer households;
    private Integer proportion;
    private String type;
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public Integer getHouseholds() {
		return households;
	}
	public void setHouseholds(Integer households) {
		this.households = households;
	}
	public Integer getProportion() {
		return proportion;
	}
	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "DataList [dataId=" + dataId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", showName=" + showName
				+ ", households=" + households + ", proportion=" + proportion
				+ ", type=" + type + "]";
	}
}