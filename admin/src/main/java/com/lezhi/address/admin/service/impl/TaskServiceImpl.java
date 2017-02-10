package com.lezhi.address.admin.service.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.lezhi.address.admin.mapper.StdMapper;
import com.lezhi.address.admin.mapper.TaskMapper;
import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.OuterAddress;
import com.lezhi.address.admin.pojo.ReturnParam;
import com.lezhi.address.admin.pojo.TAnalysisTask;
import com.lezhi.address.admin.pojo.Task;
import com.lezhi.address.admin.service.StdService;
import com.lezhi.address.admin.service.TaskService;
import com.lezhi.address.admin.util.AddressExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    					OuterAddress outerAddress = new OuterAddress();
    					outerAddress.setSrcTable(task.getTableName());
    					outerAddress.setSrcTableId(task.getId());
    					outerAddress.setStdAddrId(Integer.parseInt(rList.get(0).getId()));
    					stdMapper.insertOuterAddress(outerAddress);
    					reAdd.setMatchStatus(10);
    					reAdd.setMatchTime(new Date());
    					return reAdd;
    				} else {
    					return reAdd;
    				}
    			} else {
    				return reAdd;
    			}
    		} catch (Exception e) {
				e.printStackTrace();
				return reAdd;
			}
    	} else {
    		return reAdd;
    	}
    	
    }

    @Override
    public int createAnalysisTask(int datasourceId, String targetTableName, String targetColumnName, String dbSchema, String taskName, boolean autoMatch, int operatorUserId) {
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
    	
    	String sql = "ALTER TABLE "+targetTableName+" ADD parsed_road_lane varchar(210), ADD parsed_building_no varchar(20), ADD parsed_house_no varchar(20), ADD parsed_version int(11), ADD parsed_time datetime(6), ADD parsed_status int(11);";
    	try {
			AddressExtractor.alterTable(task2.getServer(), task2.getDbSchema(), task2.getUsername(), task2.getPassword(), sql);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    	return id;
    }

    @Override
    public int createMatchTask(int operatorUserId, int analysisTaskId, boolean unMatchedOnly) {
        return 0;
    }
}
