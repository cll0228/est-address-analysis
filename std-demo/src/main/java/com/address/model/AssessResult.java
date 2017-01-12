package com.address.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin Yan on 2016/6/30.
 */
public class AssessResult implements Serializable {

    private Integer unitPrice;
    private Double totalPrice;
    private String errorMsg;

    private Integer hangUnitPrice;
    private Double hangTotalPrice;

    private Integer dealUnitPrice;
    private Double dealTotalPrice;

    public Integer getDealUnitPrice() {
        return dealUnitPrice;
    }

    public void setDealUnitPrice(Integer dealUnitPrice) {
        this.dealUnitPrice = dealUnitPrice;
    }

    public Double getDealTotalPrice() {
        return dealTotalPrice;
    }

    public void setDealTotalPrice(Double dealTotalPrice) {
        this.dealTotalPrice = dealTotalPrice;
    }

    private List<String> hangIds = new ArrayList<>();

    private List<String> dealIds = new ArrayList<>();

    public Integer getHangUnitPrice() {
        return hangUnitPrice;
    }

    public void setHangUnitPrice(Integer hangUnitPrice) {
        this.hangUnitPrice = hangUnitPrice;
    }

    public Double getHangTotalPrice() {
        return hangTotalPrice;
    }

    public void setHangTotalPrice(Double hangTotalPrice) {
        this.hangTotalPrice = hangTotalPrice;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static AssessResult invalidResult(String errorMsg) {
        AssessResult a = new AssessResult();
        a.setErrorMsg(errorMsg);
        return a;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return this.unitPrice != null && this.totalPrice != null;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getHangIds() {
        return hangIds;
    }

    public void setHangIds(List<String> hangIds) {
        this.hangIds = hangIds;
    }

    public List<String> getDealIds() {
        return dealIds;
    }

    public void setDealIds(List<String> dealIds) {
        this.dealIds = dealIds;
    }

    @Override
    public String toString() {
        return "AssessResult{" +
                "unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
