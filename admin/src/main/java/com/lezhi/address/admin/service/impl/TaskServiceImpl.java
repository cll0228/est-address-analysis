package com.lezhi.address.admin.service.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.address.admin.mapper.StdMapper;
import com.lezhi.address.admin.mapper.TaskMapper;
import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.OuterAddress;
import com.lezhi.address.admin.pojo.ReturnParam;
import com.lezhi.address.admin.pojo.StdModel;
import com.lezhi.address.admin.pojo.TAnalysisTask;
import com.lezhi.address.admin.pojo.TMatchTask;
import com.lezhi.address.admin.pojo.Task;
import com.lezhi.address.admin.service.StdService;
import com.lezhi.address.admin.service.TaskService;
import com.lezhi.address.admin.util.AddressExtractor;

/**
 * Created by Colin Yan on 2017/2/7.
 */
@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private StdMapper stdMapper;
	@Autowired
	private StdService stdService;
    @Override
    public Address match(int analysisTaskId, int addressId, int operatorUserId) {
    	//根据analysisTaskId获取
    	Task task = taskMapper.getTaskInfo(analysisTaskId);
    	String address = null;
    	Address reAdd = new Address();
    	reAdd.setId(addressId);
    	reAdd.setMatchStatus(20);
    	reAdd.setMatchTime(new Date());
    	if(task!=null) {
    		String sql = "SELECT "+task.getAddressColumn()+" FROM "+task.getTableName()+" WHERE id="+addressId;
    		try {
    			ResultSet rs = AddressExtractor.connJDBC(task.getServer(), task.getDbSchema(), task.getUsername(), task.getPassword(), sql);
    			while (rs.next()) {
    				address = rs.getString(task.getAddressColumn());
    			}
    			if(address!=null) {
    				List<ReturnParam> rList = stdService.analysis(address);
    				if(rList!=null&&rList.size()>0) {
    					if(rList.get(0).getFlag().equals(1)) {
    						OuterAddress outerAddress = new OuterAddress();
        					outerAddress.setSrcTable(task.getTableName());
        					outerAddress.setSrcTableId(task.getId());
        					outerAddress.setStdAddrId(Integer.parseInt(rList.get(0).getId()));
        					try {
								stdMapper.insertOuterAddress(outerAddress);
							} catch (Exception e) {
								e.printStackTrace();
							}
        					reAdd.setMatchStatus(10);
        					reAdd.setMatchTime(new Date());
    					}
    				}
    			}
    		} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return reAdd;
    }

    @Override
    public int createAnalysisTask(int datasourceId, String targetTablePk, String targetTableName, String targetColumnName, String dbSchema, String taskName, boolean autoMatch, int operatorUserId) {
    	TAnalysisTask task = new TAnalysisTask();
    	task.setName(taskName);
    	task.setDbsId(datasourceId);
    	task.setTableName(targetTableName);
    	task.setAddressColumn(targetColumnName);
    	task.setAutoMatch(autoMatch);
    	task.setStatus(200);
    	task.setOperator(operatorUserId);
    	task.setDbSchema(dbSchema);
    	taskMapper.insertTAnalysisTask(task);
    	int id = task.getId();
    	//根据analysisTaskId获取
    	Task task2 = taskMapper.getTaskInfo(id);
    	
    	String sql = "ALTER TABLE "+targetTableName+" ADD parsed_road_lane varchar(210), ADD parsed_building_no varchar(20), ADD parsed_house_no varchar(20), ADD parsed_version int(11) DEFAULT '0', ADD parsed_time datetime(6), ADD parsed_status int(11) DEFAULT '0', ADD match_status int(11) DEFAULT '0', ADD match_time datetime(6);";
    	try {
			AddressExtractor.alterTable(task2.getServer(), task2.getDbSchema(), task2.getUsername(), task2.getPassword(), sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Thread t = new Thread(new Runnable(){  
            public void run(){  
            	//开始解析入库操作
            	String sql2 = "SELECT "+targetColumnName+","+targetTablePk+" FROM "+targetTableName+" WHERE parsed_status is null";
            	try {
            		String address = null;
        			ResultSet rs = AddressExtractor.connJDBC(task2.getServer(), task2.getDbSchema(), task2.getUsername(), task2.getPassword(), sql2);
        			int count = 0;
        			int successCount = 0;
        			int failedCount = 0;
        			while (rs.next()) {
        				count++;
        				address = rs.getString(task.getAddressColumn());
        				StdModel std = AddressExtractor.parseAll(new StdModel(address));
        				StringBuffer sql3 = new StringBuffer();
        				if((null!=std.getRoad()&&null!=std.getLane())||(null!=std.getRoad()&&null!=std.getBuilding())) {
        					if(null!=std.getRoad()&&null!=std.getLane()) {
        						sql3.append("UPDATE "+targetTableName+" SET parsed_road_lane='"+std.getRoad()+std.getLane()+"',parsed_building_no='"+std.getBuilding()+"',parsed_house_no='"+std.getHouseNum()+"',");
        					} else if(null!=std.getRoad()&&null!=std.getBuilding()&&null==std.getLane()){
        						sql3.append("UPDATE "+targetTableName+" SET parsed_road_lane='"+std.getRoad()+std.getBuilding()+"',parsed_house_no='"+std.getHouseNum()+"',");
        					} else {
        						sql3.append("UPDATE "+targetTableName+" SET parsed_status=20,parsed_version=1,parsed_time=NOW() WHERE "+targetTablePk+"="+rs.getInt(targetTablePk));
        						failedCount++;
        					}
        					sql3.append("parsed_status=10,parsed_version=1,parsed_time=NOW() WHERE "+targetTablePk+"="+rs.getInt(targetTablePk));
        					successCount++;
        				} else {
        					sql3.append("UPDATE "+targetTableName+" SET parsed_status=20,parsed_version=1,parsed_time=NOW() WHERE "+targetTablePk+"="+rs.getInt(targetTablePk));
        					failedCount++;
        				}
        				AddressExtractor.alterTable(task2.getServer(), task2.getDbSchema(), task2.getUsername(), task2.getPassword(), sql3.toString());
        				if(count==100) {
        					count=0;
        					task.setSuccessCount(successCount);
        					task.setFailedCount(failedCount);
        					taskMapper.updateTAnalysisTask(task);
        				}
        			}
        			task.setSuccessCount(successCount);
					task.setFailedCount(failedCount);
					task.setStatus(300);
					taskMapper.updateTAnalysisTask(task);
					if(autoMatch) {
						createMatchTask(operatorUserId, id, true);
					}
            	} catch (Exception e) {
        			e.printStackTrace();
        		}
            }});  
        t.start();  
    	return id;
    }

    @Override
    public int createMatchTask(int operatorUserId, int analysisTaskId, boolean unMatchedOnly) {
    	//根据analysisTaskId获取
    	Task task = taskMapper.getTaskInfo(analysisTaskId);
    	int id = 0;
    	if(task!=null) {
    		TMatchTask tMatchTask = new TMatchTask();
    		tMatchTask.setAnalysisTaskId(analysisTaskId);
    		tMatchTask.setStatus(200);
    		tMatchTask.setOperator(operatorUserId);
    		taskMapper.insertTMatchTask(tMatchTask);
    		id = tMatchTask.getId();
    		
    		Thread t = new Thread(new Runnable(){  
                public void run(){
                	String sql = null;
                	if(unMatchedOnly) {
                		sql = "SELECT "+task.getAddressColumn()+" FROM "+task.getTableName()+" WHERE match_status!=10";
                	} else {
                		sql = "SELECT "+task.getAddressColumn()+" FROM "+task.getTableName();
                	}
            		try {
            			ResultSet rs = AddressExtractor.connJDBC(task.getServer(), task.getDbSchema(), task.getUsername(), task.getPassword(), sql);
            			int count = 0;
            			int successCount = 0;
            			int failedCount = 0;
            			String address = null;
            			while (rs.next()) {
            				count++;
            				address = rs.getString(task.getAddressColumn());
            				StringBuffer sql2 = new StringBuffer();
            				if(address!=null) {
                				List<ReturnParam> rList = stdService.analysis(address);
                				if(rList!=null&&rList.size()>0) {
                					if(rList.get(0).getFlag().equals("1")) {
                						OuterAddress outerAddress = new OuterAddress();
                    					outerAddress.setSrcTable(task.getTableName());
                    					outerAddress.setSrcTableId(task.getId());
                    					outerAddress.setStdAddrId(Integer.parseInt(rList.get(0).getId()));
                    					try {
											stdMapper.insertOuterAddress(outerAddress);
										} catch (Exception e) {
											e.printStackTrace();
										}
                    					successCount++;
                    					sql2.append("UPDATE "+task.getTableName()+" SET match_status=10,match_time=NOW()");
                					} else {
                						failedCount++;
                						sql2.append("UPDATE "+task.getTableName()+" SET match_status=20,match_time=NOW()");
                					}
                				} else {
                					failedCount++;
                					sql2.append("UPDATE "+task.getTableName()+" SET match_status=20,match_time=NOW()");
                				}
                				AddressExtractor.alterTable(task.getServer(), task.getDbSchema(), task.getUsername(), task.getPassword(), sql2.toString());
                			}
            				if(count==100) {
            					count=0;
            					tMatchTask.setSuccessCount(successCount);
            					tMatchTask.setFailedCount(failedCount);
            					taskMapper.updateTMatchTask(tMatchTask);
            				}
            			}
            			tMatchTask.setSuccessCount(successCount);
            			tMatchTask.setFailedCount(failedCount);
            			tMatchTask.setStatus(300);
        				taskMapper.updateTMatchTask(tMatchTask);
            		} catch (Exception e) {
        				e.printStackTrace();
        			}
                }});  
            t.start(); 
    	}
    	
        return id;
    }
}
