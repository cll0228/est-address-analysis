package com.lezhi.address.admin.pojo;

public class Task {
	private Integer id;
    private Integer dbsId;
    private String tableName;
    private String addressColumn;
    private String server;
    private String username;
    private String password;
    private String dbSchema;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDbSchema() {
		return dbSchema;
	}
	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", dbsId=" + dbsId + ", tableName="
				+ tableName + ", addressColumn=" + addressColumn + ", server="
				+ server + ", username=" + username + ", password=" + password
				+ ", dbSchema=" + dbSchema + "]";
	}
}
