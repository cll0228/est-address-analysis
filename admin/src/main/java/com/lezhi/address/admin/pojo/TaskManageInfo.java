package com.lezhi.address.admin.pojo;

/**
 * Created by chendl on 2017/2/06.
 */
public class TaskManageInfo {
    private Integer id;

    private String dataSourceServer;

    private String dataTable;

    private String addressColumn;

    private Integer totalCount;

    private Integer successCount;

    private Integer failCount;

    private String schedule;

    private String status;

    private String startTime;

    private String finishTime;

    private String operator;

    private String analysisTaskId;

    private String analysisTaskName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataSourceServer() {
        return dataSourceServer;
    }

    public void setDataSourceServer(String dataSourceServer) {
        this.dataSourceServer = dataSourceServer;
    }

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public String getAddressColumn() {
        return addressColumn;
    }

    public void setAddressColumn(String addressColumn) {
        this.addressColumn = addressColumn;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getStartTime() {

        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAnalysisTaskId() {
        return analysisTaskId;
    }

    public void setAnalysisTaskId(String analysisTaskId) {
        this.analysisTaskId = analysisTaskId;
    }

    public String getAnalysisTaskName() {
        return analysisTaskName;
    }

    public void setAnalysisTaskName(String analysisTaskName) {
        this.analysisTaskName = analysisTaskName;
    }

}
