package com.address.util;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.address.mapper.StdMapper;
import com.address.model.HouseDeal;
import com.address.model.OfHouse;

/**
 * Created by Cuill on 2016/12/15. 未能匹配标准地址的交易案例 匹配标准地址解析程序
 */
public class InsertHouseDeal {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdMapper stdMapper = context.getBean(StdMapper.class);
        List<HouseDeal> resultList = stdMapper.selectHouseDeal();
        if (null == resultList)
            return;

        for (HouseDeal houseDeal : resultList) {
        	houseDeal.setAnalyAddr("house_deal");
        	try {
				if(null!=houseDeal.getId()&&null!=houseDeal.getStdAddrId()) {
					stdMapper.insertOuterAddress(houseDeal);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
    }
}
