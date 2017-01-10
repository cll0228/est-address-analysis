package com.address.mapper;

import java.util.List;

import com.address.model.*;
import org.apache.ibatis.annotations.Param;

import com.address.model.HouseDeal;
import com.address.model.OfHouse;
import com.address.model.PriceTrend;
import com.address.model.ResidenceDetail;
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
    
    List<HouseDeal> selectBillPlan();
    
    List<HouseDeal> selectAiHdNoChai();
    
    List<HouseDeal> selectExtract();
    
    List<HouseDeal> selectHouses();
    
    List<OfHouse> selectOfHouse();
    
    List<HouseDeal> selectHouseDeal();

    void updateResult(@Param("deal") HouseDeal deal);
    
    void updateMapping(@Param("deal") HouseDeal deal);
    
    void insertOuterAddress(@Param("deal") HouseDeal deal);
    
    void updateAiHd(@Param("deal") HouseDeal deal);
    
    void updateExtract(@Param("deal") HouseDeal deal);
    
    void updateBillPlan(@Param("deal") HouseDeal deal);
    
    void updateHouses(@Param("deal") HouseDeal deal);
    
    void insertOfHouse(@Param("ofHouse") OfHouse ofHouse);

    List<HouseDeal> selectTdtAddress();

    void updateTdtResult(@Param("deal")HouseDeal deal);
    
    ResidenceDetail selectResidenceDetail(@Param("roadLane")String roadLane);
    
    List<PriceTrend> getResidenceTradeAvgPriceList(@Param("residenceId")Integer residenceId,
            @Param("startMonth")String startMonth, @Param("endMonth")String endMonth);

    List<ResidenceBoundary> selectResiBoundaryById(@Param("id")String id);
}
