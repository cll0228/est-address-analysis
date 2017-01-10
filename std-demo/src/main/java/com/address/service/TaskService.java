package com.address.service;

import com.address.mapper.TaskMapper;
import com.address.model.LianjiaResidenceInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cuill on 2017/1/4.
 */
@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public List<LianjiaResidenceInfo> getLianjiaResidenceList() {
        return taskMapper.getLianjiaResidenceList();
    }

    public List<LianjiaResidenceInfo> selcetResidenceByAddr(String addr) {
        return taskMapper.selcetResidenceByAddr(addr);
    }
}
