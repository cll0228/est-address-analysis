package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.AnalyMatchDto;

import java.util.List;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface AnalyAddrService {
    List<Address> getParsedFailedAddr();

    List<AnalyMatchDto> getAnalyMatchList(Integer analysisTaskId,Integer page);

    AnalyMatchDto selectAddressById(Integer id, String dataName, String tableName);

}
