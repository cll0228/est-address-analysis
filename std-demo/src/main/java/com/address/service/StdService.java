package com.address.service;

import com.address.mapper.StdMapper;
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

    public StdModel analysis(String address) {
        StdModel model = AddressExtractor.parseAll(new StdModel(address));
        if (model.getResidence() == null && model.getRoad() == null) {
            model.setFlag("3");
            return model;// 解析失败
        }
        model.setFlag("1");

        // 判断区县是否对应
        if (model.getRoad() != null && model.getDistrict() != null) {
            String districtName = mapper.getDistrictByRoad(model.getRoad());
            if (null != districtName && !districtName.equals(model.getDistrict())) {
                model.setFlag("2");// 区县不对应
                return model;
            }
        }

        // 小区名称
        if (null != model.getResidence() && model.getRoad() == null) {
            model = mapper.getStdAddr(model.getResidence());
            if (null == model) {
                model = new StdModel();
                model.setFlag("4");
                System.out.println(model.toString());
            }
            return model;
        }

        // 路弄
        if (null != model.getRoad() || model.getLane() != null) {
            model = mapper.getStdAddr1(model);
            if (null == model) {
                model = new StdModel();
                model.setFlag("4");
                System.out.println(model);
            }
            return model;
        }
        return null;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdService bean = context.getBean(StdService.class);
         bean.analysis("杨浦区大康路859弄");
    }
}
