package com.address.util;

import com.address.mapper.StdMapper;
import com.address.model.HouseDeal;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.service.HousesService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Cuill on 2016/12/15. 未能匹配标准地址的交易案例 匹配标准地址解析程序
 */
public class HousesUtil {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdMapper stdMapper = context.getBean(StdMapper.class);
        HousesService housesService = context.getBean(HousesService.class);
        List<HouseDeal> dealList = stdMapper.selectHouses();
        if (null == dealList)
            return;

        for (HouseDeal deal : dealList) {
            HouseDeal result = new HouseDeal();

            StdModel model = HousesAddressExtractor.parseAll(new StdModel(deal.getAddress()));
            if(model==null) {
            	continue;
            }
            String analyAddr = "";
            if (null != model.getResidence())
                analyAddr += model.getResidence() + ",";

            if (null != model.getRoad())
                analyAddr += model.getRoad() + ",";

            if (null != model.getLane())
                analyAddr += model.getLane() + ",";

            if (null != model.getBuilding())
                analyAddr += model.getBuilding() + ",";

            if (null != model.getHouseNum())
                analyAddr += model.getHouseNum() + ",";

            result.setAnalyAddr(analyAddr);

            List<ReturnParam> analysis = housesService.analysis(deal.getAddress());
            ReturnParam param = analysis.get(0);
            result.setNoMappingType(param.getFlag());
            result.setBuilding(model.getBuilding());
            result.setRoom(model.getHouseNum());
            String roadLane = null;
            if(model.getRoad()!=null &&model.getLane()!=null) {
            	roadLane = model.getRoad()+model.getLane();
            } else if (model.getRoad()!=null) {
            	roadLane = model.getRoad();
            } else if (model.getRoad()!=null) {
            	roadLane = model.getLane();
            }
            result.setResidence(roadLane);
            result.setStdAddrId(param.getId());
            result.setId(deal.getId());
            stdMapper.updateHouses(result);
            
            /*//添加入库
            if(deal.getId()!=null&&param.getId()!=null) {
            	stdMapper.updateMapping(result);
            }*/
        }
    }
}
