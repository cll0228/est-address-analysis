package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.TUser;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface LoginService {
    TUser userLogin(String username);
    
    void updateLoginTime(TUser tUser);
}
