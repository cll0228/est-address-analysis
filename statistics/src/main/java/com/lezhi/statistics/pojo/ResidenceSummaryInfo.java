package com.lezhi.statistics.pojo;

/**
 * Created by wangyh on 2017/3/22.
 */
public class ResidenceSummaryInfo {
	private Integer residenceId;// 小区ID
	private String residenceName;// 小区名称
	private Double lat;// 坐标latitude
	private Double lon;// 坐标longitude
	private Integer uv;// 时间段内频道独立访客数（unique visitor）
	private Integer pv;// 时间段内频道访问量（page view）
	private Integer nv;// 时间段内频道第一次访问东方电视云平台频道内容的独立用户数（new visitor）
	private Long avgTop;// 单位为秒。东方电视云平台频道中每次会话的平均停留时长（average time on page）。注：时间跨度大于1小时的情况下有此值。
	
	public Integer getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}
	public String getResidenceName() {
		return residenceName;
	}
	public void setResidenceName(String residenceName) {
		this.residenceName = residenceName;
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
	public Integer getUv() {
		return uv;
	}
	public void setUv(Integer uv) {
		this.uv = uv;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public Integer getNv() {
		return nv;
	}
	public void setNv(Integer nv) {
		this.nv = nv;
	}
	public Long getAvgTop() {
		return avgTop;
	}
	public void setAvgTop(Long avgTop) {
		this.avgTop = avgTop;
	}
	@Override
	public String toString() {
		return "ResidenceSummaryInfo [residenceId=" + residenceId
				+ ", residenceName=" + residenceName + ", lat=" + lat
				+ ", lon=" + lon + ", uv=" + uv + ", pv=" + pv + ", nv=" + nv
				+ ", avgTop=" + avgTop + "]";
	}
}