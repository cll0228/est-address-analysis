package com.address.model;

/**
 * Created by Colin Yan on 2016/7/18.
 */
public class Address1 extends AddressModel {

    // XXX路XXX弄XXX号XXX室
    private String road;
    private String lane;
    private String building;
    private String room;

    private Integer score;

    @Override
    public Integer getScore() {
        /*
        if (score == null) {
            if (road.matches("^[\\u4E00-\\u9FA5]+$") && lane.matches("^\\d+$") && building.matches("^\\d+$") && room.matches("^\\d{3,4}$")) {
                score = 99;
            } else {
                score = 30;
            }
        }*/
        return score;
    }

    @Override
    public void setScore(Integer score) {
        this.score = score;
    }

    public Address1(String road, String lane, String building, String room) {
        this.road = road;
        this.lane = lane;
        this.building = building;
        this.room = room;
    }

    @Override
    public String toString() {
        return road + "<路>" + lane + "<弄>" + building + "<号>" + room + "<室>";
    }

    @Override
    public String binTab() {
        return road + "路" + lane + "弄\t" + building + "\t"+room;
    }

    @Override
    public String getResidence() {
        return road + "路" + lane + "弄";
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

}
