package com.address.model.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class Residence implements Comparable<Residence> {

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    private Integer residenceId;
    private String name;

    private Integer minBuilding;

    public List<BuildingBitMap> getBuildingBitMaps() {
        return buildingBitMaps;
    }

    public void setBuildingBitMaps(List<BuildingBitMap> buildingBitMaps) {
        this.buildingBitMaps = buildingBitMaps;
    }

    private List<BuildingBitMap> buildingBitMaps = new ArrayList<>();

    public Integer getMinBuilding() {
        return minBuilding;
    }

    public void setMinBuilding(Integer minBuilding) {
        this.minBuilding = minBuilding;
    }

    private List<Building> buildings = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    @Override
    public int compareTo(Residence o) {
        return name.compareTo(o.getName());
    }
}
