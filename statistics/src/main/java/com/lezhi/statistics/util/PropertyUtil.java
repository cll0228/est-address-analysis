package com.lezhi.statistics.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Created by wangyh on 2017/3/28.
 */
public class PropertyUtil {
    public static String getProperty(String type, String key) {
        InputStream is = null;
        Properties dbProps = new Properties();
        try {
            is = PropertyUtil.class.getClassLoader().getResourceAsStream(type + ".properties");
            dbProps.load(is);
            return dbProps.getProperty(key);
        } catch (Exception e) {
            return null;
        } finally {
            if (null != is) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getStartHour(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        date = calendar.getTime();  
        return sm.format(date);  
    }
    
    public static String getEndHour(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, 0);  
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:59:59");
        date = calendar.getTime();  
        return sm.format(date);  
    }

    public static String getBeforeHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        date = calendar.getTime();
        return sm.format(date);
    }
    
    public static String getBeforeDay(Date date) {  
    	Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd 23:00:00");
        date = calendar.getTime();  
        return sm.format(date);  
    }

    public static String getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        date = calendar.getTime();
        return sm.format(date);
    }

    public static String getStartMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        date = calendar.getTime();
        return sm.format(date);
    }

    public static String getEndMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1);
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:59");
        date = calendar.getTime();
        return sm.format(date);
    }
    
    public static void main(String[] args) {
    	
    	System.out.println(getBeforeDay(new Date()));
    }
}
