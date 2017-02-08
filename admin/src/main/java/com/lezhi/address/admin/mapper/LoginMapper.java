package com.lezhi.address.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.TUser;

/**
 * Created by wangyh on 2017/2/7.
 */
public interface LoginMapper {
	TUser userLogin(@Param("username") String username);
	
	void updateLoginTime(@Param("tUser")TUser tUser);
}
