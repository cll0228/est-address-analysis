package com.lezhi.statistics.pojo;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangyh on 2017/3/30.
 */
public class ChannelVisitSummary {
	private Integer id;

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	private Integer districtId;
	private Integer residenceId;
	private Integer blockId;
	private String channelNo;
	private String mac;
	private String session;
	private Integer pv;
	private Date beginTime;
	private Date endTime;
	private Long totalTop;
	private Long begin;
	private Long end;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getResidenceId() {
		return residenceId;
	}
	public void setResidenceId(Integer residenceId) {
		this.residenceId = residenceId;
	}
	public Integer getBlockId() {
		return blockId;
	}
	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
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
		return "LogGeneratorBlock [id=" + id + ", residenceId=" + residenceId
				+ ", blockId=" + blockId + ", channelNo=" + channelNo
				+ ", mac=" + mac + ", session=" + session + ", pv=" + pv
				+ ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", totalTop=" + totalTop + ", begin=" + begin + ", end="
				+ end + "]";
	}

	public String getBeginTimeUTC_8() {
		if (beginTime == null)
			return null;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginTime);
	}
	public String getEndTimeUTC_8() {
		if (endTime == null)
			return null;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime);
	}
}