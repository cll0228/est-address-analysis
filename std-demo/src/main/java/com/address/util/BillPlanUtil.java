package com.address.util;

import com.address.mapper.StdMapper;
import com.address.model.HouseDeal;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.service.StdService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Cuill on 2016/12/15. 未能匹配标准地址的交易案例 匹配标准地址解析程序
 */
public class BillPlanUtil {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdMapper stdMapper = context.getBean(StdMapper.class);
        StdService stdService = context.getBean(StdService.class);
        List<HouseDeal> dealList = stdMapper.selectBillPlan();
        if (null == dealList)
            return;

        for (HouseDeal deal : dealList) {
            HouseDeal result = new HouseDeal();

            StdModel model = AddressExtractor.parseAll(new StdModel(deal.getAddress()));
            
            if(null == model) {
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

            List<ReturnParam> analysis = stdService.analysis(deal.getAddress());
            ReturnParam param = analysis.get(0);
            result.setNoMappingType(param.getFlag());

            result.setId(deal.getId());
            result.setStdAddrId(param.getId());

            stdMapper.updateBillPlan(result);
            result.setAnalyAddr("bill_plan");
            //添加入库
            try {
				if(deal.getId()!=null&&param.getId()!=null) {
					stdMapper.insertOuterAddress(result);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
