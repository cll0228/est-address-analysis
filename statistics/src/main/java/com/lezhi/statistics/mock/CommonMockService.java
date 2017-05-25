package com.lezhi.statistics.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lezhi.statistics.pojo.BlockSummaryInfo;
import com.lezhi.statistics.pojo.DistrictSummaryInfo;
import com.lezhi.statistics.pojo.MacVisit;
import com.lezhi.statistics.pojo.ResidenceSummaryInfo;

/**
 * Created by Colin Yan on 2017/5/25.
 */
@Service
public class CommonMockService {

    public MacVisit getMacVisitChannelHistoryInfo() {
        // TODO
        return null;
    }
    
    public Map<String, Object> getMacInfoByMac() {
    	Map<String, Object> result = new HashMap<>();
    	List<DistrictSummaryInfo> summaryInfoList = new ArrayList<DistrictSummaryInfo>();
    	DistrictSummaryInfo d1 = new DistrictSummaryInfo();
    	d1.setAvgTop(88016L);
    	d1.setDistrictId(310109);
    	d1.setDistrictName("虹口区");
    	d1.setLon(121.495910);
    	d1.setLat(31.287834);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	DistrictSummaryInfo d2 = new DistrictSummaryInfo();
    	d2.setAvgTop(79876L);
    	d2.setDistrictId(310115);
    	d2.setDistrictName("浦东新区");
    	d2.setLon(121.718866);
    	d2.setLat(31.196763);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	DistrictSummaryInfo d3 = new DistrictSummaryInfo();
    	d3.setAvgTop(87321L);
    	d3.setDistrictId(310110);
    	d3.setDistrictName("杨浦区");
    	d3.setLon(121.517929);
    	d3.setLat(31.309973);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	DistrictSummaryInfo d4 = new DistrictSummaryInfo();
    	d4.setAvgTop(96231L);
    	d4.setDistrictId(310105);
    	d4.setDistrictName("长宁区");
    	d4.setLon(121.383699);
    	d4.setLat(31.211912);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	DistrictSummaryInfo d5 = new DistrictSummaryInfo();
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
    	List<BlockSummaryInfo> summaryInfoList = new ArrayList<BlockSummaryInfo>();
    	BlockSummaryInfo d1 = new BlockSummaryInfo();
    	d1.setAvgTop(86769L);
    	d1.setBlockId(96);
    	d1.setBlockName("鞍山");
    	d1.setLon(121.503116);
    	d1.setLat(31.278934);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	BlockSummaryInfo d2 = new BlockSummaryInfo();
    	d2.setAvgTop(89572L);
    	d2.setBlockId(97);
    	d2.setBlockName("东外滩");
    	d2.setLon(121.523816);
    	d2.setLat(31.26058);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	BlockSummaryInfo d3 = new BlockSummaryInfo();
    	d3.setAvgTop(80932L);
    	d3.setBlockId(98);
    	d3.setBlockName("黄兴公园");
    	d3.setLon(121.532974);
    	d3.setLat(31.297323);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	BlockSummaryInfo d4 = new BlockSummaryInfo();
    	d4.setAvgTop(81221L);
    	d4.setBlockId(99);
    	d4.setBlockName("控江路");
    	d4.setLon(121.528034);
    	d4.setLat(31.287506);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	BlockSummaryInfo d5 = new BlockSummaryInfo();
    	d5.setAvgTop(90769L);
    	d5.setBlockId(100);
    	d5.setBlockName("五角场");
    	d5.setLon(121.499755);
    	d5.setLat(31.300934);
    	d5.setNv((int)(Math.random()*90+10));
    	d5.setPv((int)(Math.random()*90+10));
    	d5.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d5);
    	BlockSummaryInfo d6 = new BlockSummaryInfo();
    	d6.setAvgTop(89190L);
    	d6.setBlockId(101);
    	d6.setBlockName("新江湾城");
    	d6.setLon(121.506491);
    	d6.setLat(31.330849);
    	d6.setNv((int)(Math.random()*90+10));
    	d6.setPv((int)(Math.random()*90+10));
    	d6.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d6);
    	BlockSummaryInfo d7 = new BlockSummaryInfo();
    	d7.setAvgTop(85769L);
    	d7.setBlockId(102);
    	d7.setBlockName("周家嘴路");
    	d7.setLon(121.535798);
    	d7.setLat(31.279141);
    	d7.setNv((int)(Math.random()*90+10));
    	d7.setPv((int)(Math.random()*90+10));
    	d7.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d7);
    	BlockSummaryInfo d8 = new BlockSummaryInfo();
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
    	List<ResidenceSummaryInfo> summaryInfoList = new ArrayList<ResidenceSummaryInfo>();
    	ResidenceSummaryInfo d1 = new ResidenceSummaryInfo();
    	d1.setAvgTop(83221L);
    	d1.setResidenceId(3573);
    	d1.setResidenceName("大学路127号");
    	d1.setLon(121.505898);
    	d1.setLat(31.306182);
    	d1.setNv((int)(Math.random()*90+10));
    	d1.setPv((int)(Math.random()*90+10));
    	d1.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d1);
    	ResidenceSummaryInfo d2 = new ResidenceSummaryInfo();
    	d2.setAvgTop(84321L);
    	d2.setResidenceId(3574);
    	d2.setResidenceName("大学路163号");
    	d2.setLon(121.505612);
    	d2.setLat(31.306074);
    	d2.setNv((int)(Math.random()*90+10));
    	d2.setPv((int)(Math.random()*90+10));
    	d2.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d2);
    	ResidenceSummaryInfo d3 = new ResidenceSummaryInfo();
    	d3.setAvgTop(82239L);
    	d3.setResidenceId(3575);
    	d3.setResidenceName("大学路172号");
    	d3.setLon(121.509733);
    	d3.setLat(31.304270);
    	d3.setNv((int)(Math.random()*90+10));
    	d3.setPv((int)(Math.random()*90+10));
    	d3.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d3);
    	ResidenceSummaryInfo d4 = new ResidenceSummaryInfo();
    	d4.setAvgTop(82998L);
    	d4.setResidenceId(3576);
    	d4.setResidenceName("大学路199号");
    	d4.setLon(121.504921);
    	d4.setLat(31.305833);
    	d4.setNv((int)(Math.random()*90+10));
    	d4.setPv((int)(Math.random()*90+10));
    	d4.setUv((int)(Math.random()*90+10));
    	summaryInfoList.add(d4);
    	ResidenceSummaryInfo d5 = new ResidenceSummaryInfo();
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
}
