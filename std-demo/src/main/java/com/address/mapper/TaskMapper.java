package com.address.mapper;

import com.address.model.LianjiaResidenceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Cuill on 2017/1/4.
 */
public interface TaskMapper {
    List<LianjiaResidenceInfo> getLianjiaResidenceList();

    List<LianjiaResidenceInfo> selcetResidenceByAddr(@Param("addr") String addr);

}
