package com.address.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.address.model.HouseDeal;
import com.address.model.OfHouse;
import com.address.model.ReturnParam;
import com.address.model.StdModel;

/**
 * Created by Cuill on 2016/12/13.
 */
public interface StdMapper {
    List<String> getDistrictByRoad(@Param("road") String road);

    List<ReturnParam> getStdAddr(@Param("model") StdModel model);

    ReturnParam getStdAddr1(@Param("model") StdModel model);

    List<HouseDeal> selectNoMappingHouseDeal();
    
    List<HouseDeal> selectAiHdNoChai();
    
    List<HouseDeal> selectHouses();
    
    List<OfHouse> selectOfHouse();

    void updateResult(@Param("deal") HouseDeal deal);
    
    void updateMapping(@Param("deal") HouseDeal deal);
    
    void updateAiHd(@Param("deal") HouseDeal deal);
    
    void updateHouses(@Param("deal") HouseDeal deal);
    
    void insertOfHouse(@Param("ofHouse") OfHouse ofHouse);
}
