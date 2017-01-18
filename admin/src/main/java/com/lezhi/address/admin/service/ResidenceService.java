package com.lezhi.address.admin.service;

import com.lezhi.address.admin.pojo.OfResidence;

import java.util.List;

/**
 * Created by Cuill on 2017/1/18.
 */
public interface ResidenceService {
    List<OfResidence> selectResidenceByName(String keyword);
}
