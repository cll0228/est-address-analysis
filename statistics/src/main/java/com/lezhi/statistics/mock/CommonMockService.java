package com.lezhi.statistics.mock;

import com.lezhi.statistics.mock.model.MacVisitChannelHistoryInfo;
import com.lezhi.statistics.pojo.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Colin Yan on 2017/5/25.
 */
@Service
public class CommonMockService {
    static String[] disrticts = {"310101","310102","310103","310104","310105","310106"};
    static String[] blocks = {"1","3","5","38","43","55","108"};
    static String[] residences = {"25","52","60","62","72","112"};
    static String[] macs = {"00196843AC57","00196843AC75","00196843A637","00196843AD2F","00196843AD5B","0019684375FB","00196843ADBD"};
    static String[] channels = {"993","998","999","994","995","996","997"};

    public MacVisit getMacVisitChannelHistoryInfo() {
        MacVisit obj = new MacVisit();
        List<MacVisitHistoryInfo> pagedList = new ArrayList<>();
        for(int i=0;i<macs.length;i++){
            int pv = new Random().nextInt(1000) + 1;
            int top = new Random().nextInt(500) + 1;
            MacVisitHistoryInfo macVisitHistoryInfo = new MacVisitHistoryInfo();
            macVisitHistoryInfo.setMac(macs[i]);
            String channelNo = getRandomString(channels);
            macVisitHistoryInfo.setChannelNo(Integer.valueOf(channelNo));
            macVisitHistoryInfo.setPv(pv);
            macVisitHistoryInfo.setFirstVisitTime(Long.valueOf("1495641600000"));
            macVisitHistoryInfo.setLastVisitTime(Long.valueOf("1495727999000"));
            macVisitHistoryInfo.setTotalTop((long) top);
            pagedList.add(macVisitHistoryInfo);
        }
        obj.setHistories(pagedList);
        obj.setStatus("success");
        obj.setErrMsg("");
        obj.setIsFirstPage(true);
        obj.setTotalPageCount(1);
        obj.setTotalCount(pagedList.size());
        obj.setIsLastPage(true);
        obj.setPageNo(1);
        obj.setPageSize(1);
        obj.setRealPageSize(pagedList.size());
        return obj;
    }

    public Summary getChannelVisitSummaryInfo() {
        Summary obj = new Summary();
        List<ChannelSummaryObj> summaryObjs = new ArrayList<>();
        for(int i=0;i<channels.length;i++) {
            int uv = new Random().nextInt(1000) + 1;
            int pv = new Random().nextInt(500) + 1;
            int nv = new Random().nextInt(100) + 1;
            ChannelSummaryObj channelSummaryObj = new ChannelSummaryObj();
            channelSummaryObj.setChannelNo(Integer.valueOf(channels[i]));
            channelSummaryObj.setPv(pv);
            channelSummaryObj.setNv(nv);
            channelSummaryObj.setUv(uv);
            summaryObjs.add(channelSummaryObj);
        }
        obj.setChannelSummaries(summaryObjs);
        obj.setStatus("success");
        obj.setErrMsg("");
        obj.setIsFirstPage(true);
        obj.setTotalPageCount(1);
        obj.setTotalCount(summaryObjs.size());
        obj.setIsLastPage(true);
        obj.setPageNo(1);
        obj.setPageSize(1);
        obj.setRealPageSize(summaryObjs.size());
        return obj;
    }

    public MacVisit getMacVisitLogInfo() {
        MacVisit obj = new MacVisit();
        List<MacVisitLogInfo> macInfoList = new ArrayList<>();
        for(int i=0;i<macs.length;i++) {
            int uv = new Random().nextInt(1000) + 1;
            int pv = new Random().nextInt(500) + 1;
            int nv = new Random().nextInt(100) + 1;
            MacVisitLogInfo macVisitLogInfo = new MacVisitLogInfo();
            String channel = getRandomString(channels);
            macVisitLogInfo.setMac(macs[i]);
            macVisitLogInfo.setChannelNo(channel);
            macVisitLogInfo.setDistrictId(Integer.valueOf(getRandomString(disrticts)));
            macVisitLogInfo.setDistrictName("");
            macVisitLogInfo.setBlockId(Integer.valueOf(getRandomString(blocks)));
            macVisitLogInfo.setBlockName("");
            macVisitLogInfo.setResidenceId(Integer.valueOf(getRandomString(residences)));
            macVisitLogInfo.setResidenceName("");
            macVisitLogInfo.setTime(System.currentTimeMillis());
            macInfoList.add(macVisitLogInfo);
        }
        obj.setHistories(macInfoList);
        obj.setStatus("success");
        obj.setErrMsg("");
        obj.setIsFirstPage(true);
        obj.setTotalPageCount(1);
        obj.setTotalCount(macInfoList.size());
        obj.setIsLastPage(true);
        obj.setPageNo(1);
        obj.setPageSize(1);
        obj.setRealPageSize(macInfoList.size());
        return obj;
    }

    public String getRandomString(String[] args){
        int len = args.length;//获取数组长度给变量len
        Random random = new Random();//创建随机对象
        int arrIdx = random.nextInt(len-1);//随机数组索引，nextInt(len-1)表示随机整数[0,(len-1)]之间的值
        return args[arrIdx];//获取数组值
    }
}
