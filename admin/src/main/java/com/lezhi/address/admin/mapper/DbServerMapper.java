package com.lezhi.address.admin.mapper;

import com.lezhi.address.admin.pojo.DbServer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DbServerMapper {
    List<DbServer> getDataSourceList();

    Integer addServer(@Param("serverIp")String serverIp, @Param("userName")String userName,
                      @Param("password")String password, @Param("alias")String alias, @Param("addStaff")String addStaff);

    Integer editServer(@Param("serverIp")String serverIp, @Param("userName")String userName,
                       @Param("password")String password, @Param("alias")String alias,
                       @Param("operateStaff")String operateStaff, @Param("id")Integer id);

    Integer deleteServer(@Param("operateStaff")String operateStaff, @Param("id")Integer id);
}