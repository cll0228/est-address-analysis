package com.address.model;

/**
 * Created by Wangyh on 2017/1/13.
 */
public class PrimarySchool {
	private Integer id;

    private Integer ofResidenceId;

    private String primarySchool;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOfResidenceId() {
		return ofResidenceId;
	}

	public void setOfResidenceId(Integer ofResidenceId) {
		this.ofResidenceId = ofResidenceId;
	}

	public String getPrimarySchool() {
		return primarySchool;
	}

	public void setPrimarySchool(String primarySchool) {
		this.primarySchool = primarySchool;
	}

	@Override
	public String toString() {
		return "ResidenceMetro [id=" + id + ", ofResidenceId=" + ofResidenceId
				+ ", primarySchool=" + primarySchool + "]";
	}
}
