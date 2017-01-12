package com.address.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Colin Yan on 2016/6/27.
 */
public class AssessParam implements Serializable {

    private int residenceId;// 小区ID
    private String buildingNo;// 楼栋号
    private String room;// 室号， 如203
    private double propertyArea;// 面积
    private Double propertyAreaBasement;// 地下室面积
    private Double PropertyGroundArea;//地上面积
    private Integer propertyType;// 房屋类型
    private Integer propertyStorey;// 总层数
    private Integer roomNum;// 房间数，如3 （3室1厅）
    private Integer hallNum;// 客厅数
    @Deprecated
    private String frequency; //频率 month/week

    private Integer toiletNum;// 卫生间数
    private Integer toward;// 朝向
    private Integer planeType;// 平面类型
    private Integer landScape;// 景观状况
    private Integer nearStreet;// 临街状况
    private Integer floor;// 所在层数

    private Integer reserved;   //保留扩展字段，DO NOT use unless you known what you do.
    private Map<?, ?> reservedParams;   //保留扩展字段2

    @Deprecated
    public String getFrequency() {
        return frequency;
    }
    @Deprecated
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getToiletNum() {
        return toiletNum;
    }

    public void setToiletNum(Integer toiletNum) {
        this.toiletNum = toiletNum;
    }

    public Integer getToward() {
        return toward;
    }

    public void setToward(Integer toward) {
        this.toward = toward;
    }

    public Integer getPlaneType() {
        return planeType;
    }

    public void setPlaneType(Integer planeType) {
        this.planeType = planeType;
    }

    public Integer getLandScape() {
        return landScape;
    }

    public void setLandScape(Integer landScape) {
        this.landScape = landScape;
    }

    public Integer getNearStreet() {
        return nearStreet;
    }

    public void setNearStreet(Integer nearStreet) {
        this.nearStreet = nearStreet;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public double getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(double propertyArea) {
        this.propertyArea = propertyArea;
    }

    public Double getPropertyAreaBasement() {
        return propertyAreaBasement;
    }

    public void setPropertyAreaBasement(Double propertyAreaBasement) {
        this.propertyAreaBasement = propertyAreaBasement;
    }

    public Double getPropertyGroundArea() {
        return PropertyGroundArea;
    }

    public void setPropertyGroundArea(Double propertyGroundArea) {
        PropertyGroundArea = propertyGroundArea;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getPropertyStorey() {
        return propertyStorey;
    }

    public void setPropertyStorey(Integer propertyStorey) {
        this.propertyStorey = propertyStorey;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getHallNum() {
        return hallNum;
    }

    public void setHallNum(Integer hallNum) {
        this.hallNum = hallNum;
    }

    public int getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(int residenceId) {
        this.residenceId = residenceId;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "AssessParam{" +
                "residenceId=" + residenceId +
                ", buildingNo='" + buildingNo + '\'' +
                ", room='" + room + '\'' +
                ", propertyArea=" + propertyArea +
                ", propertyAreaBasement=" + propertyAreaBasement +
                ", PropertyGroundArea=" + PropertyGroundArea +
                ", propertyType=" + propertyType +
                ", propertyStorey=" + propertyStorey +
                ", roomNum=" + roomNum +
                ", hallNum=" + hallNum +
                ", toiletNum=" + toiletNum +
                ", toward=" + toward +
                ", planeType=" + planeType +
                ", landScape=" + landScape +
                ", nearStreet=" + nearStreet +
                ", floor=" + floor +
                ", reserved=" + reserved +
                '}';
    }

    public Map<?, ?> getReservedParams() {
        return reservedParams;
    }

    public void setReservedParams(Map<?, ?> reservedParams) {
        this.reservedParams = reservedParams;
    }
}
