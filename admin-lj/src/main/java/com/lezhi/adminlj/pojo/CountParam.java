package com.lezhi.adminlj.pojo;

/**
 * Created by Wangyh on 2017/2/21.
 */
public class CountParam {
    private Integer households;//机顶盒总户数
    private String levelName;//层级名（包括区县，街道，居委）
    private String levelId;//层级id
    private Integer levelHouseCount;//层级总户数
    private Double proportion;//占比
	private Double lat;
	private Double lon;
	private String districtName;
	private String townNname;
	private String neighborhoodName;
	public Integer getHouseholds() {
		return households;
	}
	public void setHouseholds(Integer households) {
		this.households = households;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public Integer getLevelHouseCount() {
		return levelHouseCount;
	}
	public void setLevelHouseCount(Integer levelHouseCount) {
		this.levelHouseCount = levelHouseCount;
	}
	public Double getProportion() {
		return proportion;
	}
	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
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
		return "CountParam [households=" + households + ", levelName="
				+ levelName + ", levelId=" + levelId + ", levelHouseCount="
				+ levelHouseCount + ", proportion=" + proportion + ", lat="
				+ lat + ", lon=" + lon + ", districtName=" + districtName
				+ ", townNname=" + townNname + ", neighborhoodName="
				+ neighborhoodName + "]";
	}
}
