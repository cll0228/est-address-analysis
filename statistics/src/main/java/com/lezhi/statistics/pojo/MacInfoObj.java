package com.lezhi.statistics.pojo;

/**
 * Created by wangyh on 2017/3/13.
 */
public class MacInfoObj {
	private Integer id;// 主键ID
	private String mac;// 机顶盒mac地址
	private Integer residenceId;// 机顶盒所在小区ID
	private String residenceName;// 机顶盒所在小区名称
	private Integer buildingId;// 机顶盒所在楼栋ID
	private String buildingNo;// 机顶盒所在楼栋号
	private Double lat;// 坐标latitude
	private Double lon;// 坐标longitude
	private String district;// 区县
	private Integer districtId;// 区县ID
	private String block;// 板块
	private Integer blockId;// 板块ID
	private String serialNo;// 机顶盒编号
	private String resCode;// 机顶盒型号
	private String resName;// 机顶盒名称
	private String resCategory;// 机顶盒类别

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

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResCategory() {
		return resCategory;
	}

	public void setResCategory(String resCategory) {
		this.resCategory = resCategory;
	}

	@Override
	public String toString() {
		return "MacInfoObj [id=" + id + ", mac=" + mac + ", residenceId="
				+ residenceId + ", residenceName=" + residenceName
				+ ", buildingId=" + buildingId + ", buildingNo=" + buildingNo
				+ ", lat=" + lat + ", lon=" + lon + ", district=" + district
				+ ", districtId=" + districtId + ", block=" + block
				+ ", blockId=" + blockId + ", serialNo=" + serialNo
				+ ", resCode=" + resCode + ", resName=" + resName
				+ ", resCategory=" + resCategory + "]";
	}
}
