package com.address.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.address.model.PoiDetail;
import com.address.model.ResidenceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.address.mapper.StdMapper;
import com.address.model.PriceTrend;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.util.AddressExtractor;
import com.address.util.CommUitl;

/**
 * Created by Cuill on 2016/12/12.
 */
@Service
public class StdService {

    @Autowired
    private StdMapper mapper;

    static SimpleDateFormat SD = new SimpleDateFormat("yyyyMM");
    
    public List<ReturnParam> analysis(String address) {
        address = address.replaceAll(" ", "");
        if (address.indexOf("下") + 1 == address.length()) {
            address = address.replace("下", "");
        }
        List<ReturnParam> list = new ArrayList<>();
        ReturnParam reParam = new ReturnParam();
        if (null == address || "".equals(address)) {
            reParam.setFlag("8");
            list.add(reParam);
            return list;
        }

        StdModel model = AddressExtractor.parseAll(new StdModel(address));
        System.out.println("地址解析成功=" + model.toString());
        if (model.getResidence() == null && model.getRoad() == null) {
            reParam.setFlag("3");
            list.add(reParam);// 解析失败
            return list;
        }
        reParam.setFlag("1");

        // 判断区县是否对应

        /*if (model.getRoad() != null && model.getDistrict() != null) {
            String districtName = null;
            List<String> districtNameList = mapper.getDistrictByRoad(model.getRoad());
            if (null != districtNameList && districtNameList.size() != 1) {
                // 路对应多个区
                boolean flag = false;
                for (String name : districtNameList) {
                    if (name.equals(model.getDistrict())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    reParam.setFlag("2");// 区县不对应
                    list.add(reParam);
                    return list;
                }
            } else {
                districtName = districtNameList.get(0);
                if (null == districtName) {
                    reParam.setFlag("5");
                    list.add(reParam);
                    return list;
                }
                if (null != districtName && !districtName.equals(model.getDistrict())) {
                    reParam.setFlag("2");// 区县不对应
                    list.add(reParam);
                    return list;
                }
            }
        }*/

        // 小区名称
        if (null != model.getResidence() && model.getRoad() == null) {
            list = mapper.getStdAddr(model);
            if (null != list && list.size() > 1) {
                for (ReturnParam param : list) {
                    param.setFlag("1");
                    if (null == model.getHouseNum()) {
                        param.setHouseNo(null);
                        if (null != param.getAddrCode()) {
                            param.setAddrCode(param.getAddrCode().replace(
                                    param.getAddrCode().substring(19, param.getAddrCode().length()), "0000"));
                        }
                    }
                    if (null == model.getBuilding()) {
                        param.setBuilding(null);
                        if (null != param.getAddrCode()) {
                            param.setAddrCode(param.getAddrCode().replace(
                                    param.getAddrCode().substring(15, param.getAddrCode().length()),
                                    "00000000"));
                        }
                    }
                }

                return list;
            } else if (list.size() == 1) {
                reParam = list.get(0);
                reParam.setFlag("1");
                if (null == model.getHouseNum()) {
                    reParam.setHouseNo(null);
                    if (null != reParam.getAddrCode()) {
                    	reParam.setAddrCode(reParam.getAddrCode().replace(reParam.getAddrCode().substring(19, reParam.getAddrCode().length()),"0000"));
                    }
                }
                if (null == model.getBuilding()) {
                    reParam.setBuilding(null);
                    if (null != reParam.getAddrCode()) {
                    	reParam.setAddrCode(reParam.getAddrCode().replace(reParam.getAddrCode().substring(15, reParam.getAddrCode().length()),"00000000"));
                    }
                }
                list = new ArrayList<>();
                list.add(reParam);
                return list;
            } else {
                // 去标准地址库中查询
//                model.setRoad(model.getResidence());
                ReturnParam stdAddr1 = this.getStdAddr1(model);
                if (null == stdAddr1) {
                    reParam = new ReturnParam();
                    reParam.setFlag("4");
                    list = new ArrayList<>();
                    list.add(reParam);
                    return list;
                } else {
                    list = new ArrayList<>();
                    list.add(stdAddr1);
                    return list;
                }

            }
        }

        // 路弄
        if (null != model.getRoad() || model.getLane() != null) {
            list.add(this.getStdAddr1(model));
            return list;
        }
        return null;
    }

