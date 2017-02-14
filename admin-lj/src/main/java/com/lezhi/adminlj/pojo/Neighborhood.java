package com.lezhi.adminlj.pojo;

/**
 * Created by Wangyh on 2017/2/14.
 */
public class Neighborhood {
    private Double neighborhoodId;
    private String neighborhoodName;
    private String neighborhoodAddr;
    private Double lng;
    private Double lat;
	public Double getNeighborhoodId() {
		return neighborhoodId;
	}
	public void setNeighborhoodId(Double neighborhoodId) {
		this.neighborhoodId = neighborhoodId;
	}
	public String getNeighborhoodName() {
		return neighborhoodName;
	}
	public void setNeighborhoodName(String neighborhoodName) {
		this.neighborhoodName = neighborhoodName;
	}
	public String getNeighborhoodAddr() {
		return neighborhoodAddr;
	}
	public void setNeighborhoodAddr(String neighborhoodAddr) {
		this.neighborhoodAddr = neighborhoodAddr;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "Neighborhood [neighborhoodId=" + neighborhoodId
				+ ", neighborhoodName=" + neighborhoodName
				+ ", neighborhoodAddr=" + neighborhoodAddr + ", lng=" + lng
				+ ", lat=" + lat + "]";
	}
}
