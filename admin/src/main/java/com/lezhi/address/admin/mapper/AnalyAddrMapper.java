package com.lezhi.address.admin.mapper;

import com.lezhi.address.admin.pojo.Address;

import java.util.List;

/**
 * Created by Cuill on 2017/1/23.
 */
public interface AnalyAddrMapper {
    List<Address> getParsedFailedAddr();
}
