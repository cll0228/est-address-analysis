package com.address.mapper;

import com.address.model.StdModel;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Cuill on 2016/12/13.
 */
public interface StdMapper {
    String getDistrictByRoad(@Param("road") String road);

    StdModel getStdAddr(@Param("residence") String residence);

    StdModel getStdAddr1(@Param("model") StdModel model);
}