    // model去标准库中查询
    private ReturnParam getStdAddr1(StdModel model) {
        ReturnParam reParam = mapper.getStdAddr1(model);
        if (null == reParam) {
            if (null == model.getBuilding() && null == model.getHouseNum()) {
            	reParam = new ReturnParam();
                reParam.setFlag("4");
            	return reParam;
            }
            if (null != model.getHouseNum() && null != model.getBuilding()) {
            	model.setHouseNum(null);
            	reParam = mapper.getStdAddr1(model);
            	if (null != reParam) {
            		reParam = new ReturnParam();
                    reParam.setFlag("6");
            	} else {
            		model.setBuilding(null);
            		reParam = mapper.getStdAddr1(model);
            		if(null != reParam) {
            			reParam = new ReturnParam();
                        reParam.setFlag("7");
            		} else {
            			reParam = new ReturnParam();
                        reParam.setFlag("4");
            		}
            	}
            } else if(null == model.getHouseNum() && null != model.getBuilding()) {
            	model.setBuilding(null);
        		reParam = mapper.getStdAddr1(model);
        		if(null != reParam) {
        			reParam = new ReturnParam();
                    reParam.setFlag("7");
        		} else {
        			reParam = new ReturnParam();
                    reParam.setFlag("4");
        		}
            } else if(null != model.getHouseNum() && null == model.getBuilding()) {
            	model.setHouseNum(null);
        		reParam = mapper.getStdAddr1(model);
        		if(null != reParam) {
        			reParam = new ReturnParam();
                    reParam.setFlag("6");
        		} else {
        			reParam = new ReturnParam();
                    reParam.setFlag("4");
        		}
            }
            return reParam;
        }
        reParam.setFlag("1");
        if (null == model.getHouseNum()) {

            reParam.setHouseNo(null);
            if (null != reParam.getAddrCode()) {
                reParam.setAddrCode(reParam.getAddrCode().replace(
                        reParam.getAddrCode().substring(19, reParam.getAddrCode().length()), "0000"));
            }
        }

        if (null == model.getBuilding()) {
            reParam.setBuilding(null);
            if (null != reParam.getAddrCode()) {
                reParam.setAddrCode(reParam.getAddrCode().replace(
                        reParam.getAddrCode().substring(15, reParam.getAddrCode().length()), "00000000"));
            }
        }
        return reParam;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdService bean = context.getBean(StdService.class);
        bean.analysis("武东路198");
    }

    public List<PoiDetail> getResidencePoiDetailList(String residenceAddr, String r, String categoryName) {
        // 小区经纬度
        ResidenceInfo residenceDetail = mapper.getResidenceLonAndLat(residenceAddr);
        if (null == residenceDetail) {
            return null;
        }
        Double lon = residenceDetail.getLon();
        Double lat = residenceDetail.getLat();
        if (null == lon && null == lat) {
            lon = 121.4751453;
            lat = 31.26517581;
        }
        List<PoiDetail> poiList = mapper.gePoiDetailList(lon, lat, r,categoryName);
        if(poiList == null){
            PoiDetail poiDetail = new PoiDetail();
            poiDetail.setBaiduLat(31.378998);
            poiDetail.setBaiduLon(121.544417);
            poiDetail.setPoiKind("交通");
            poiDetail.setCategoryName("高速服务区");
            poiDetail.setPoiName("服务区");
            poiDetail.setPoiAddress("江东路861弄1号");
            poiDetail.setDistance(Double.valueOf(300));

            PoiDetail poiDetail1 = new PoiDetail();
            poiDetail1.setBaiduLat(31.122117);
            poiDetail1.setBaiduLon(121.265604);
            poiDetail1.setPoiKind("交通");
            poiDetail1.setCategoryName("车站");
            poiDetail1.setPoiName("泗泾汽车站");
            poiDetail1.setPoiAddress("泗宝路");
            poiDetail1.setDistance(Double.valueOf(200));
            poiList.add(poiDetail);
            poiList.add(poiDetail1);
        }

        return poiList;

    }
    
    public List<PriceTrend> getResidenceTradeAvgPriceList(Integer residenceId) {
        // month为null 返回一年的数据, 为null返回month月份的数据
        String startMonth = CommUitl.getLastYearMonth(CommUitl.getLastMonth(SD.format(new Date())));
        String endMonth = CommUitl.getLastMonth(SD.format(new Date()));
        List<PriceTrend> priceTrends = mapper.getResidenceTradeAvgPriceList(residenceId,
                startMonth, endMonth);
        if (null == priceTrends || priceTrends.size() == 0) {
            return null;
        }
        return priceTrends;
    }

    public Integer getScore(String roadLane, String categoryName) {
        ResidenceInfo residenceDetail = mapper.getResidenceLonAndLat(roadLane);
        Integer residenceId = residenceDetail.getResidenceId();
        String facilityScore = mapper.getFacilityScore(residenceId, categoryName);
        if (facilityScore == null){
            if("交通".equals(categoryName)){
                return 3;
            } else if("教育".equals(categoryName)){
                return 2;
            } else if("购物".equals(categoryName)){
                return 4;
            } else if("医疗".equals(categoryName)) {
                return 1;
            } else {
                return 5;
            }
        }
        double result = (double)Integer.valueOf(facilityScore)/20;
        Integer score = (int)Math.rint(result);
        return score;
    }
}
