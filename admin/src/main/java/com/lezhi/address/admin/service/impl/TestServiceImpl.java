package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.TestMapper;
import com.lezhi.address.admin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Colin Yan on 2017/1/17.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public int testa() {
        return testMapper.testa();
    }
}
