package com.address.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.address.mapper.StdMapper;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.util.AddressExtractor;

/**
 * Created by Cuill on 2016/12/12.
 */
@Service
public class AiHdService {

    @Autowired
    private StdMapper mapper;

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

        if (model.getRoad() != null && model.getDistrict() != null) {
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
        }

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
                model.setRoad(model.getResidence());
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
            reParam = new ReturnParam();
            reParam.setFlag("4");
            if (null != model.getHouseNum() && null != model.getBuilding()) {
                reParam.setFlag("6");
            }

            if (null != model.getBuilding() && null == model.getHouseNum()) {
                reParam.setFlag("7");
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
        AiHdService bean = context.getBean(AiHdService.class);
        bean.analysis("武东路198");
    }
}
