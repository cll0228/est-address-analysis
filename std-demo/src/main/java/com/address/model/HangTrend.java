package com.address.model;

import java.util.Date;

public class HangTrend {

    private Integer id;
    
    private Integer ofResidenceId;

    private Date month;

    private Integer hangAvgPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOfResidenceId() {
		return ofResidenceId;
	}

	public void setOfResidenceId(Integer ofResidenceId) {
		this.ofResidenceId = ofResidenceId;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Integer getHangAvgPrice() {
		return hangAvgPrice;
	}

	public void setHangAvgPrice(Integer hangAvgPrice) {
		this.hangAvgPrice = hangAvgPrice;
	}

	@Override
	public String toString() {
		return "HangTrend [id=" + id + ", ofResidenceId=" + ofResidenceId
				+ ", month=" + month + ", hangAvgPrice=" + hangAvgPrice + "]";
	}

}
