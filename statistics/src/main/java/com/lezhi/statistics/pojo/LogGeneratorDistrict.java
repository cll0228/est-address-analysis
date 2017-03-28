package com.lezhi.statistics.pojo;

import java.util.Date;

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
	private Date beginTime;
	private Date endTime;
	private Long totalTop;
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getTotalTop() {
		return totalTop;
	}
	public void setTotalTop(Long totalTop) {
		this.totalTop = totalTop;
	}
	@Override
	public String toString() {
		return "LogGeneratorDistrict [id=" + id + ", districtId=" + districtId
				+ ", channelNo=" + channelNo + ", mac=" + mac + ", session="
				+ session + ", pv=" + pv + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", totalTop=" + totalTop + "]";
	}
}