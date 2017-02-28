package com.lezhi.adminlj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lezhi.adminlj.pojo.CountParam;
import com.lezhi.adminlj.pojo.DataList;
import com.lezhi.adminlj.pojo.District;
import com.lezhi.adminlj.service.SlideNavService;


/**
 * Created by Wangyh on 2017/2/14.
 */
@Controller
@RequestMapping("/")
public class SlideNavController {
	@Autowired
	private SlideNavService slideNavService;
    
	/**
	 * 侧边栏列表查询 
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "district", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> district(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	ArrayList<District> dList = slideNavService.districtList();
    	result.put("list", dList);
		return result;
    }
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> list(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	ArrayList<DataList> dataList = new ArrayList<DataList>();
    	ArrayList<CountParam> countList = new ArrayList<CountParam>();
    	countList = slideNavService.districtCount();
    	for (CountParam countParam : countList) {
			DataList da = new DataList();
        	da.setDataId(countParam.getLevelId().toString());
        	da.setHouseholds(countParam.getHouseholds());
        	da.setProportion(countParam.getProportion());
        	da.setShowName(countParam.getLevelName());
        	da.setDiv("1");
        	da.setType("init");
        	
        	dataList.add(da);
		}
    	/*String qupin[] = new String[]{"pudongxinqu","minhang","baoshan","xuhui","putuo","yangpu","changning","songjiang","jiading","huangpu","jingan","zhabei","hongkou","qingpu","fengxian","jinshan","chongming"};
    	String qu[] = new String[]{"浦东新区","闵行区","宝山区","徐汇区","普陀区","杨浦区","长宁区","松江区","嘉定区","黄浦区","静安区","闸北区","虹口区","青浦区","奉贤区","金山区","崇明区"};
    	for (int i = 0; i < qupin.length; i++) {
    		DataList da = new DataList();
        	da.setDataId(qupin[i]);
        	da.setHouseholds((int)((Math.random()*9+1)*100000));
        	da.setProportion(1+(Double)(Math.random()*90));
        	da.setLatitude(31.2080020904541);
        	da.setLongitude(121.60652923583984);
        	da.setShowName(qu[i]);
        	da.setType("init");
        	
        	dataList.add(da);
		}*/
    	result.put("dataList", dataList);
		return result;
    }
    
    @RequestMapping(value = "listSearch", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listSearch(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
    	ArrayList<DataList> dataList = new ArrayList<DataList>();
    	String dataId = request.getParameter("dataId");
    	String type = request.getParameter("type");
    	ArrayList<CountParam> countList = new ArrayList<CountParam>();
		String keyword = "undefined".equals(request.getParameter("keyword"))?null:request.getParameter("keyword");
		String a = request.getParameter("a");// 用户规模
		String l = request.getParameter("l");// 小区规模
		String p = request.getParameter("p");// 小区均价
		String t = request.getParameter("t");// 小区特征/小区分类
		String c = request.getParameter("c");// 小区特征/小区用户占比

    	if("city".equals(type) && isNullString(keyword) && isNullString(a) && isNullString(l) &&
				isNullString(p) && isNullString(t) && isNullString(c)) {
    		result.put("status", "1");
    		result.put("dataList", dataList);
    	}else{
			result.put("dataList", dataList);
			result.put("status", "0");
		}
/*		else {
    		if(type.equals("0")||(dataId.equals("sh")&&type.equals("1"))) {
    			countList = slideNavService.districtCount();
    		} else if((dataId.length()==6&&type.equals("1"))||(dataId.length()==6&&type.equals("2"))) {
    			countList = slideNavService.levelOneCount(dataId);
    		} else if((dataId.length()==9&&type.equals("2"))||(dataId.length()==9&&type.equals("3"))) {
    			countList = slideNavService.levelTwoCount(dataId);
    		} else if((dataId.length()==12&&type.equals("3"))) {
    			countList = slideNavService.levelThreeCount(dataId);
    		}
    		for (CountParam countParam : countList) {
    			DataList da = new DataList();
            	da.setDataId(countParam.getLevelId().toString());
            	da.setHouseholds(countParam.getHouseholds());
            	da.setProportion(countParam.getProportion());
            	da.setShowName(countParam.getLevelName());
            	if(dataId.equals("sh")) {
            		da.setDiv(type);
            	} else {
            		Integer d = Integer.parseInt(type)+1;
            		da.setDiv(d.toString());
            	}
            	da.setType("init");
            	
            	dataList.add(da);
    		}
    		*//*String qupin[] = new String[]{"pudongxinqu","minhang","baoshan","xuhui","putuo","yangpu","changning","songjiang","jiading","huangpu","jingan","zhabei","hongkou","qingpu","fengxian","jinshan","chongming"};
        	String qu[] = new String[]{"浦东新区1","闵行区1","宝山区1","徐汇区","普陀区","杨浦区","长宁区","松江区","嘉定区","黄浦区","静安区","闸北区","虹口区","青浦区","奉贤区","金山区","崇明区"};
        	for (int i = 0; i < qupin.length; i++) {
        		DataList da = new DataList();
            	da.setDataId(qupin[i]);
            	da.setHouseholds((int)((Math.random()*9+1)*100000));
            	da.setProportion(1+(int)(Math.random()*90));
            	da.setLatitude(31.2080020904541);
            	da.setLongitude(121.60652923583984);
            	da.setShowName(qu[i]);
            	da.setType("init");
            	
            	dataList.add(da);
    		}*//*
        	result.put("status", "0");
        	result.put("dataList", dataList);
    	}*/
		return result;
    }

	public static boolean isNullString(String str) {
		return (null == str || StringUtils.isBlank(str.trim()) || "null".equals(str.trim().toLowerCase())) ? true : false;
	}
}
