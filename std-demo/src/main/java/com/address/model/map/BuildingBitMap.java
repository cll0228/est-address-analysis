package com.address.model.map;

/**
 * Created by Colin Yan on 2016/7/22.
 */
public class BuildingBitMap {

    private boolean exists;

    private String buildingName;

    private Integer buildingNameInt;

    public Integer getBuildingNameInt() {
        return buildingNameInt;
    }

    public void setBuildingNameInt(Integer buildingNameInt) {
        this.buildingNameInt = buildingNameInt;
    }

    private Integer buildingId;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }
}
