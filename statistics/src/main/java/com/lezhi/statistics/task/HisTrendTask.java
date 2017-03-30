package com.lezhi.statistics.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import com.lezhi.statistics.pojo.HisTrendInfo;
import com.lezhi.statistics.pojo.Log;

/**
 * Created by Cuill on 2017/3/28.
 */
@SuppressWarnings("all")
public class HisTrendTask {

    static DataPlatformMapper mapper;

    static Calendar calendar = Calendar.getInstance();

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

    static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/conf/applicationContext.xml");
        context.start();
        mapper = context.getBean(DataPlatformMapper.class);
    }

    public static void main(String[] args) throws ParseException {
        // 当前计算开始时间
        //Long startTime = format.parse(reduce1Hour(format.format(new Date()), -1)).getTime() / 1000;// 秒单位
        Long startTime =1489420800L;
        // 当前计算结束时间
//        Long endTime = format.parse(format.format(new Date())).getTime() / 1000;
        Long endTime = 1489424400L;
        List<Log> logs = mapper.selectLog(startTime,endTime);
        if(null == logs) return;
        Map<String, HisTrendInfo> map = new HashedMap();
        Map<String, HisTrendInfo> district_map = new HashedMap();
        Map<String, HisTrendInfo> block_map = new HashedMap();
        Map<String, HisTrendInfo> resi_map = new HashedMap();
        int count = 0;
        for (Log log : logs) {
            if(log.getMac().equals("00196843A7A3")){
                System.out.println("正在处理log="+log.getMac());
            }
            HisTrendInfo task = new HisTrendInfo();
            // 查询两小时内是否有session相同
            List<Log> before = mapper.selectLogBySession(log,startTime,endTime);
            if (null == before || before.size() == 0) {
                // 之前无记录，查询后面
                continue;
            } else {
                System.out.println("当前mac="+log.getMac()+"在该时间段前有记录，正在处理。。");
                task.setChannelNo(log.getChannel());
                task.setUv(mapper.selectUv(log,startTime,endTime));
                task.setNv(mapper.selectNv(log,startTime,endTime));
                task.setPv(null == mapper.selectPv(log,startTime,endTime) ? 0 : mapper.selectPv(log,startTime,endTime).size());
                task.setDistrictId(mapper.selectDistrictId(log));
                task.setBlockId(mapper.selectBlockId(log));
                task.setResidenceId(mapper.selectResidenceId(log));
                task.setTimeBegin(startTime);
                task.setTimeEnd(endTime);
                // 查找当前mac session 在此时间段内
                List<Log> same = mapper.selectTimeBySameMacAndSession(log, startTime);
                if (null == same || same.size() == 0) {
                    // 访问时长从当前时间点算起
                    System.out.println("未找到当前数据在该时间段内同一session记录"+log.getSession());
                    task.setTotalTop(log.getTimeStamp().getTime() / 1000 - startTime);
                } else
                    task.setTotalTop(log.getTimeStamp().getTime() / 1000
                            - same.get(0).getTimeStamp().getTime() / 1000);


                //保存区县的记录
                HisTrendInfo his_district = district_map.get(log.getChannel() + task.getDistrictId());
                if(null != his_district){
                    task.setTotalTop(his_district.getTotalTop()+task.getTotalTop());
                }
                district_map.put(log.getChannel().toString()+task.getDistrictId(),task);

                //保存板块的信息记录
                HisTrendInfo his_block = block_map.get(log.getChannel() + task.getBlockId());
                if(null != his_block){
                    task.setTotalTop(his_block.getTotalTop()+task.getTotalTop());
                }
                block_map.put(log.getChannel().toString()+task.getBlockId(),task);

                //保存小区的信息记录
                HisTrendInfo his_resi = resi_map.get(log.getChannel() + task.getResidenceId());
                if(null != his_resi){
                    task.setTotalTop(his_resi.getTotalTop()+task.getTotalTop());
                }
                resi_map.put(log.getChannel().toString()+task.getResidenceId(),task);

            }
        }
        //保存信息
        saveDisInfo(district_map);
        saveblockInfo(block_map);
        saveResiInfo(resi_map);
    }

    private static void saveResiInfo(Map<String, HisTrendInfo> resi_map) {
        for(String key :resi_map.keySet()){
            mapper.saveDis(resi_map.get(key),"residence");
        }
    }

    private static void saveblockInfo(Map<String, HisTrendInfo> block_map) {
        for(String key :block_map.keySet()){
            mapper.saveDis(block_map.get(key),"block");
        }
    }

    private static void saveDisInfo(Map<String, HisTrendInfo> district_map) {
        for(String key :district_map.keySet()){
            mapper.saveDis(district_map.get(key),"district");
        }
    }

    public static String reduce1Hour(String time, Integer hour) throws ParseException {
        Date parse = format1.parse(time);
        calendar.setTime(parse);
        calendar.add(calendar.HOUR_OF_DAY, hour);
        return format1.format(calendar.getTime());
    }

}
