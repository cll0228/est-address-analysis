package com.address.util;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.address.mapper.StdMapper;
import com.address.model.OfHouse;

/**
 * Created by Cuill on 2016/12/15. 未能匹配标准地址的交易案例 匹配标准地址解析程序
 */
public class InsertOfHouse {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdMapper stdMapper = context.getBean(StdMapper.class);
        List<OfHouse> resultList = stdMapper.selectOfHouse();
        if (null == resultList)
            return;

        for (OfHouse ofHouse : resultList) {
        	
            stdMapper.insertOfHouse(ofHouse);
            
        }
    }
}
