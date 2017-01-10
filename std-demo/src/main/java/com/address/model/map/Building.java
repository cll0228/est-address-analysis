package com.address.model.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin Yan on 2016/7/19.
 */
public class Building implements Comparable<Building> {

    private Integer id;
    private Integer residenceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    private Integer nameInt;

    private List<Floor> floors = new ArrayList<>();
    private int totalFloor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        try {
        	name = name.replaceAll("号", "");
            nameInt = Integer.valueOf(name);
        } catch (Exception ignore) {
        }
    }

    public Integer getNameInt() {
        return nameInt;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public void fillIn() {
        List<Floor> filled = new ArrayList<>();
        try {
            int maxFloor = 0;
            for (Floor f : floors) {
                if(f.getTotalFloor()>maxFloor){
                    maxFloor = f.getTotalFloor();
                }
                int fi = f.getIntName();
                if (fi > maxFloor) {
                    maxFloor = fi;
                }
            }
            for (int i = 1; i <= maxFloor; i++) {
                boolean exists = false;
                for (Floor f : floors) {
                    int fi = f.getIntName();
                    if (fi == i) {
                        exists = true;
                    }
                }
                if (!exists) {
                    Floor f = new Floor();
                    f.setReal(false);
                    f.setName(String.valueOf(i));
                    filled.add(f);
                }
            }
            this.floors.addAll(filled);
        } catch (Exception ignore) {
        }
    }


    @Override
    public int compareTo(Building o) {
        return Integer.valueOf(this.name).compareTo(Integer.valueOf(o.getName()));
    }

    public void setTotalFloor(int totalFloor) {
        this.totalFloor = totalFloor;
    }

    public int getTotalFloor() {
        return totalFloor;
    }

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }
}
