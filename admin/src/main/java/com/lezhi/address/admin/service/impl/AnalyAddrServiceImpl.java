package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.AnalyAddrMapper;
import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.service.AnalyAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
