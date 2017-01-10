package com.address.util;

import com.address.mapper.TaskMapper;
import com.address.model.OfResidence;
import com.address.service.TaskService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Cuill on 2017/1/9. 统计of_residence 楼栋和房屋数量
 */
public class CountBuildingAndHouseUitl {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/conf/applicationContext.xml");
        context.start();
        TaskMapper mapper = context.getBean(TaskMapper.class);
        List<OfResidence> residences = mapper.selcetOfResidence();
        Integer updateBuildingCount = 0;
        Integer updateHouseCount = 0;
        for (OfResidence residence : residences) {
            // 查询楼栋数量
            Integer buildingCount = mapper.selectBuildingCount(residence.getId());
            if (null != buildingCount && null != residence.getBuildingCount()
                    && residence.getBuildingCount() != buildingCount) {
                // 更新楼栋数据
                mapper.updateOfResidenceBuildingCount(buildingCount, residence.getId());
                updateBuildingCount++;
                System.out.println();
            }
            Integer houseCount = mapper.selectHouseCount(residence.getId());
            if (null != houseCount && null != residence.getHouseCount()
                    && residence.getHouseCount() != houseCount) {
                // 更新房屋数据
                mapper.updateOfResidenceHouseCount(houseCount, residence.getId());
                updateHouseCount++;
            }
        }
        System.out.println("更新楼栋数量 = " + updateBuildingCount);
        System.out.println("更新房屋数量 = " + updateHouseCount);
    }
}
