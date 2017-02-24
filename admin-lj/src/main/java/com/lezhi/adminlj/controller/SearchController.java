package com.lezhi.adminlj.controller;

import com.lezhi.adminlj.pojo.*;
import com.lezhi.adminlj.service.SlideNavService;
import com.lezhi.adminlj.util.PropertyUtil;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by chendl on 2017/2/15.
 */
@Controller
@RequestMapping("/")
public class SearchController {
	@Autowired
	private SlideNavService slideNavService;

	String luceneIndexPathProp = "Lucene.Index.FilePath";
	String houseAddressIndexDirPath;
	Analyzer analyzer = new IKAnalyzer();

	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public Map<String, Object> search(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<>();
		String keyWord = String.valueOf(request.getParameter("keyword"));
		if (null == houseAddressIndexDirPath)
			houseAddressIndexDirPath = PropertyUtil.get("/lucene", luceneIndexPathProp);
		System.out.println(houseAddressIndexDirPath);
		IndexReader reader = null;
		try {
			String[] houseAddressIndexFields = {"name", "addr"};
			reader = DirectoryReader.open(FSDirectory.open(new File(houseAddressIndexDirPath)));
			IndexSearcher searcher = new IndexSearcher(reader);
			MultiFieldQueryParser parser = new MultiFieldQueryParser(houseAddressIndexFields, analyzer);
			parser.setDefaultOperator(QueryParser.Operator.OR);
			Query query = parser.parse("\"" + keyWord + "\"");
			System.out.println("query:" + query.toString());
			TopDocs hits = searcher.search(query, 10);
			List<LuceneSearchDto> list = new ArrayList<LuceneSearchDto>();
			for (ScoreDoc sd : hits.scoreDocs) {
				Document document = searcher.doc(sd.doc);
				LuceneSearchDto info = new LuceneSearchDto();
				if (null != document.get("type")) info.setType(Integer.valueOf(document.get("type")));
				if (null != document.get("district_id"))
					info.setDistrict_id(Integer.valueOf(document.get("district_id")));
				if (null != document.get("district_name")) info.setDistrict_name(String.valueOf(document.get("district_name")));
				if (null != document.get("town_id")) info.setTown_id(Integer.valueOf(document.get("town_id")));
				if (null != document.get("town_name")) info.setTown_name(String.valueOf(document.get("town_name")));
				if (null != document.get("neighborhood_id")) info.setNeighborhood_id(Integer.valueOf(document.get("neighborhood_id")));
				if (null != document.get("neighborhood_name")) info.setNeighborhood_name(String.valueOf(document.get("neighborhood_name")));
				if (null != document.get("id")) info.setId(document.get("id"));
				if (null != document.get("name")) info.setName(String.valueOf(document.get("name")));
				if (null != document.get("addr")) info.setAddr(String.valueOf(document.get("addr")));
				if (null != document.get("longitude")) info.setLongitude(Double.valueOf(document.get("longitude")));
				if (null != document.get("latitude")) info.setLatitude(Double.valueOf(document.get("latitude")));
				if (null != document.get("center_lng"))
					info.setCenter_lng(Double.valueOf(document.get("center_lng")));
				if (null != document.get("center_lat"))
					info.setCenter_lat(Double.valueOf(document.get("center_lat")));
				System.out.println(info);
				list.add(info);
			}
			result.put("list", list);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	@RequestMapping(value = "searchKeyword", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchKeyword(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<>();
		ArrayList<DataList> dataList = new ArrayList<DataList>();
		String dataId = request.getParameter("dataId");
		String type = request.getParameter("type");
		String jdh = null;
		if(type.contains("\"")) {
			jdh = type;
			type = "1";
		}
		String keyword = "null".equals(request.getParameter("keyword"))?null:request.getParameter("keyword");
		String keyId = "null".equals(request.getParameter("keyId"))?null:request.getParameter("keyId");
		String keyType = "null".equals(request.getParameter("keyType"))?null:request.getParameter("keyType");
		String a = request.getParameter("a");// 用户规模
		String l = request.getParameter("l");// 小区规模
		String p = request.getParameter("p");// 小区均价
		String t = request.getParameter("t");// 小区特征/小区分类
		String c = request.getParameter("c");// 小区特征/小区用户占比
/*		String g = request.getParameter("g");// 不动产估值
		String o = request.getParameter("o");// 有限账单/账单活跃度
		String f = request.getParameter("f");// 有限账单/是否订阅增值节目*/
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setKeyword(keyword);
		paramInfo.setKeyId(keyId);
		paramInfo.setKeyType(keyType);
		paramInfo.setA(a);
		paramInfo.setL(l);
		paramInfo.setP(p);
		paramInfo.setT(t);
		paramInfo.setC(c);
		paramInfo.setDataId(dataId);
		paramInfo.setType(type);
		paramInfo.setJdh(jdh);

		ArrayList<CountParam> countList = new ArrayList<CountParam>();
		countList = slideNavService.searchKeyword(paramInfo);

		if(type.equals("city")) {
			result.put("status", "1");
			result.put("dataList", dataList);
		} else {
/*			if(type.equals("0")) {
				countList = slideNavService.districtCount();
			} else if(dataId.equals("sh")&&type.equals("1")) {
				countList = slideNavService.districtCount();
			} else if(dataId.length()==6&&type.equals("1")) {
				countList = slideNavService.levelOneCount(dataId);
			} else if(dataId.equals("2")) {

			} else if(dataId.equals("3")) {

			}*/
			for (CountParam countParam : countList) {
				DataList da = new DataList();
				da.setDataId(countParam.getLevelId().toString());
				da.setHouseholds(countParam.getHouseholds());
				da.setProportion(countParam.getProportion());
				da.setShowName(countParam.getLevelName());
				da.setType("init");

				dataList.add(da);
			}
    		/*String qupin[] = new String[]{"pudongxinqu","minhang","baoshan","xuhui","putuo","yangpu","changning","songjiang","jiading","huangpu","jingan","zhabei","hongkou","qingpu","fengxian","jinshan","chongming"};
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
    		}*/
			result.put("status", "0");
			result.put("dataList", dataList);
		}
		return result;
	}
}
