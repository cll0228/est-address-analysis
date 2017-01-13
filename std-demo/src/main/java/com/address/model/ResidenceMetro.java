package com.address.model;

/**
 * Created by Wangyh on 2017/1/13.
 */
public class ResidenceMetro {
    private Integer id;

    private Integer ofResidenceId;

    private String metroStationName;

    private String metroLineName;

    private Integer metroDistance;

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

	public String getMetroStationName() {
		return metroStationName;
	}

	public void setMetroStationName(String metroStationName) {
		this.metroStationName = metroStationName;
	}

	public String getMetroLineName() {
		return metroLineName;
	}

	public void setMetroLineName(String metroLineName) {
		this.metroLineName = metroLineName;
	}

	public Integer getMetroDistance() {
		return metroDistance;
	}

	public void setMetroDistance(Integer metroDistance) {
		this.metroDistance = metroDistance;
	}

	@Override
	public String toString() {
		return "ResidenceMetro [id=" + id + ", ofResidenceId=" + ofResidenceId
				+ ", metroStationName=" + metroStationName + ", metroLineName="
				+ metroLineName + ", metroDistance=" + metroDistance + "]";
	}

}
