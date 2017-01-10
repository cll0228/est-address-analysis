package com.address.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.app.model.Residence;

/**
 * Created by Colin Yan on 2016/7/20.
 */
public interface ResidenceMapper {

    List<Residence> findAll();
    List<Residence> find(@Param("keywords") String keywords[]);
}
