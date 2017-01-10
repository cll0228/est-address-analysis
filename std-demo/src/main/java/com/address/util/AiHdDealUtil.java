package com.address.util;

import com.address.mapper.StdMapper;
import com.address.model.HouseDeal;
import com.address.model.ReturnParam;
import com.address.model.StdModel;
import com.address.service.AiHdService;
import com.address.service.StdService;
import com.mysql.jdbc.Connection;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Cuill on 2016/12/15. 未能匹配标准地址的交易案例 匹配标准地址解析程序
 */
public class AiHdDealUtil {
	public void multiThreadImport( final int ThreadNum){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        StdMapper stdMapper = context.getBean(StdMapper.class);
        StdService stdService = context.getBean(StdService.class);
        final CountDownLatch cdl= new CountDownLatch(ThreadNum);
        List<HouseDeal> dealList = stdMapper.selectAiHdNoChai();
        long starttime=System.currentTimeMillis();
        for (HouseDeal deal : dealList) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i=1;i<=80000/ThreadNum;i++){
                        	
                            if (null == dealList)
                                return;
                            
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
                                stdMapper.updateAiHd(result);
                                result.setAnalyAddr("aihd_1400");
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
                        cdl.countDown();
                    } catch (Exception e) {
                    	
                    }
                }
            }).start();
        }
        try {
            cdl.await();
            long spendtime=System.currentTimeMillis()-starttime;
            System.out.println( ThreadNum+"个线程花费时间:"+spendtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

	
    public static void main(String[] args) {
    	AiHdDealUtil ti=new AiHdDealUtil();
        ti.multiThreadImport(16);
        /*ti.multiThreadImport(5);
        ti.multiThreadImport(8);
        ti.multiThreadImport(10);
        ti.multiThreadImport(20);
        ti.multiThreadImport(40);*/
        System.out.println("笔记本CPU数:"+Runtime.getRuntime().availableProcessors());
    }
}
