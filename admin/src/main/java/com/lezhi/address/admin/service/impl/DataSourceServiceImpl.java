package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.DbServerMapper;
import com.lezhi.address.admin.pojo.DbServer;
import com.lezhi.address.admin.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chendl on 2017/2/07.
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private DbServerMapper dbServerMapper;
    @Override
    public List<DbServer> getDataSourceList() {
        return dbServerMapper.getDataSourceList();
    }

    public Integer addServer(String serverIp, String userName, String password, String alias, String addStaff){
        return dbServerMapper.addServer(serverIp, userName, password, alias, addStaff);
    }

    public Integer editServer(String serverIp, String userName, String password, String alias, String operateStaff, Integer id){
        return dbServerMapper.editServer(serverIp, userName, password, alias, operateStaff, id);
    }

    public Integer deleteServer(String operateStaff, Integer id){
        return dbServerMapper.deleteServer(operateStaff, id);
    }
}
