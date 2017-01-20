package com.address.model;

/**
 * Created by Paul on 2016/7/14.
 */
public class LuceneSearchDto {
    private int type;  // 目标类型：1区县、2板块、3网点、4小区
    private int district_id;  // 查询网点、小区、板块时有值，对应的区县id
    private String district;  // 查询网点、小区、板块时有值，对应的区县
    private int block_id;  // 查询网点、小区时有值，对应的板块id
    private String block_name;  // 查询网点、小区时有值，对应的板块id
    private int id;  // 目标ID，查询区县、板块、网点、小区时有值
    private String name;  // 目标名称，查询区县、板块、网点、小区时有值
    private String addr;  // 查询网点、小区时有值
    private double longitude;  // 查询网点、小区时有值
    private double latitude;  // 查询网点、小区时有值
    private double max_longitude;  // 查询区县、板块时有值
    private double max_latitude;  // 查询区县、板块时有值
    private double min_longitude;  // 查询区县、板块时有值
    private double min_latitude;  // 查询区县、板块时有值
    private double center_longitude;  // 查询区县、板块时有值
    private double center_latitude;  // 查询区县、板块时有值


    @Override
    public String toString() {
        return "LuceneSearchDto{" +
                "type=" + type +
                ", district_id=" + district_id +
                ", district='" + district + '\'' +
                ", block_id=" + block_id +
                ", block_name='" + block_name + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", max_longitude=" + max_longitude +
                ", max_latitude=" + max_latitude +
                ", min_longitude=" + min_longitude +
                ", min_latitude=" + min_latitude +
                ", center_longitude=" + center_longitude +
                ", center_latitude=" + center_latitude +
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getBlock_id() {
        return block_id;
    }

    public void setBlock_id(int block_id) {
        this.block_id = block_id;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getMax_longitude() {
        return max_longitude;
    }

    public void setMax_longitude(double max_longitude) {
        this.max_longitude = max_longitude;
    }

    public double getMax_latitude() {
        return max_latitude;
    }

    public void setMax_latitude(double max_latitude) {
        this.max_latitude = max_latitude;
    }

    public double getMin_longitude() {
        return min_longitude;
    }

    public void setMin_longitude(double min_longitude) {
        this.min_longitude = min_longitude;
    }

    public double getMin_latitude() {
        return min_latitude;
    }

    public void setMin_latitude(double min_latitude) {
        this.min_latitude = min_latitude;
    }

    public double getCenter_longitude() {
        return center_longitude;
    }

    public void setCenter_longitude(double center_longitude) {
        this.center_longitude = center_longitude;
    }

    public double getCenter_latitude() {
        return center_latitude;
    }

    public void setCenter_latitude(double center_latitude) {
        this.center_latitude = center_latitude;
    }
}
