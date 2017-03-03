package com.lezhi.adminlj.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import com.lezhi.adminlj.pojo.LuceneSearchDto;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendl on 2017/02/16.
 */
public class IndexGenerator {
    String luceneIndexPathProp = "Lucene.Index.FilePath";
//    String luceneIndexPathProp = "/lucene/index";
    String houseAddressIndexDirPath;
    Analyzer analyzer = new IKAnalyzer();
    Connection conn;

    @Test
    public void testSearchIndex() {
        searchIndex("永业公寓");
    }

    @Test
    public void testCreateIndex() throws Exception {
        appendIndex(getResult());
    }

    public void appendIndex(ResultSet rs) {
        if (null == houseAddressIndexDirPath)
            houseAddressIndexDirPath = PropertyUtil.get("/lucene", luceneIndexPathProp);
        IndexWriter writer = null;
        System.out.println(houseAddressIndexDirPath);
        try {
            Directory directory = FSDirectory.open(new File(houseAddressIndexDirPath));
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            writer = new IndexWriter(directory, iwc);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            String titleStr = "";
            String titleType = "";
            for (int i = 1; i <= columnCount; i++) {
                titleStr += metaData.getColumnLabel(i) + "\t";
                titleType += metaData.getColumnTypeName(i) + "\t";
            }

            System.out.println(titleStr);
            System.out.println(titleType);

            while (rs.next()) {
                Document document = new Document();
                for (int i = 1; i <= columnCount; i++) {
                    Object v = rs.getObject(i);
                    System.out.print(v + "\t");
                    if (v instanceof Integer) {
                        document.add(new IntField(metaData.getColumnLabel(i), (Integer) v, Field.Store.YES));
                    } else if (v instanceof Long) {
                        document.add(new LongField(metaData.getColumnLabel(i), ((Long) v).intValue(), Field.Store.YES));
                    } else if (v instanceof BigDecimal) {
                        document.add(new DoubleField(metaData.getColumnLabel(i), ((BigDecimal) v).doubleValue(), Field.Store.YES));
                    } else if (v instanceof String) {
                        document.add(new TextField(metaData.getColumnLabel(i), (String) v, Field.Store.YES));
                    }
                }
                writer.addDocument(document);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.commit();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ResultSet getResult() throws Exception {
        Statement stmt = conn.createStatement();
        String sql ="SELECT CONVERT (1, signed) AS type, NULL district_id, NULL district_name, NULL town_id, NULL town_name, NULL neighborhood_id,\n" +
                "NULL neighborhood_name, district_id AS id, district_name AS name, NULL AS addr, NULL longitude, NULL latitude, center_lng, center_lat\n" +
                "FROM of_district\n" +
                "UNION ALL\n" +
                "SELECT CONVERT (2, signed) AS type, d.district_id, d.district_name, NULL town_id, NULL town_name, NULL neighborhood_id, NULL neighborhood_name,\n" +
                "t.town_id AS id, t.town_name AS name, NULL AS addr, NULL longitude, NULL latitude, t.longitude center_lng, t.latitude center_lat\n" +
                "FROM of_district d\n" +
                "LEFT JOIN of_town t ON d.district_id = t.district_id\n" +
                "UNION ALL\n" +
                "SELECT CONVERT (3, signed) AS type, d.district_id, d.district_name, t.town_id, t.town_name, NULL neighborhood_id, NULL neighborhood_name,\n" +
                "n.neighborhood_id AS id, n.neighborhood_name AS name, n.neighborhood_addr AS addr, n.lng AS longitude, n.lat AS latitude, NULL center_lng, NULL center_lat\n" +
                "FROM of_neighborhood n\n" +
                "LEFT JOIN of_town t ON n.town_id = t.town_id\n" +
                "LEFT JOIN of_district d ON n.district_id = d.district_id\n" +
                "UNION ALL\n" +
                "SELECT CONVERT (4, signed) AS type, NULL district_id, NULL district_name, NULL town_id, NULL town_name, NULL neighborhood_id, NULL neighborhood_name,\n" +
                "r.id AS id, r.road_name AS name, NULL addr, NULL longitude, NULL latitude, NULL center_lng, NULL center_lat\n" +
                "FROM of_std_road r\n" +
                "UNION ALL\n" +
                "SELECT CONVERT (5, signed) AS type, d.district_id, d.district_name, t.town_id, t.town_name, n.neighborhood_id, n.neighborhood_name, r.id AS id,\n" +
                "r.residence_name AS name, r.residence_addr AS addr, r.lon AS longitude, r.lat AS latitude, NULL center_lng, NULL center_lat\n" +
                "FROM of_residence r\n" +
                "LEFT JOIN of_town t ON r.of_town_id = t.town_id\n" +
                "LEFT JOIN of_district d ON r.of_district_id = d.district_id\n" +
                "LEFT JOIN of_neighborhood n ON r.of_neighborhood_id = n.neighborhood_id";
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }

    public List<LuceneSearchDto> searchIndex(String keyWord) {
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

    @Test
    public void tokenIKAnalyzer() {
        try {
            Analyzer analyzer = new IKAnalyzer();
            TokenStream tokenStream = analyzer.tokenStream("", "金山大道2335弄、蒙山路1727弄、卫零路886弄");
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                System.out.println(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void conn() throws SQLException {
        String url = "jdbc:mysql:loadbalance://192.168.201.26:3306/ocn_address";
        String userName = "appUser";
        String password = "aAzWw4vE";
        conn = DriverManager.getConnection(url, userName, password);
    }
}
