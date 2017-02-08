package com.lezhi.address.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.address.admin.mapper.AnalyAddrMapper;
import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.AnalyMatchDto;
import com.lezhi.address.admin.service.AnalyAddrService;

/**
 * Created by Cuill on 2017/1/23.
 */
@Service
public class AnalyAddrServiceImpl implements AnalyAddrService {
    @Autowired
    private AnalyAddrMapper analyAddrMapper;

    @Override
    public List<Address> getParsedFailedAddr() {
        return analyAddrMapper.getParsedFailedAddr();
    }

    @Override
    public List<AnalyMatchDto> getAnalyMatchList(Integer analysisTaskId, Integer page) {
        if (null == page)
            page = 1;

        AnalyMatchDto dto1 = analyAddrMapper.selectAddrTableNameByAnaTaskId(analysisTaskId);
        if (null == dto1)
            return null;

        List<AnalyMatchDto> analyMatchDtos = analyAddrMapper.selectAddrList(dto1.getTableName(), null);
        List<AnalyMatchDto> page_result = analyAddrMapper.selectAddrList(dto1.getTableName(),
                (page - 1) * 20);
        if (null == analyMatchDtos)
            return null;
        for (AnalyMatchDto dto : page_result) {
            dto.setDataName("ocn_Address");// 数据库
            dto.setTableName(dto1.getTableName());
            dto.setAnalTaskId(analysisTaskId);
            dto.setName(dto1.getName());
            dto.setId(dto1.getId());
            dto.setTotalPage(analyMatchDtos.size() / 20 + 1);
        }
        return page_result;
    }

    @Override
    public AnalyMatchDto selectAddressById(Integer id, String dataName, String tableName) {
        return analyAddrMapper.selectAddressById(id, dataName, tableName);
    }
}