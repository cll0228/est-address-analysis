package com.lezhi.address.admin.mapper;

import com.lezhi.address.admin.pojo.OfResidence;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2017/1/18.
 */
public interface OfResidenceMapper {
    List<OfResidence> selectResidenceByName(@Param("keyword") String keyword);
}
