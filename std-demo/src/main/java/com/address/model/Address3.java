package com.address.model;

/**
 * Created by Colin Yan on 2016/7/18.
 */
public class Address3 extends AddressModel {
    // XXX(村)XXX(组、队)XXX号
    private String village;
    private String group;
    private String no;

    private Integer score;

    @Override
    public Integer getScore() {
        /*
        if (score == null) {
            if (village.matches("^[\\u4E00-\\u9FA5]+村$") && group.matches("^\\d+[组队]$") && no.matches("^\\d+$")) {
                score = 99;
            } else {
                score = 30;
            }
        }
        */
        return score;
    }

    @Override
    public void setScore(Integer score) {
        this.score = score;
    }

    public Address3(String village, String group, String no) {
        this.village = village;
        this.group = group;
        this.no = no;
    }

    @Override
    public String toString() {
        return village + "<村>" + group + "<组,队>" + no + "<号>";
    }

    @Override
    public String binTab() {
        return village + "\t" + group + "\t" + no;
    }

    @Override
    public String getResidence() {
        return village;
    }

    @Override
    public String getBuilding() {
        return group;
    }

    @Override
    public String getRoom() {
        return no;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
