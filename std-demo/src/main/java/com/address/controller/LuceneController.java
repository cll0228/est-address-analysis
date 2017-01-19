package com.address.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.address.model.LuceneSearchDto;
import com.address.util.PropertyUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/show/lucene")
public class LuceneController {

    String luceneIndexPathProp = "Lucene.Index.FilePath";
    String houseAddressIndexDirPath;
    Analyzer analyzer = new IKAnalyzer();

    @ResponseBody
    @RequestMapping("/{keyWord}")
    public List<LuceneSearchDto> search(@PathVariable String keyWord) {
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
                if (null != document.get("district")) info.setDistrict(String.valueOf(document.get("district")));
                if (null != document.get("block_id")) info.setBlock_id(Integer.valueOf(document.get("block_id")));
                if (null != document.get("block_name")) info.setBlock_name(String.valueOf(document.get("block_name")));
                if (null != document.get("id")) info.setId(Integer.valueOf(document.get("id")));
                if (null != document.get("name")) info.setName(String.valueOf(document.get("name")));
                if (null != document.get("addr")) info.setAddr(String.valueOf(document.get("addr")));
                if (null != document.get("longitude")) info.setLongitude(Double.valueOf(document.get("longitude")));
                if (null != document.get("latitude")) info.setLatitude(Double.valueOf(document.get("latitude")));
                if (null != document.get("max_longitude"))
                    info.setMax_longitude(Double.valueOf(document.get("max_longitude")));
                if (null != document.get("max_latitude"))
                    info.setMax_latitude(Double.valueOf(document.get("max_latitude")));
                if (null != document.get("min_longitude"))
                    info.setMin_longitude(Double.valueOf(document.get("min_longitude")));
                if (null != document.get("min_latitude"))
                    info.setMin_latitude(Double.valueOf(document.get("min_latitude")));
                if (null != document.get("center_longitude"))
                    info.setCenter_longitude(Double.valueOf(document.get("center_longitude")));
                if (null != document.get("center_latitude"))
                    info.setCenter_latitude(Double.valueOf(document.get("center_latitude")));
                list.add(info);
            }
            return list;
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
