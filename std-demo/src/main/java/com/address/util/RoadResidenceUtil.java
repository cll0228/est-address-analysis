package com.address.util;

import com.address.model.LianjiaResidenceInfo;
import com.address.service.TaskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Cuill on 2017/1/4. 统计链家一个路弄对应多个小区
 */
public class RoadResidenceUtil {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext*.xml");
        context.start();
        TaskService taskService = context.getBean(TaskService.class);
        List<LianjiaResidenceInfo> list = taskService.getLianjiaResidenceList();

        StringBuffer buffer = new StringBuffer();
        for (LianjiaResidenceInfo info : list) {

            String address = info.getAddress();
            if (address.contains("号") && address.contains("弄")) {
                continue;
                // String before = StringUtils.substringBefore(address, "弄");
                // List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(before+"弄");
                // if(count.size()>1){
                // buffer.append(info.getResidenceId()+","+info.getResidenceName()+","+info.getAddress()+"\n");
                // }
            }

            if (address.contains("村")) {
                if (address.contains(",")) {
                    String[] split = address.split(",");
                    for (String addr : split) {
                        List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(addr);
                        if (count.size() > 1) {
                            buffer.append(info.getResidenceId() + "," + info.getResidenceName() + ","
                                    + info.getAddress() + "\n");
                        }
                    }
                } else {
                    String before = StringUtils.substringBefore(address, "村");
                    List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(before + "村");
                    if (count.size() > 1) {
                        buffer.append(info.getResidenceId() + "," + info.getResidenceName() + ","
                                + info.getAddress() + "\n");
                    }
                }
            }
        }
        System.out.println(buffer.toString());
        try {
            FileUtils.writeStringToFile(new File("MangRoadResidence.excel"), buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
