package com.of.tests;

import com.lezhi.statistics.mapper.MacMapper;
import com.lezhi.statistics.pojo.MacInfoObj;
import org.apache.commons.io.FileUtils;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Colin Yan on 2017/3/28.
 */
public class LogGenerator2 {

    public static void main(String[] args) throws IOException {

        ApplicationContext context = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
        MacMapper macMapper = context.getBean(MacMapper.class);

        List<MacInfoObj> list = macMapper.getMacInfoList(null, null, 0, 10);

        // 取100个mac，包含区域信息

        // 每隔3秒到1分钟，写入一条数据, create_time
        // 频道，随机

        long end = System.currentTimeMillis();
        long begin = end - (30L * 24L * 60L * 60L * 1000L);

        int i = 1;

        StringBuilder sb = new StringBuilder();

        while (begin < end) {

            long nextTime = new Random().nextInt(2 * 60 * 1000);

            MacInfoObj mac = list.get(new Random().nextInt(list.size()));

            Session ss = nextSession(mac.getMac(), nextTime);

            int channelNo = new Random().nextInt(3) + 997;

            sb.append(
                    (i) + "\t" +
                    mac.getMac() + "\t" +
                            mac.getDistrictId() + "\t" +
                            mac.getBlockId() + "\t" +
                            mac.getResidenceId() + "\t" +
                            channelNo + "\t" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(begin)) + "\t" +
                    ss.getUuid()).append("\n");
            begin += nextTime;
            i++;
        }

        FileUtils.write(new File("111.txt"), sb.toString(), "utf-8");
        //session 生成
    }

    private static synchronized Session nextSession(String mac, long mssec) {
        if (sessionMap.containsKey(mac)) {
            if (mssec < (30 * 60 * 1000) && new Random().nextInt() % 2 == 0) {
                Session ss = sessionMap.get(mac);
                ss.setCount(ss.getCount() + 1);
                return ss;
            } else {
                Session ss = Session.newInstant();
                sessionMap.put(mac, ss);
                return ss;
            }
        } else {
            Session ss = Session.newInstant();
            sessionMap.put(mac, ss);
            return ss;
        }
    }

    private static Map<String, Session> sessionMap = new HashMap<>();

    static class Session {

        public static Session newInstant() {
            Session ss = new Session();
            ss.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
            ss.setCount(1);
            return ss;
        }

        private String uuid;

        private int count;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
