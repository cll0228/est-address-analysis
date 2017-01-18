package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.OfResidenceMapper;
import com.lezhi.address.admin.pojo.OfResidence;
import com.lezhi.address.admin.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cuill on 2017/1/18.
 */
@Service
public class ResidenceServiceImpl implements ResidenceService {

    @Autowired
    private OfResidenceMapper ofResidenceMapper;

    @Override
    public List<OfResidence> selectResidenceByName(String keyword) {
       return ofResidenceMapper.selectResidenceByName(keyword);
    }
}
