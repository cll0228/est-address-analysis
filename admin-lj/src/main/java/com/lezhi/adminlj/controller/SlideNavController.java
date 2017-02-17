package com.lezhi.adminlj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    	String qupin[] = new String[]{"pudongxinqu","minhang","baoshan","xuhui","putuo","yangpu","changning","songjiang","jiading","huangpu","jingan","zhabei","hongkou","qingpu","fengxian","jinshan","chongming"};
    	String qu[] = new String[]{"浦东新区","闵行区","宝山区","徐汇区","普陀区","杨浦区","长宁区","松江区","嘉定区","黄浦区","静安区","闸北区","虹口区","青浦区","奉贤区","金山区","崇明区"};
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
		}
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
    	if(type.equals("city")) {
    		result.put("status", "1");
    		result.put("dataList", dataList);
    	} else {
    		String qupin[] = new String[]{"pudongxinqu","minhang","baoshan","xuhui","putuo","yangpu","changning","songjiang","jiading","huangpu","jingan","zhabei","hongkou","qingpu","fengxian","jinshan","chongming"};
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
    		}
        	result.put("status", "0");
        	result.put("dataList", dataList);
    	}
		return result;
    }
}
