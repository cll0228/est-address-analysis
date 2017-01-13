package com.address.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Cuill on 2016/8/18.
 */
public class CommUitl {
//    static SimpleDateFormat SD = new SimpleDateFormat("yyyyMM");
    
	static SimpleDateFormat SD = new SimpleDateFormat("yyyy-MM-dd");
	
    static SimpleDateFormat SD2 = new SimpleDateFormat("yyyy-MM");

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static String getLastMonth(String month) {
        SD.setLenient(false);
        if (null == month) {
            return null;
        }
        Date data = null;
        try {
            data = SD.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, -1);
        return SD.format(calendar.getTime());
    }

    public static String getLastYearMonth(String month) {
        SD.setLenient(false);
        if (null == month) {
            return null;
        }
        Date data = null;
        try {
            data = SD.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, -11);
        return SD.format(calendar.getTime());
    }
    
    public static String getLastMonth2(String month) {
        SD2.setLenient(false);
        if (null == month) {
            return null;
        }
        Date data = null;
        try {
            data = SD2.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, -1);
        return SD2.format(calendar.getTime());
    }

    public static String getLastYearMonth2(String month) {
        SD2.setLenient(false);
        if (null == month) {
            return null;
        }
        Date data = null;
        try {
            data = SD2.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, -11);
        return SD2.format(calendar.getTime());
    }

    public static boolean isNull(Object str) {
        if (null == str || "".equals(str)) {
            return true;
        } else
            return false;
    }

    public static boolean isNotNull(Object str) {
        if (null != str && !"".equals(str)) {
            return true;
        } else
            return false;
    }

    public static boolean isNullList(List<?> list) {
        if (null == list || list.size() == 0) {
            return true;
        } else
            return false;
    }

    public static boolean islon(Double d) {
        String str = String.valueOf(d);
        String regex = "^[0-9]{3}.[0-9]{0,6}$";
        boolean matches = str.matches(regex);
        if (matches) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean islat(Double d) {
        String str = String.valueOf(d);
        String regex = "^[0-9]{2}.[0-9]{0,6}$";
        boolean matches = str.matches(regex);
        if (matches) {
            return true;
        } else {
            return false;
        }
    }

    public static int getMonths(String date1, String date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(sdf.parse(date1));

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(sdf.parse(date2));

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
                flag = 1;

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12
                        + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static synchronized int daysBetween(String smdate, String bdate) throws ParseException {
        sdf.setLenient(false);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) throws ParseException {
        int months = getMonths("2010-01-02", "2010-04-03");
        System.out.println(months);
        int monthSpace = daysBetween("2014-06", "2015-4-09");
        System.out.println(monthSpace);
    }
    
}
