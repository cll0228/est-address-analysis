package com.lezhi.statistics.util;

import com.lezhi.statistics.mapper.DataPlatformMapper;
import org.apache.commons.collections.map.HashedMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cuill on 2017/3/27.
 */
public class DateUtil {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

    static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    public static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

    public static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static Calendar calendar = Calendar.getInstance();

    static DataPlatformMapper mapper;


    public static void main(String[] args) throws Exception {
    }

    public static void his_trend_day()throws Exception{
        for (int i = 0; i < 360; i++) {
            Map<String, Object> map = new HashedMap();
            map.put("channelNo", 999);
            map.put("beginTime", updateDay(new Date(), i));
            map.put("endTime", reduce1Second(updateDay(format1.parse(map.get("beginTime").toString()), 1)));
            map.put("uv", (i + 2) * Math.random() + 10);
            map.put("pv", (i + 1) * Math.random() + 5);
            map.put("nv", 1);
            map.put("avgTop", (i + 1) * Math.random() * 10);
            map.put("districtId", 310106);
            mapper.insert_trend_day(map, "district");
        }
    }

    // 时间减去一秒
    public static String reduce1Second(String time) throws ParseException {
        Date parse = format1.parse(time);
        calendar.setTime(parse);
        calendar.add(calendar.SECOND, -1);
        return format1.format(calendar.getTime());
    }

    public static String reduce1Hour(String time, Integer hour) throws ParseException {
        Date parse = format1.parse(time);
        calendar.setTime(parse);
        calendar.add(calendar.HOUR_OF_DAY, hour);
        return format1.format(calendar.getTime());
    }

    //减去一分钟
    public static String update1Min(String time, Integer min) throws ParseException {
        Date parse = format1.parse(time);
        calendar.setTime(parse);
        calendar.add(calendar.MINUTE, min);
        return format1.format(calendar.getTime());
    }

    public static String updateDay(Date time, Integer day) throws ParseException {
        calendar.setTime(time);
        calendar.add(calendar.DAY_OF_YEAR, day);
        return format1.format(calendar.getTime());
    }
}
