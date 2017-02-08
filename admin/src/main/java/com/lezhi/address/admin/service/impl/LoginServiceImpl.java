package com.lezhi.address.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lezhi.address.admin.mapper.LoginMapper;
import com.lezhi.address.admin.pojo.TUser;
import com.lezhi.address.admin.service.LoginService;

/**
 * Created by wangyh on 2017/2/7.
 */
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public TUser userLogin(String username) {
		return loginMapper.userLogin(username);
	}

	@Override
	public void updateLoginTime(TUser tUser) {
		loginMapper.updateLoginTime(tUser);
		
	}
}
