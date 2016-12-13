package com.address.model;

/**
 * Created by Colin Yan on 2016/7/18.
 */
public abstract class AddressModel {

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    private boolean filtered;

    public abstract String binTab();

    public abstract String getResidence();

    public abstract String getBuilding();

    public abstract String getRoom();

    public abstract Integer getScore();

    public abstract void setScore(Integer score);
}
