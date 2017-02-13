package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.Address;
import com.lezhi.address.admin.pojo.DbServer;
import com.lezhi.address.admin.pojo.TDatasource;

import java.util.List;

/**
 * Created by chendl on 2017/2/07.
 */
public interface DataSourceService {
    List<TDatasource> getDataSourceList();

    Integer addServer(String serverIp,String type, String userName, String password, String alias, String addStaff);

    Integer editServer(String serverIp, String type, String userName, String password, String alias, String operateStaff, Integer id);

    Integer deleteServer(String operateStaff, Integer id);
}
