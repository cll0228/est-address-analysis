package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * Created by Colin Yan on 2017/2/7.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Address match(int analysisTaskId, int addressId, int operatorUserId) {
        return null;
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
