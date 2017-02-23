package com.lezhi.adminlj.pojo;

import java.util.ArrayList;

/**
 * Created by Wangyh on 2017/2/14.
 */
public class Town extends Num{
    private Integer townId;
    private String townName;
    private String longitude;
    private String latitude;
	private ArrayList<Neighborhood> neighborhoodList;
	public Integer getTownId() {
		return townId;
	}
	public void setTownId(Integer townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public ArrayList<Neighborhood> getNeighborhoodList() {
		return neighborhoodList;
	}
	public void setNeighborhoodList(ArrayList<Neighborhood> neighborhoodList) {
		this.neighborhoodList = neighborhoodList;
	}
	@Override
	public String toString() {
		return "Town [townId=" + townId + ", townName=" + townName
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", neighborhoodList=" + neighborhoodList + "]";
	}
}
