package com.lezhi.address.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lezhi.address.admin.pojo.OuterAddress;
import com.lezhi.address.admin.pojo.ReturnParam;
import com.lezhi.address.admin.pojo.StdModel;


/**
 * Created by wangyh on 2017/2/8.
 */
public interface StdMapper {

    List<ReturnParam> getStdAddr(@Param("model") StdModel model);

    ReturnParam getStdAddr1(@Param("model") StdModel model);
    
    void insertOuterAddress(@Param("outerAddress") OuterAddress outerAddress);

}
