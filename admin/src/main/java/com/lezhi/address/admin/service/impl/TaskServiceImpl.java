package com.lezhi.address.admin.service.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.lezhi.address.admin.mapper.StdMapper;
import com.lezhi.address.admin.mapper.TaskMapper;
import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.OuterAddress;
import com.lezhi.address.admin.pojo.ReturnParam;
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
    					Address reAdd = new Address();
    					reAdd.setMatchStatus(10);
    					reAdd.setMatchTime(new Date());
    					return reAdd;
    				} else {
    					return null;
    				}
    			} else {
    				return null;
    			}
    		} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
    	} else {
    		return null;
    	}
    	
    }

    @Override
    public int createAnalysisTask(int datasourceId, String targetTableName, String targetColumnName, String taskName, boolean autoMatch, int operatorUserId) {
        return 0;
    }

    @Override
    public int createMatchTask(int analysisTaskId, boolean unMatchedOnly) {
        return 0;
    }
}
