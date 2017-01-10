package com.address.model;

public class PriceTrend {

    private String month;

    private Integer dealAvgPrice1st;

    private Integer dealCount1st;

    private Integer dealAvgPrice2nd;

    private Integer dealCount2nd;

	public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getDealAvgPrice1st() {
        return dealAvgPrice1st;
    }

    public void setDealAvgPrice1st(Integer dealAvgPrice1st) {
        this.dealAvgPrice1st = dealAvgPrice1st;
    }

    public Integer getDealCount1st() {
        return dealCount1st;
    }

    public void setDealCount1st(Integer dealCount1st) {
        this.dealCount1st = dealCount1st;
    }

    public Integer getDealAvgPrice2nd() {
        return dealAvgPrice2nd;
    }

    public void setDealAvgPrice2nd(Integer dealAvgPrice2nd) {
        this.dealAvgPrice2nd = dealAvgPrice2nd;
    }

    public Integer getDealCount2nd() {
        return dealCount2nd;
    }

    public void setDealCount2nd(Integer dealCount2nd) {
        this.dealCount2nd = dealCount2nd;
    }

    @Override
    public String toString() {
        return "PriceTrend [month=" + month + ", dealAvgPrice1st=" + dealAvgPrice1st + ", dealCount1st="
                + dealCount1st + ", dealAvgPrice2nd=" + dealAvgPrice2nd + ", dealCount2nd=" + dealCount2nd
                + "]";
    }

}
