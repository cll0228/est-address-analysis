package com.lezhi.address.admin.service;

import java.util.List;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.AnalyMatchDto;
import com.lezhi.address.admin.pojo.TaskManageInfo;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface AnalyAddrService {
    List<Address> getParsedFailedAddr();

    List<AnalyMatchDto> getAnalyMatchList(Integer analysisTaskId, Integer page);

    AnalyMatchDto selectAddressById(Integer id, String dataName, String tableName);

    List<TaskManageInfo> selcetMatchTask();

    List<TaskManageInfo> getAnalyAddrTaskList();

    List<AnalyMatchDto> getAnalyAddrLikeList(String addrLike, Integer page, Integer analyStatus,
            Integer matchStatus, String dataName, String tableName, Integer analySisTaskId);

    void humanEditAddr(String roadLane, String building, String house, Integer addressId, String dataName,
            String tableName) throws Exception;
}
