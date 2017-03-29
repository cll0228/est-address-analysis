package com.lezhi.statistics.pojo;


/**
 * Created by wangyh on 2017/3/28.
 */
public class LogGeneratorDistrict {
	private Integer id;
	private Integer districtId;
	private String channelNo;
	private String mac;
	private String session;
	private Integer pv;
	private String beginTime;
	private String endTime;
	private Long totalTop;
	private Long begin;
	private Long end;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getTotalTop() {
		return totalTop;
	}
	public void setTotalTop(Long totalTop) {
		this.totalTop = totalTop;
	}
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "LogGeneratorDistrict [id=" + id + ", districtId=" + districtId
				+ ", channelNo=" + channelNo + ", mac=" + mac + ", session="
				+ session + ", pv=" + pv + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", totalTop=" + totalTop
				+ ", begin=" + begin + ", end=" + end + "]";
	}
}