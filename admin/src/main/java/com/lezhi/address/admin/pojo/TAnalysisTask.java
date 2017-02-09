package com.lezhi.address.admin.pojo;

import java.util.Date;

public class TAnalysisTask {
	private Integer id;
	private String name;
    private Integer dbsId;
    private String tableName;
    private String addressColumn;
    private boolean autoMatch;
    private Integer successCount;
    private Integer failedCount;
    private Date createTime;
    private Date startTime;
    private Date finishTime;
    private Integer status;
    private Integer operator;
    private String dbSchema;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDbsId() {
		return dbsId;
	}
	public void setDbsId(Integer dbsId) {
		this.dbsId = dbsId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getAddressColumn() {
		return addressColumn;
	}
	public void setAddressColumn(String addressColumn) {
		this.addressColumn = addressColumn;
	}
	public boolean getAutoMatch() {
		return autoMatch;
	}
	public void setAutoMatch(boolean autoMatch) {
		this.autoMatch = autoMatch;
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
	public String getDbSchema() {
		return dbSchema;
	}
	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}
	@Override
	public String toString() {
		return "TAnalysisTask [id=" + id + ", name=" + name + ", dbsId="
				+ dbsId + ", tableName=" + tableName + ", addressColumn="
				+ addressColumn + ", autoMatch=" + autoMatch
				+ ", successCount=" + successCount + ", failedCount="
				+ failedCount + ", createTime=" + createTime + ", startTime="
				+ startTime + ", finishTime=" + finishTime + ", status="
				+ status + ", operator=" + operator + ", dbSchema=" + dbSchema
				+ "]";
	}
}
