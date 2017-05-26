package com.lezhi.statistics.mock;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.DateUtil;

/**
 * Created by Colin Yan on 2017/5/25.
 */
@Service
public class CommonMockService {

    public static void main(String[] args) {
        new CommonMockService().getRealTime();
    }

    public MacVisit getMacVisitChannelHistoryInfo() {
        // TODO
        return null;
    }

    public RealTimeSummary getRealTime() {
        RealTimeSummary summary = new RealTimeSummary();
        List<RealTimeSummaryObj> realTimeSummaryObjs = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            RealTimeSummaryObj obj = new RealTimeSummaryObj();
            obj.setNv(i * (new Random().nextInt(10)));
            obj.setPv((i + 2) * (new Random().nextInt(20)));
            obj.setUv(i * (new Random().nextInt(15)));
            try {
                obj.setRefreshTime(DateUtil.format2
                        .parse(DateUtil.update1Min(DateUtil.format2.format(new Date()), -i)).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println(DateUtil.format1.format(new Date(obj.getRefreshTime())));
            realTimeSummaryObjs.add(obj);
        }
        summary.setRealtimeSummary(realTimeSummaryObjs);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public Trend trend() {
        Trend trend = new Trend();
        List<TrendObj> trendObjs = new ArrayList<>();
        TrendObj tmp = null;
        for (int i = 1; i <= 60; i++) {
            TrendObj t = new HisTrendInfo();
            t.setAvgTop(Long.valueOf(i * new Random().nextInt()));
            t.setNv(i * (new Random().nextInt(10)));
            t.setPv((i + 2) * (new Random().nextInt(20)));
            t.setUv(i * (new Random().nextInt(15)));
            String format = DateUtil.format2.format(new Date());// 当前时间整分钟
            try {
                if (null == tmp) {
                    tmp = new TrendObj();
                    t.setTimeEnd(DateUtil.format1.parse(DateUtil.reduce1Second(format)).getTime());
                    t.setTimeBegin(DateUtil.format1.parse(DateUtil.update1Min(format, -i)).getTime());
                } else {
                    t.setTimeBegin(tmp.getTimeBegin() - 60 * 1000);
                    t.setTimeEnd(tmp.getTimeBegin() - 1000);
                }
                tmp = t;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            trendObjs.add(t);
        }

        trend.setStatus("success");
        trend.setTrend(trendObjs);
        return trend;
    }
}
