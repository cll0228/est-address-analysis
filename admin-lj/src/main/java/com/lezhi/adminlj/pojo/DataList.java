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
    private Double proportion;
    private String type;
    private String div;
    private String gdataId;
    private String pShowName;
    private String cityName;
    private String districtName;
    private String townNname;
    private String neighborhoodName;
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
	public Double getProportion() {
		return proportion;
	}
	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	public String getGdataId() {
		return gdataId;
	}
	public void setGdataId(String gdataId) {
		this.gdataId = gdataId;
	}
	public String getpShowName() {
		return pShowName;
	}
	public void setpShowName(String pShowName) {
		this.pShowName = pShowName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getTownNname() {
		return townNname;
	}
	public void setTownNname(String townNname) {
		this.townNname = townNname;
	}
	public String getNeighborhoodName() {
		return neighborhoodName;
	}
	public void setNeighborhoodName(String neighborhoodName) {
		this.neighborhoodName = neighborhoodName;
	}
	@Override
	public String toString() {
		return "DataList [dataId=" + dataId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", showName=" + showName
				+ ", households=" + households + ", proportion=" + proportion
				+ ", type=" + type + ", div=" + div + ", gdataId=" + gdataId
				+ ", pShowName=" + pShowName + ", cityName=" + cityName
				+ ", districtName=" + districtName + ", townNname=" + townNname
				+ ", neighborhoodName=" + neighborhoodName + "]";
	}
}
