package com.lezhi.adminlj.pojo;

/**
 * Created by chendl on 2017/2/16.
 */
public class LuceneSearchDto {
    private int type;  // 目标类型：1区县、2街道、3居委、4道路、5小区
    private int district_id;  // 查询街道、小区、居委时有值，对应的区县id
    private String district_name;  // 查询街道、小区、居委时有值，对应的区县
    private int town_id;  // 查询小区、居委时有值，对应的街道id
    private String town_name;  // 查询小区、居委时有值，对应的街道
    private int neighborhood_id;  // 查询小区时有值，对应的居委id
    private String neighborhood_name;  // 查询小区时有值，对应的居委
    private String id;  // 目标ID，查询区县、街道、小区、居委、道路时有值
    private String name;  // 目标名称，查询区县、街道、小区、居委、道路时有值
    private String addr;  // 查询居委、小区时有值
    private double longitude;  // 查询居委、小区时有值
    private double latitude;  // 查询居委、小区时有值
    private double center_lng;  // 查询区县、板块时有值
    private double center_lat;  // 查询区县、板块时有值


    @Override
    public String toString() {
        return "LuceneSearchDto{" +
                "type=" + type +
                ", district_id=" + district_id +
                ", district_name='" + district_name + '\'' +
                ", town_id=" + town_id +
                ", town_name='" + town_name + '\'' +
                ", neighborhood_id=" + neighborhood_id +
                ", neighborhood_name='" + neighborhood_name + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", center_lng=" + center_lng +
                ", center_lat=" + center_lat +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public int getNeighborhood_id() {
        return neighborhood_id;
    }

    public void setNeighborhood_id(int neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }

    public String getNeighborhood_name() {
        return neighborhood_name;
    }

    public void setNeighborhood_name(String neighborhood_name) {
        this.neighborhood_name = neighborhood_name;
    }

    public double getCenter_lng() {
        return center_lng;
    }

    public void setCenter_lng(double center_lng) {
        this.center_lng = center_lng;
    }

    public double getCenter_lat() {
        return center_lat;
    }

    public void setCenter_lat(double center_lat) {
        this.center_lat = center_lat;
    }
}
