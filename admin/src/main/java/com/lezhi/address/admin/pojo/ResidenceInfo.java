package com.lezhi.address.admin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by chendl on 2017/01/10.
 */
public class ResidenceInfo {

    private Integer residenceId;

    private String residenceName;

    private Double lon;

    private Double lat;

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

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
