package com.lezhi.address.admin.service.impl;

import com.lezhi.address.admin.mapper.DbServerMapper;
import com.lezhi.address.admin.mapper.TDatasourceMapper;
import com.lezhi.address.admin.pojo.TDatasource;
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
    private TDatasourceMapper tDatasourceMapper;
    @Override
    public List<TDatasource> getDataSourceList() {
        return tDatasourceMapper.getDataSourceList();
    }

    public Integer addServer(String serverIp,String type, String userName, String password, String alias, String addStaff){
        return tDatasourceMapper.addServer(serverIp, type, userName, password, alias, addStaff);
    }

    public Integer editServer(String serverIp, String type, String userName, String password, String alias, String operateStaff, Integer id){
        return tDatasourceMapper.editServer(serverIp, type, userName, password, alias, operateStaff, id);
    }

    public Integer deleteServer(String operateStaff, Integer id){
        return tDatasourceMapper.deleteServer(operateStaff, id);
    }
}
