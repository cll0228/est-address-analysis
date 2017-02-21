package com.lezhi.adminlj.pojo;

/**
 * Created by Wangyh on 2017/2/21.
 */
public class CountParam {
    private Integer households;//机顶盒总户数
    private String levelName;//层级名（包括区县，街道，居委）
    private Integer levelId;//层级id
    private Integer levelHouseCount;//层级总户数
    private Double proportion;//占比
	public Integer getHouseholds() {
		return households;
	}
	public void setHouseholds(Integer households) {
		this.households = households;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getLevelHouseCount() {
		return levelHouseCount;
	}
	public void setLevelHouseCount(Integer levelHouseCount) {
		this.levelHouseCount = levelHouseCount;
	}
	public Double getProportion() {
		return proportion;
	}
	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}
	@Override
	public String toString() {
		return "CountParam [households=" + households + ", levelName="
				+ levelName + ", levelId=" + levelId + ", levelHouseCount="
				+ levelHouseCount + ", proportion=" + proportion + "]";
	}
}
