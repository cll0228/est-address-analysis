package com.lezhi.adminlj.pojo;

import java.util.ArrayList;

/**
 * Created by Wangyh on 2017/2/14.
 */
public class District {
    private Integer districtId;
    private String districtName;
    private Double centerLng;
    private Double centerLat;
    private ArrayList<Town> townList;
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Double getCenterLng() {
		return centerLng;
	}
	public void setCenterLng(Double centerLng) {
		this.centerLng = centerLng;
	}
	public Double getCenterLat() {
		return centerLat;
	}
	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}
	public ArrayList<Town> getTownList() {
		return townList;
	}
	public void setTownList(ArrayList<Town> townList) {
		this.townList = townList;
	}
	@Override
	public String toString() {
		return "District [districtId=" + districtId + ", districtName="
				+ districtName + ", centerLng=" + centerLng + ", centerLat="
				+ centerLat + ", townList=" + townList + "]";
	}
}
