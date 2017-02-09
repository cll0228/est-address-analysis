package com.lezhi.address.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.AnalyMatchDto;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface AnalyAddrMapper {
    List<Address> getParsedFailedAddr();

    AnalyMatchDto selectAddrTableNameByAnaTaskId(@Param("analysisTaskId") Integer analysisTaskId);

    List<AnalyMatchDto> selectAddrList(@Param("tableName") String tableName, @Param("page") Integer page);

    AnalyMatchDto selectAddressById(@Param("id") Integer id, @Param("dataName") String dataName,
            @Param("tableName") String tableName);

}
