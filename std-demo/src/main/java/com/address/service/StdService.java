package com.address.service;

import com.address.mapper.StdMapper;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.util.AddressExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by Cuill on 2016/12/12.
 */
@Service
public class StdService {

    @Autowired
    private StdMapper mapper;

    public ReturnParam analysis(String address) {
        ReturnParam reParam = new ReturnParam();
        StdModel model = AddressExtractor.parseAll(new StdModel(address));
        if (model.getResidence() == null && model.getRoad() == null) {
            reParam.setFlag("3");
            return reParam;// 解析失败
        }
        reParam.setFlag("1");

        // 判断区县是否对应
        if (model.getRoad() != null && model.getDistrict() != null) {
            String districtName = mapper.getDistrictByRoad(model.getRoad());
            if (null == districtName) {
                reParam.setFlag("5");
                return reParam;
            }
            if (null != districtName && !districtName.equals(model.getDistrict())) {
                reParam.setFlag("2");// 区县不对应
                return reParam;
            }
        }

        // 小区名称
        if (null != model.getResidence() && model.getRoad() == null) {
            reParam = mapper.getStdAddr(model.getResidence());
            if (null == model) {
                reParam = new ReturnParam();
                reParam.setFlag("4");
            } else {
                reParam.setFlag("1");
            }
            if (null == model.getHouseNum()) {
                reParam.setHouseNo(null);
            }
            if (null == model.getBuilding()) {
                reParam.setBuilding(null);
            }
            return reParam;
        }

        // 路弄
        if (null != model.getRoad() || model.getLane() != null) {
            reParam = mapper.getStdAddr1(model);
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

            if (null == model.getHouseNum()) {
                reParam.setHouseNo(null);
            }

            if (null == model.getBuilding()) {
                reParam.setBuilding(null);
            }
            return reParam;
        }
        return null;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdService bean = context.getBean(StdService.class);
        bean.analysis("文化花园");
    }
}
