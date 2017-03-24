package com.lezhi.statistics.pojo;

/**
 * Created by chendl on 2017/3/24.
 */
public class MacVisitLogInfo {
	private String mac;// 机顶盒mac地址
	private Integer districtId;// 区县ID
	private String districtName;// 区县
	private Integer blockId;// 板块ID
	private String blockName;// 板块
	private Integer residenceId;// 小区ID
	private String residenceName;// 小区名称
	private Long time;// 日志时间。 unix time, 单位ms

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

	@Override
	public String toString() {
		return "MacInfoObj [mac=" + mac + ",districtId=" + districtId + ",districtName=" + districtName
				+", blockId=" + blockId + ",  blockName=" + blockName + ", residenceId="+ residenceId
				+", residenceName=" + residenceName +", time="+ time +"]";
	}
}