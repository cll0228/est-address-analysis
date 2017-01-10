package com.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.address.mapper.StdMapper;
import com.address.model.HouseDeal;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.service.StdService;
import com.address.util.AddressExtractor;

/**
 * Created by Cuill on 2016/12/12.
 */
@Controller
@RequestMapping(value = "/")
public class StdController {

    @Autowired
    private StdService stdService;
    @Autowired
    private StdMapper stdMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(StdController.class);

    @RequestMapping("index")
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> analysis(@RequestParam("address") String address) {
        LOGGER.info("正在执行请求address=" + address);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<ReturnParam> paramList = stdService.analysis(address);
        LOGGER.info("返回resultList条数=" + paramList.size());
        if (paramList != null) {
            for (ReturnParam returnParam : paramList) {
                Map<String, Object> result = new HashMap<>();
                result.put("qx", returnParam.getDistrict());
                result.put("jd", returnParam.getStreet());
                result.put("jw", returnParam.getCommitte());
                result.put("ln", returnParam.getRoadLane());
                result.put("h", returnParam.getBuilding());
                result.put("s", returnParam.getHouseNo());
                result.put("bm", returnParam.getAddrCode());
                result.put("f", returnParam.getFlag());
                result.put("id",returnParam.getId());
                LOGGER.info("返回状态码 flag = " + returnParam.getFlag());
                resultList.add(result);
            }
        }
        return resultList;

    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insertInto1400() {
    	List<HouseDeal> dealList = stdMapper.selectExtract();
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
            result.setBuilding(model.getBuilding());
            result.setRoom(model.getHouseNum());
            result.setStdAddrId(param.getId());
            result.setId(deal.getId());
            stdMapper.updateExtract(result);
            result.setAnalyAddr("extract_1400");
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
    
    @RequestMapping(value = "/insertBillPlan", method = RequestMethod.GET)
    @ResponseBody
    public void insertIntoBillPlan() {
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
