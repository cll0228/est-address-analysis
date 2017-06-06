package com.lezhi.statistics.pojo;

/**
 * Created by chendl on 2017/3/24.
 */
public class MacVisitLogInfo {
	private Integer id;
	private String mac;// 机顶盒mac地址
	private String channelNo;// 频道号
	private Integer districtId;// 区县ID
	private String districtName;// 区县
	private Integer blockId;// 板块ID
	private String blockName;// 板块
	private Integer residenceId;// 小区ID
	private String residenceName;// 小区名称
	private Long time;// 日志时间。 unix time, 单位ms

	// null表示新用户，非null表示老用户；查询的时候如果指定了频道，就是相对该频道，否则相对全局
	private Integer firstVisitIdIfEverVisited;

	public Integer getFirstVisitIdIfEverVisited() {
		return firstVisitIdIfEverVisited;
	}

	public void setFirstVisitIdIfEverVisited(Integer firstVisitIdIfEverVisited) {
		this.firstVisitIdIfEverVisited = firstVisitIdIfEverVisited;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

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

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	@Override
	public String toString() {
		return "MacInfoObj [mac=" + mac + ",channelNo=" + channelNo + ",districtId=" + districtId + ","
				+ "districtName=" + districtName+", blockId=" + blockId + ",  blockName=" + blockName
				+ ", residenceId="+ residenceId
				+", residenceName=" + residenceName +", time="+ time +"]";
	}
}
