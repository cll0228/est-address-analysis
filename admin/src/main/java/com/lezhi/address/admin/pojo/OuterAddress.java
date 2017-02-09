package com.lezhi.address.admin.pojo;

/**
 * Created by wangyh on 2017/2/83.
 */
public class OuterAddress {
    private String srcTable;
    private Integer srcTableId;
    private Integer stdAddrId;
	public String getSrcTable() {
		return srcTable;
	}
	public void setSrcTable(String srcTable) {
		this.srcTable = srcTable;
	}
	public Integer getSrcTableId() {
		return srcTableId;
	}
	public void setSrcTableId(Integer srcTableId) {
		this.srcTableId = srcTableId;
	}
	public Integer getStdAddrId() {
		return stdAddrId;
	}
	public void setStdAddrId(Integer stdAddrId) {
		this.stdAddrId = stdAddrId;
	}
	@Override
	public String toString() {
		return "OuterAddress [srcTable=" + srcTable + ", srcTableId="
				+ srcTableId + ", stdAddrId=" + stdAddrId + "]";
	}
}
