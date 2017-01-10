package com.address.util;

import com.address.model.LianjiaResidenceInfo;
import com.address.service.TaskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

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

        Pattern pattern = Pattern.compile("[0-9]$");
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        Set<LianjiaResidenceInfo> infos1 = new HashSet<>();
        List<LianjiaResidenceInfo> infos = new ArrayList<>();
        for (LianjiaResidenceInfo info : list) {

            String address = info.getAddress();
            if (address.contains("弄路")) {
                buffer1.append(address + ",");
                continue;
            }
            if (address.contains("号") && address.contains("弄")) {
                if (address.contains(",")) {
                    String[] split = address.split(",");
                    for (String addr : split) {
                        List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(addr);
                        if (count.size() > 1) {
                            for (LianjiaResidenceInfo residenceInfo : count) {
                                infos.add(residenceInfo);
                            }
                        }
                    }
                } else {
                    String before = StringUtils.substringBefore(address, "弄");
                    if(!pattern.matcher(before).find()){
                        buffer1.append(address + ",");
                        continue;
                    }
                    List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(before + "弄");
                    if (count.size() > 1) {
                        for (LianjiaResidenceInfo residenceInfo : count) {
                            infos.add(residenceInfo);
                        }
                    }
                }
            }

            if (address.contains("村")) {
                if (address.contains(",")) {
                    String[] split = address.split(",");
                    for (String addr : split) {
                        List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(addr);
                        if (count.size() > 1) {
                            for (LianjiaResidenceInfo residenceInfo : count) {
                                infos.add(residenceInfo);
                            }
                        }
                    }
                } else {
                    String before = StringUtils.substringBefore(address, "村");
                    if ("新".equals(before) && !address.contains("弄")) {
                        buffer1.append(address + ",");
                        continue;
                    }
                    List<LianjiaResidenceInfo> count = taskService.selcetResidenceByAddr(address);
                    if (count.size() > 1) {
                        for (LianjiaResidenceInfo residenceInfo : count) {
                            infos.add(residenceInfo);
                        }
                    }
                }
            }
        }
        infos1.addAll(infos);
        for (LianjiaResidenceInfo info : infos1) {
            buffer.append(info.getResidenceId() + "\t" + info.getResidenceName() + "\t" + info.getAddress()
                    + "\n");
        }
        System.out.println(buffer.toString());
        try {
            FileUtils.writeStringToFile(new File("MangRoadResidence.excel"), buffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(infos1.size());
        System.out.println(buffer1.toString());
    }
}
