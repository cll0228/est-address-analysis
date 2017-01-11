package com.address.model;

/**
 * Created by chendl on 2017/1/10.
 */
public class PoiDetail {
    private String poiKind;

    private String poiName;

    private Double distance;

    private String poiAddr;

    private Double baiduLon;

    private Double baiduLat;

    public String getPoiKind() {
        return poiKind;
    }

    public void setPoiKind(String poiKind) {
        this.poiKind = poiKind;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPoiAddress() {
        return poiAddr;
    }

    public void setPoiAddress(String poiAddress) {
        this.poiAddr = poiAddress;
    }

    public Double getBaiduLon() {
        return baiduLon;
    }

    public void setBaiduLon(Double baiduLon) {
        this.baiduLon = baiduLon;
    }

    public Double getBaiduLat() {
        return baiduLat;
    }

    public void setBaiduLat(Double baiduLat) {
        this.baiduLat = baiduLat;
    }
}