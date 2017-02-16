package com.lezhi.adminlj.controller;

import com.lezhi.adminlj.pojo.District;
import com.lezhi.adminlj.pojo.LuceneSearchDto;
import com.lezhi.adminlj.pojo.SearchData;
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
	String luceneIndexPathProp = "Lucene.Index.FilePath";
	String houseAddressIndexDirPath;
	Analyzer analyzer = new IKAnalyzer();

	@Autowired
	private SlideNavService slideNavService;

    @RequestMapping(value = "searchKeyword", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> searchKeyword(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Map<String, Object> result = new HashMap<>();
//    	ArrayList<District> dList = slideNavService.districtList();
		List<SearchData> searchDataList = new ArrayList<>();
		SearchData searchData1 = new SearchData();
		searchData1.setId("st100021816");
		searchData1.setDataId("100021816");
		searchData1.setRemark("1号线");
		searchData1.setShowName("共康路站");
		searchData1.setTypeName("地铁站");
		searchData1.setSellCount(50);
		SearchData searchData2 = new SearchData();
		searchData2.setId("5011000013338");
		searchData2.setDataId("5011000013338");
		searchData2.setRemark("共康 宝山");
		searchData2.setShowName("共康五村");
		searchData2.setTypeName("小区");
		searchData2.setSellCount(30);
		SearchData searchData3 = new SearchData();
		searchData3.setId("sq611900003");
		searchData3.setDataId("gongkang");
		searchData3.setRemark("");
		searchData3.setShowName("共康");
		searchData3.setTypeName("板块");
		searchData3.setSellCount(11);
		searchDataList.add(searchData1);
		searchDataList.add(searchData2);
		searchDataList.add(searchData3);
    	result.put("list", searchDataList);
		return result;
    }

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
}
