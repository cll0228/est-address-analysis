package com.address.mapper;

import com.address.model.ReturnParam;
import com.address.model.StdModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2016/12/13.
 */
public interface StdMapper {
    String getDistrictByRoad(@Param("road") String road);

    List<ReturnParam> getStdAddr(@Param("model") StdModel model);

    ReturnParam getStdAddr1(@Param("model") StdModel model);
}
