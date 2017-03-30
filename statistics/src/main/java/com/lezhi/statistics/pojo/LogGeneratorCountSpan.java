package com.lezhi.statistics.pojo;

/**
 * Created by wangyh on 2017/3/29.
 */
public class LogGeneratorCountSpan {
	private Integer count;
	private Long spanTime;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Long getSpanTime() {
		return spanTime;
	}
	public void setSpanTime(Long spanTime) {
		this.spanTime = spanTime;
	}
	@Override
	public String toString() {
		return "LogGeneratorDistrict [count=" + count + ", spanTime="
				+ spanTime + "]";
	}
}