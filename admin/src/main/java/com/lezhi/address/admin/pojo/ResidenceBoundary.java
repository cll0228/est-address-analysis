package com.lezhi.address.admin.pojo;

/**
 * Created by chendl on 2017/1/18.
 */
public class ResidenceBoundary {
    private Integer id;
    private String baiduLon;
    private String baiduLat;
    private Integer orderId;
    private Integer iwjwResidenceId;
    private Integer ofResidenceId;
    private String resiLon;

    private String resiLat;

    public String getResiLon() {
        return resiLon;
    }

    public void setResiLon(String resiLon) {
        this.resiLon = resiLon;
    }

    public String getResiLat() {
        return resiLat;
    }

    public void setResiLat(String resiLat) {
        this.resiLat = resiLat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaiduLon() {
        return baiduLon;
    }

    public void setBaiduLon(String baiduLon) {
        this.baiduLon = baiduLon;
    }

    public String getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(String baiduLat) {
        this.baiduLat = baiduLat;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIwjwResidenceId() {
        return iwjwResidenceId;
    }

    public void setIwjwResidenceId(Integer iwjwResidenceId) {
        this.iwjwResidenceId = iwjwResidenceId;
    }

    public Integer getOfResidenceId() {
        return ofResidenceId;
    }

    public void setOfResidenceId(Integer ofResidenceId) {
        this.ofResidenceId = ofResidenceId;
    }
}