package com.lezhi.adminlj.controller;

import com.lezhi.adminlj.pojo.District;
import com.lezhi.adminlj.pojo.LuceneSearchDto;
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
