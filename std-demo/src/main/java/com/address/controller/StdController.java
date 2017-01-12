package com.address.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.address.model.AssessParam;
import com.address.model.AssessResult;
import com.address.model.FacilityScore;
import com.address.model.HouseDeal;
import com.address.model.OfHouse;
import com.address.model.PoiDetail;
import com.address.model.PriceTrend;
import com.address.model.ResidenceDetail;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.service.IAssService;
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
    @Autowired
	private IAssService assService;

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
        int flag = 0;
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
                LOGGER.info("返回状态码 flag = " + returnParam.getFlag());
                // 小区配套查询（默认交通，0.5km）
                List<PoiDetail> poiList = stdService.getResidencePoiDetailList(returnParam.getRoadLane(), "0.5", "交通");
                result.put("poiList",poiList);
                ResidenceDetail detail = null;
                if(flag==0&&returnParam.getFlag().equals("1")) {
                	flag++;
                	detail = stdMapper.selectResidenceDetail(returnParam.getRoadLane());
                	result.put("detail", detail);
                	List<PriceTrend> list = stdService.getResidenceTradeAvgPriceList(55);//detail.getId()
                	int[] price = new int[list.size()];
                	String[] month = new String[list.size()];
                	for (int i = 0; i < list.size(); i++) {
                		price[i] = list.get(i).getDealAvgPrice2nd();
                		month[i] = list.get(i).getMonth();
					}
                	result.put("a", price);
                	result.put("b", month);
                }
                
                OfHouse ofHouse = stdMapper.selectHouseByStdAddrId(returnParam.getId(),returnParam.getHouseNo()==null?returnParam.getHouseNo():returnParam.getHouseNo().replace("室", ""),returnParam.getBuilding());
                if(ofHouse!=null) {
                	if(null!=ofHouse.getArea()) {
                		result.put("d", "true");
                		result.put("area", ofHouse.getArea());
                		result.put("towards", ofHouse.getTowards());
                		if(detail!=null) {
                			if(null!=detail.getAccomplishDate()) {
                				SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
                				String d = format.format(detail.getAccomplishDate());
                				result.put("accomplishDate", d);
                			}
                			
                		}
                		
                		if(ofHouse.getArea() < 60){
                			result.put("roomType", "一室");
                        }
                        if(ofHouse.getArea() >= 60 && ofHouse.getArea() <90){
                            result.put("roomType", "二室");
                        }
                        if(ofHouse.getArea() >= 90 && ofHouse.getArea() <140){
                        	result.put("roomType", "三室");
                        }
                        if(ofHouse.getArea() >= 140){
                        	result.put("roomType", "多室");
                        }
                		
                	} else {
                		result.put("d", "false");
                	}
                	FacilityScore facilityScore = stdMapper.getResidenceFacilityScore(ofHouse.getResidenceId());
                	if (facilityScore != null) {
                		int[] facility = new int[5];
                		facility[0] = facilityScore.getTransportScore().intValue();
                		facility[1] = facilityScore.getMedicalScore().intValue();
                		facility[2] = facilityScore.getShoppingScore().intValue();
                		facility[3] = facilityScore.getEducationScore().intValue();
                		facility[4] = facilityScore.getLivingScore().intValue();
                		result.put("c", facility);
                    }
                	
                	//估价
                	Integer residenceId = 123;
            		Integer roomNum = 123;
            		Integer hallNum = 123;
            		Integer toiletNum = 123;
            		String buildingNo = "123";
            		String roomNo = "123";
            		Double propertyArea = 123D;
            		Integer placeFloor = 123;
            		Integer totalFloor = 123;
            		Integer toward = 123;
            		Integer landScape = 123;
            		Integer nearStreet = 123;
            		Integer planeType = 123;
            		Integer propertyTypeId = 123;
            		
            		AssessParam assessParam = new AssessParam();
            		assessParam.setResidenceId(residenceId);
            		assessParam.setRoomNum(roomNum);
            		assessParam.setHallNum(hallNum);
            		assessParam.setToiletNum(toiletNum);
            		assessParam.setBuildingNo(buildingNo);
            		assessParam.setRoom(roomNo);
            		assessParam.setPropertyArea(propertyArea);
            		assessParam.setPropertyStorey(totalFloor);
            		assessParam.setPropertyType(propertyTypeId);
            		assessParam.setFloor(placeFloor);
            		assessParam.setToward(toward);
            		assessParam.setLandScape(landScape);
            		assessParam.setNearStreet(nearStreet);
            		assessParam.setPlaneType(planeType);

            		AssessResult dto = assService.assHouse(assessParam);
            		Double assTotalPrice = dto.getTotalPrice();
            		Integer assUnitPrice = dto.getUnitPrice();
            		if(assTotalPrice != null && assUnitPrice != null){
            			result.put("assTotalPrice",assTotalPrice);
            			result.put("assUnitPrice",assUnitPrice);
            		}
                }
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
