package com.address.model;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Cuill on 2016/5/11.
 */
public class LianjiaResidenceInfo {
    private Long residenceId;

    private String residenceName;

    private String address;

    private String district;

    private String block;

    private String avagePrice;

    private String propertyType;

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    private String houseQuotationPrice;

    public String getHouseQuotationPrice() {
        return houseQuotationPrice;
    }

    public void setHouseQuotationPrice(String houseQuotationPrice) {
        this.houseQuotationPrice = houseQuotationPrice;
    }

    private String accomplishDate;

    private Integer buildingNum;

    private Integer totalHouse;

    private String volumeRate;// 容积率

    private String greeningRate;// 绿化率

    private String longitude;// 经度

    private String latitude;// 纬度

    private String residenceUrl;

    private String subWayDesc;

    private Date createTime;

    private Date modifyTime;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        LianjiaResidenceInfo that = (LianjiaResidenceInfo) o;

        return new EqualsBuilder().append(residenceId, that.residenceId)
                .append(residenceName, that.residenceName).append(address, that.address)
                .append(district, that.district).append(block, that.block).append(avagePrice, that.avagePrice)
                .append(accomplishDate, that.accomplishDate).append(buildingNum, that.buildingNum)
                .append(totalHouse, that.totalHouse).append(volumeRate, that.volumeRate)
                .append(greeningRate, that.greeningRate).append(longitude, that.longitude)
                .append(latitude, that.latitude).append(residenceUrl, that.residenceUrl)
                .append(subWayDesc, that.subWayDesc).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(residenceId).append(residenceName).append(address)
                .append(district).append(block).append(avagePrice).append(accomplishDate).append(buildingNum)
                .append(totalHouse).append(volumeRate).append(greeningRate).append(longitude).append(latitude)
                .append(residenceUrl).append(subWayDesc).toHashCode();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSubWayDesc() {
        return subWayDesc;
    }

    public void setSubWayDesc(String subWayDesc) {
        this.subWayDesc = subWayDesc;
    }

    public Long getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Long residenceId) {
        this.residenceId = residenceId;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getAvagePrice() {
        return avagePrice;
    }

    public void setAvagePrice(String avagePrice) {
        this.avagePrice = avagePrice;
    }

    public String getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(String accomplishDate) {
        this.accomplishDate = accomplishDate;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
    }

    public Integer getTotalHouse() {
        return totalHouse;
    }

    public void setTotalHouse(Integer totalHouse) {
        this.totalHouse = totalHouse;
    }

    public String getVolumeRate() {
        return volumeRate;
    }

    public void setVolumeRate(String volumeRate) {
        this.volumeRate = volumeRate;
    }

    public String getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        this.greeningRate = greeningRate;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getResidenceUrl() {
        return residenceUrl;
    }

    public void setResidenceUrl(String residenceUrl) {
        this.residenceUrl = residenceUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("residenceId", residenceId)
                .append("residenceName", residenceName).append("address", address)
                .append("district", district).append("block", block).append("avagePrice", avagePrice)
                .append("accomplishDate", accomplishDate).append("buildingNum", buildingNum)
                .append("totalHouse", totalHouse).append("volumeRate", volumeRate)
                .append("greeningRate", greeningRate).append("longitude", longitude)
                .append("latitude", latitude).append("residenceUrl", residenceUrl).toString();
    }
}
