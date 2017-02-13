package com.lezhi.address.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.AnalyMatchDto;
import com.lezhi.address.admin.pojo.TaskManageInfo;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface AnalyAddrMapper {
    List<Address> getParsedFailedAddr();

    AnalyMatchDto selectAddrTableNameByAnaTaskId(@Param("analysisTaskId") Integer analysisTaskId);

    List<AnalyMatchDto> selectAddrList(@Param("tableName") String tableName, @Param("page") Integer page);

    AnalyMatchDto selectAddressById(@Param("id") Integer id, @Param("dataName") String dataName,
            @Param("tableName") String tableName);

    List<TaskManageInfo> selcetMatchTask();

    List<TaskManageInfo> getAnalyAddrTaskList();

    List<AnalyMatchDto> getAnalyAddrLikeList(@Param("addrLike") String addrLike, @Param("page") Integer page,
            @Param("analyStatus") Integer analyStatus, @Param("matchStatus") Integer matchStatus,
            @Param("dataName") String dataName, @Param("tableName") String tableName);

    void humanEditAddr(@Param("roadLane") String roadLane, @Param("building") String building,
            @Param("house") String house, @Param("addressId") Integer addressId,
            @Param("dataName") String dataName, @Param("tableName") String tableName);
}
