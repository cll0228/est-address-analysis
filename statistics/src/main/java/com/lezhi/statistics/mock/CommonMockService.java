package com.lezhi.statistics.mock;

import com.lezhi.statistics.pojo.*;
import com.lezhi.statistics.util.DateUtil;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

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

    public HistoryListResult<MacVisitHistoryInfo> getMacVisitChannelHistoryInfo() {
        HistoryListResult<MacVisitHistoryInfo> obj = new HistoryListResult<>();
        List<MacVisitHistoryInfo> pagedList = new ArrayList<>();
        for(int i=0;i<macs.length;i++){
            int pv = new Random().nextInt(1000) + 1;
            int top = new Random().nextInt(500) + 1;
            MacVisitHistoryInfo macVisitHistoryInfo = new MacVisitHistoryInfo();
            macVisitHistoryInfo.setMac(macs[i]);
            String channelNo = getRandomString(channels);
            macVisitHistoryInfo.setChannelNo(channelNo);
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

    public SummaryResult getChannelVisitSummaryInfo() {
        SummaryResult obj = new SummaryResult();
        List<ChannelSummaryObj> summaryObjs = new ArrayList<>();
        for(int i=0;i<channels.length;i++) {
            int uv = new Random().nextInt(1000) + 1;
            int pv = new Random().nextInt(500) + 1;
            int nv = new Random().nextInt(100) + 1;
            ChannelSummaryObj channelSummaryObj = new ChannelSummaryObj();
            channelSummaryObj.setChannelNo(channels[i]);
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

    public HistoryListResult<MacVisitLogInfo> getMacVisitLogInfo() {
        HistoryListResult<MacVisitLogInfo> obj = new HistoryListResult<>();
        List<MacVisitLogInfo> macInfoList = new ArrayList<>();
		MacVisitLogInfo macVisitLogInfo = new MacVisitLogInfo();
		macVisitLogInfo.setMac(getRandomString(macs));
		macVisitLogInfo.setChannelNo(getRandomString(channels));
		macVisitLogInfo.setDistrictId(310115);
		macVisitLogInfo.setDistrictName("浦东新区");
		macVisitLogInfo.setBlockId(28);
		macVisitLogInfo.setBlockName("万祥镇");
		macVisitLogInfo.setResidenceId(2);
		macVisitLogInfo.setResidenceName("祥安竹苑");
		macVisitLogInfo.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo);

		MacVisitLogInfo macVisitLogInfo1 = new MacVisitLogInfo();
		macVisitLogInfo1.setMac(getRandomString(macs));
		macVisitLogInfo1.setChannelNo(getRandomString(channels));
		macVisitLogInfo1.setDistrictId(310104);
		macVisitLogInfo1.setDistrictName("徐汇区");
		macVisitLogInfo1.setBlockId(69);
		macVisitLogInfo1.setBlockName("漕河泾");
		macVisitLogInfo1.setResidenceId(9882);
		macVisitLogInfo1.setResidenceName("钦江大楼");
		macVisitLogInfo1.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo1);

		MacVisitLogInfo macVisitLogInfo2 = new MacVisitLogInfo();
		macVisitLogInfo2.setMac(getRandomString(macs));
		macVisitLogInfo2.setChannelNo(getRandomString(channels));
		macVisitLogInfo2.setDistrictId(310104);
		macVisitLogInfo2.setDistrictName("徐汇区");
		macVisitLogInfo2.setBlockId(69);
		macVisitLogInfo2.setBlockName("漕河泾");
		macVisitLogInfo2.setResidenceId(10339);
		macVisitLogInfo2.setResidenceName("虹梅小区");
		macVisitLogInfo2.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo2);

		MacVisitLogInfo macVisitLogInfo3 = new MacVisitLogInfo();
		macVisitLogInfo3.setMac(getRandomString(macs));
		macVisitLogInfo3.setChannelNo(getRandomString(channels));
		macVisitLogInfo3.setDistrictId(310107);
		macVisitLogInfo3.setDistrictName("普陀区");
		macVisitLogInfo3.setBlockId(84);
		macVisitLogInfo3.setBlockName("长风");
		macVisitLogInfo3.setResidenceId(3402);
		macVisitLogInfo3.setResidenceName("梅岭新村");
		macVisitLogInfo3.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo3);

		MacVisitLogInfo macVisitLogInfo4 = new MacVisitLogInfo();
		macVisitLogInfo4.setMac(getRandomString(macs));
		macVisitLogInfo4.setChannelNo(getRandomString(channels));
		macVisitLogInfo4.setDistrictId(310107);
		macVisitLogInfo4.setDistrictName("普陀区");
		macVisitLogInfo4.setBlockId(84);
		macVisitLogInfo4.setBlockName("长风");
		macVisitLogInfo4.setResidenceId(14400);
		macVisitLogInfo4.setResidenceName("爱建新村");
		macVisitLogInfo4.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo4);

		MacVisitLogInfo macVisitLogInfo5 = new MacVisitLogInfo();
		macVisitLogInfo5.setMac(getRandomString(macs));
		macVisitLogInfo5.setChannelNo(getRandomString(channels));
		macVisitLogInfo5.setDistrictId(310107);
		macVisitLogInfo5.setDistrictName("普陀区");
		macVisitLogInfo5.setBlockId(95);
		macVisitLogInfo5.setBlockName("中远两湾城");
		macVisitLogInfo5.setResidenceId(33019);
		macVisitLogInfo5.setResidenceName("中远两湾城");
		macVisitLogInfo5.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo5);

		MacVisitLogInfo macVisitLogInfo6 = new MacVisitLogInfo();
		macVisitLogInfo6.setMac(getRandomString(macs));
		macVisitLogInfo6.setChannelNo(getRandomString(channels));
		macVisitLogInfo6.setDistrictId(310110);
		macVisitLogInfo6.setDistrictName("杨浦区");
		macVisitLogInfo6.setBlockId(97);
		macVisitLogInfo6.setBlockName("东外滩");
		macVisitLogInfo6.setResidenceId(3304);
		macVisitLogInfo6.setResidenceName("惠民新苑");
		macVisitLogInfo6.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo6);

		MacVisitLogInfo macVisitLogInfo7 = new MacVisitLogInfo();
		macVisitLogInfo7.setMac(getRandomString(macs));
		macVisitLogInfo7.setChannelNo(getRandomString(channels));
		macVisitLogInfo7.setDistrictId(310105);
		macVisitLogInfo7.setDistrictName("长宁区");
		macVisitLogInfo7.setBlockId(106);
		macVisitLogInfo7.setBlockName("虹桥");
		macVisitLogInfo7.setResidenceId(1334);
		macVisitLogInfo7.setResidenceName("安顺路273号");
		macVisitLogInfo7.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo7);

		MacVisitLogInfo macVisitLogInfo8 = new MacVisitLogInfo();
		macVisitLogInfo8.setMac(getRandomString(macs));
		macVisitLogInfo8.setChannelNo(getRandomString(channels));
		macVisitLogInfo8.setDistrictId(310101);
		macVisitLogInfo8.setDistrictName("黄浦区");
		macVisitLogInfo8.setBlockId(146);
		macVisitLogInfo8.setBlockName("人民广场");
		macVisitLogInfo8.setResidenceId(1388);
		macVisitLogInfo8.setResidenceName("新昌路61弄");
		macVisitLogInfo8.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo8);

		MacVisitLogInfo macVisitLogInfo9 = new MacVisitLogInfo();
		macVisitLogInfo9.setMac(getRandomString(macs));
		macVisitLogInfo9.setChannelNo(getRandomString(channels));
		macVisitLogInfo9.setDistrictId(310106);
		macVisitLogInfo9.setDistrictName("静安区");
		macVisitLogInfo9.setBlockId(152);
		macVisitLogInfo9.setBlockName("静安寺");
		macVisitLogInfo9.setResidenceId(2367);
		macVisitLogInfo9.setResidenceName("愚园路601号");
		macVisitLogInfo9.setTime(System.currentTimeMillis());
		macInfoList.add(macVisitLogInfo9);
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
        int len = args.length;
        Random random = new Random();
        int arrIdx = random.nextInt(len-1);
        return args[arrIdx];
    }

    public Map<String, Object> getMacInfoByMac() {
    	Map<String, Object> result = new HashMap<>();
    	List<SummaryInfo> summaryInfoList = new ArrayList<SummaryInfo>();
    	SummaryInfo d1 = new SummaryInfo();
    	d1.setAvgTop(88016L);
    	d1.setDistrictId(310109);
    	d1.setDistrictName("虹口区");
    	d1.setLon(121.495910);
    	d1.setLat(31.287834);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	SummaryInfo d2 = new SummaryInfo();
    	d2.setAvgTop(79876L);
    	d2.setDistrictId(310115);
    	d2.setDistrictName("浦东新区");
    	d2.setLon(121.718866);
    	d2.setLat(31.196763);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	SummaryInfo d3 = new SummaryInfo();
    	d3.setAvgTop(87321L);
    	d3.setDistrictId(310110);
    	d3.setDistrictName("杨浦区");
    	d3.setLon(121.517929);
    	d3.setLat(31.309973);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	SummaryInfo d4 = new SummaryInfo();
    	d4.setAvgTop(96231L);
    	d4.setDistrictId(310105);
    	d4.setDistrictName("长宁区");
    	d4.setLon(121.383699);
    	d4.setLat(31.211912);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	SummaryInfo d5 = new SummaryInfo();
    	d5.setAvgTop(69876L);
    	d5.setDistrictId(310107);
    	d5.setDistrictName("普陀区");
    	d5.setLon(121.407677);
    	d5.setLat(31.281754);
    	d5.setNv((int)(Math.random()*90+10));
    	d5.setPv((int)(Math.random()*90+10));
    	d5.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d5);
		// 封装返回对象
		result.put("summaries", summaryInfoList);
		result.put("status", "success");
		result.put("errMsg", "");
        return result;
    }

    public Map<String, Object> getMacInfoByDistrict() {
    	Map<String, Object> result = new HashMap<>();
    	List<SummaryInfo> summaryInfoList = new ArrayList<SummaryInfo>();
    	SummaryInfo d1 = new SummaryInfo();
    	d1.setAvgTop(86769L);
    	d1.setBlockId(96);
    	d1.setBlockName("鞍山");
    	d1.setLon(121.503116);
    	d1.setLat(31.278934);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	SummaryInfo d2 = new SummaryInfo();
    	d2.setAvgTop(89572L);
    	d2.setBlockId(97);
    	d2.setBlockName("东外滩");
    	d2.setLon(121.523816);
    	d2.setLat(31.26058);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	SummaryInfo d3 = new SummaryInfo();
    	d3.setAvgTop(80932L);
    	d3.setBlockId(98);
    	d3.setBlockName("黄兴公园");
    	d3.setLon(121.532974);
    	d3.setLat(31.297323);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	SummaryInfo d4 = new SummaryInfo();
    	d4.setAvgTop(81221L);
    	d4.setBlockId(99);
    	d4.setBlockName("控江路");
    	d4.setLon(121.528034);
    	d4.setLat(31.287506);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	SummaryInfo d5 = new SummaryInfo();
    	d5.setAvgTop(90769L);
    	d5.setBlockId(100);
    	d5.setBlockName("五角场");
    	d5.setLon(121.499755);
    	d5.setLat(31.300934);
    	d5.setNv((int)(Math.random()*90+10));
    	d5.setPv((int)(Math.random()*90+10));
    	d5.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d5);
    	SummaryInfo d6 = new SummaryInfo();
    	d6.setAvgTop(89190L);
    	d6.setBlockId(101);
    	d6.setBlockName("新江湾城");
    	d6.setLon(121.506491);
    	d6.setLat(31.330849);
    	d6.setNv((int)(Math.random()*90+10));
    	d6.setPv((int)(Math.random()*90+10));
    	d6.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d6);
    	SummaryInfo d7 = new SummaryInfo();
    	d7.setAvgTop(85769L);
    	d7.setBlockId(102);
    	d7.setBlockName("周家嘴路");
    	d7.setLon(121.535798);
    	d7.setLat(31.279141);
    	d7.setNv((int)(Math.random()*90+10));
    	d7.setPv((int)(Math.random()*90+10));
    	d7.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d7);
    	SummaryInfo d8 = new SummaryInfo();
    	d8.setAvgTop(77676L);
    	d8.setBlockId(103);
    	d8.setBlockName("中原");
    	d8.setLon(121.531893);
    	d8.setLat(31.322619);
    	d8.setNv((int)(Math.random()*90+10));
    	d8.setPv((int)(Math.random()*90+10));
    	d8.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d8);
		// 封装返回对象
		result.put("summaries", summaryInfoList);
		result.put("status", "success");
		result.put("errMsg", "");
        return result;
    }

    public Map<String, Object> getMacInfoByBlock() {
    	Map<String, Object> result = new HashMap<>();
    	List<SummaryInfo> summaryInfoList = new ArrayList<SummaryInfo>();
    	SummaryInfo d1 = new SummaryInfo();
    	d1.setAvgTop(83221L);
    	d1.setResidenceId(3573);
    	d1.setResidenceName("大学路127号");
    	d1.setLon(121.505898);
    	d1.setLat(31.306182);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	SummaryInfo d2 = new SummaryInfo();
    	d2.setAvgTop(84321L);
    	d2.setResidenceId(3574);
    	d2.setResidenceName("大学路163号");
    	d2.setLon(121.505612);
    	d2.setLat(31.306074);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	SummaryInfo d3 = new SummaryInfo();
    	d3.setAvgTop(82239L);
    	d3.setResidenceId(3575);
    	d3.setResidenceName("大学路172号");
    	d3.setLon(121.509733);
    	d3.setLat(31.304270);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	SummaryInfo d4 = new SummaryInfo();
    	d4.setAvgTop(82998L);
    	d4.setResidenceId(3576);
    	d4.setResidenceName("大学路199号");
    	d4.setLon(121.504921);
    	d4.setLat(31.305833);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	SummaryInfo d5 = new SummaryInfo();
    	d5.setAvgTop(83307L);
    	d5.setResidenceId(3577);
    	d5.setResidenceName("大学路217号");
    	d5.setLon(121.504746);
    	d5.setLat(31.305669);
    	d5.setNv((int)(Math.random()*90+10));
    	d5.setPv((int)(Math.random()*90+10));
    	d5.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d5);
		// 封装返回对象
    	result.put("status", "success");
		result.put("datalist", summaryInfoList);
		result.put("errMsg", "");
		result.put("pageNo", 1);
		result.put("pageSize", 1);
		result.put("realPageSize", 1);
		result.put("totalPageCount", 1);
		result.put("totalCount", 5);
		result.put("isFirstPage", true);
		result.put("isLastPage", true);
        return result;
    }

    public RealTimeSummaryResult getRealTime() {
        RealTimeSummaryResult summary = new RealTimeSummaryResult();
        RealTimeSummaryObj obj = new RealTimeSummaryObj();
        obj.setRefreshTime(new Date().getTime());
        obj.setUv(new Random().nextInt(30));
        obj.setPv(new Random().nextInt(50));
        obj.setNv(new Random().nextInt(30));
        // for (int i = 1; i <= 60; i++) {
        // RealTimeSummaryObj obj = new RealTimeSummaryObj();
        // obj.setNv(i * (new Random().nextInt(10)));
        // obj.setPv((i + 2) * (new Random().nextInt(20)));
        // obj.setUv(i * (new Random().nextInt(15)));
        // try {
        // obj.setRefreshTime(DateUtil.format2
        // .parse(DateUtil.update1Min(DateUtil.format2.format(new Date()), -i)).getTime());
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
        //
        // System.out.println(DateUtil.format1.format(new Date(obj.getRefreshTime())));
        // realTimeSummaryObjs.add(obj);
        // }
        summary.setRealtimeSummary(obj);
        summary.setErrMsg("");
        summary.setStatus("success");
        return summary;
    }

    public TrendResult trend() {
        TrendResult trendResult = new TrendResult();
        Map<String, List<TrendObj>> map = new HashMap<>();
        List<TrendObj> cur = new ArrayList<>();
        List<TrendObj> hiscusr = new ArrayList<>();
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
            cur.add(t);
        }

        map.put("current", cur);

        for (int i = 1; i <= 60; i++) {
            TrendObj t = new HisTrendInfo();
            t.setAvgTop(Long.valueOf(i * new Random().nextInt()));
            t.setNv(i * (new Random().nextInt(10)));
            t.setPv((i + 2) * (new Random().nextInt(20)));
            t.setUv(i * (new Random().nextInt(15)));
            t.setTimeBegin(tmp.getTimeBegin() - 60 * 1000);
            t.setTimeEnd(tmp.getTimeBegin() - 1000);
            tmp = t;
            hiscusr.add(t);
        }
        map.put("contrastive", hiscusr);
        trendResult.setStatus("success");
        trendResult.setTrend(map);
        return trendResult;
    }
}
