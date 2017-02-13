package com.lezhi.address.admin.pojo;

import java.util.Date;

public class TMatchTask {
	private Integer id;
	private Integer analysisTaskId;
    private Integer successCount;
    private Integer failedCount;
    private Date createTime;
    private Date startTime;
    private Date finishTime;
    private Integer status;
    private Integer operator;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAnalysisTaskId() {
		return analysisTaskId;
	}
	public void setAnalysisTaskId(Integer analysisTaskId) {
		this.analysisTaskId = analysisTaskId;
	}
	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	public Integer getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "TMatchTask [id=" + id + ", analysisTaskId=" + analysisTaskId
				+ ", successCount=" + successCount + ", failedCount="
				+ failedCount + ", createTime=" + createTime + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", status="
				+ status + ", operator=" + operator + "]";
	}
}
