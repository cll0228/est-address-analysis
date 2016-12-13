package com.address.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.address.model.StdModel;

/**
 * 
 * @author yyyoushen
 *
 */
public class AddressGenerator {

	public static final String[] QUARRAY = { "黄浦区", "卢湾区", "徐汇区", "长宁区", "静安区",
			"普陀区", "闸北区", "虹口区", "杨浦区", "闵行区", "宝山区", "嘉定区", "浦东新区", "金山区",
			"松江区", "青浦区", "南汇区", "奉贤区", "崇明区", "黄浦县", "卢湾县", "徐汇县", "长宁县",
			"静安县", "普陀县", "闸北县", "虹口县", "杨浦县", "闵行县", "宝山县", "嘉定县", "浦东县",
			"金山县", "松江县", "青浦县", "南汇县", "奉贤县", "崇明县" };
	public static final String[] NOTQUARRAY = { "黄浦", "徐汇", "长宁", "静安", "普陀",
			"闸北", "虹口", "杨浦", "闵行", "宝山", "嘉定", "浦东", "金山", "松江", "青浦", "奉贤",
			"崇明", "卢湾" };
	public static final String[] JIEZHENARRAY = { "庄行镇", "颛桥镇", "祝桥镇", "诸翟镇",
			"朱泾镇", "朱家桥镇", "朱家角镇", "朱行镇", "周庄镇", "周浦镇", "周家桥街道", "周家渡街道",
			"重固镇", "中兴镇", "中山街道", "芷江西路街道", "蒸淀镇", "真新街道", "真如镇", "柘林镇", "赵巷镇",
			"赵屯镇", "长征镇南块", "长征镇北块", "长征镇", "长兴镇", "长寿路街道", "长桥街道", "长风新村街道",
			"长白新村街道", "张泽镇", "张堰镇", "张庙街道", "张江镇", "岳阳街道", "月浦镇", "豫园街道",
			"裕安镇", "友谊路街道", "永兴镇", "永丰街道", "永安镇", "盈浦街道", "殷行街道", "宜川路街道",
			"叶榭镇", "姚庄镇", "洋泾街道", "杨行镇", "盐仓镇", "延吉新村街道", "宣桥镇", "徐泾镇",
			"徐家汇街道", "徐行镇", "兴塔镇", "新镇", "新泰安镇", "新寺镇", "新桥镇", "新农镇", "新民镇",
			"新泾镇", "新江湾城街道", "新华路街道", "新虹街道", "新河镇", "新合丰镇", "新海镇", "新港镇",
			"新埭镇", "新村乡", "新成路街道", "新场镇", "新仓镇", "新浜镇", "斜土路街道", "小蒸镇", "小昆山镇",
			"小东门街道", "向化镇", "香花桥街道", "仙霞新村街道", "夏阳街道", "下沙镇", "西渡镇", "西岑镇",
			"五厍镇", "五里桥街道", "五角场镇", "五角场街道", "吴淞街道", "吴泾镇", "邬桥镇", "潍坊新村街道",
			"万祥镇", "万安镇", "外滩街道", "外冈镇", "瓦屑镇", "头桥镇", "亭林镇", "田林街道", "天山路街道",
			"天平路街道", "天目西路街道", "天马山镇", "提篮桥街道", "桃浦镇", "塘湾镇", "塘外镇", "塘桥街道",
			"唐镇镇", "唐镇", "唐行镇", "坦直镇", "泰日镇", "孙桥镇", "淞南镇", "松隐镇", "松江镇",
			"泗泾镇", "四团镇", "四平路街道", "四川北路街道", "竖新镇", "书院镇", "史家镇", "石泉路街道",
			"石门二路街道", "石化街道", "石湖荡镇", "盛桥镇", "沈巷镇", "莘庄镇", "佘山镇", "邵厂镇",
			"上钢新村街道", "商榻镇", "山阳镇", "三灶镇", "三星镇", "三林镇", "三墩镇", "瑞金二路街道",
			"全塘镇", "曲阳路街道", "青浦镇", "青村镇", "钦洋镇", "钱圩镇", "钱桥镇", "千灯镇", "祁连镇",
			"齐贤镇", "七宝镇", "浦兴路街道", "浦江镇", "平凉路街道", "平安镇", "蓬朗镇", "彭浦镇",
			"彭浦新村街道", "欧阳路街道", "倪家镇", "泥城镇", "南翔镇", "南桥镇", "南码头路街道", "南京西路街道",
			"南京东路街道", "南汇新城镇", "庙镇镇", "庙镇", "庙行镇", "梅陇镇", "泖港镇", "马桥镇", "马陆镇",
			"绿华镇", "吕巷镇", "罗南镇", "罗泾镇", "罗店镇", "陆家嘴街道", "陆家镇", "鲁汇镇", "娄塘镇",
			"龙华街道", "六里镇", "浏河镇街道", "浏河镇", "刘行镇", "凌云路街道", "灵甸镇", "临江镇",
			"临汾路街道", "凉城新村街道", "练塘镇", "莲盛镇", "李塔汇镇", "老西门街道", "老港镇", "廊下镇",
			"控江路街道", "康桥镇", "康健新村街道", "九亭镇", "静安寺街道", "金泽镇", "金杨新村街道", "金山卫镇",
			"金桥镇", "金汇镇", "江湾镇街道", "江苏路街道", "江桥镇", "江浦路街道", "江宁路街道", "江口镇",
			"江海镇", "江川路街道", "建设镇", "戬浜镇", "嘉兴路街道", "嘉定镇街道", "嘉定镇", "纪王镇",
			"机场镇", "惠南镇", "惠民镇", "汇泰镇", "黄路镇", "黄渡镇", "淮海中路街道", "华阳镇", "华阳路街道",
			"华新镇", "华亭镇", "华泾镇", "华漕镇", "花桥镇", "花木街道", "沪东新村街道", "湖南路街道",
			"胡桥镇", "侯家镇", "洪庙镇", "虹桥镇", "虹桥街道", "虹梅路街道", "横沙镇", "横沙乡", "横沔新镇",
			"合作镇", "合庆镇", "合丰镇", "航头镇", "海湾镇", "广中路街道", "广陈镇", "光明镇", "顾路镇",
			"顾村镇", "古美街道", "共和新路街道", "龚路镇", "高桥镇", "高境镇", "高行镇", "高东镇", "港沿镇",
			"港西镇", "干巷镇", "甘泉路街道", "奉新镇", "奉城镇", "凤溪镇", "封浜镇", "枫林路街道", "枫泾镇",
			"方泰镇", "方松街道", "杜行镇", "独山港镇", "洞泾镇", "东兴镇", "东平镇", "东明路街道", "东海镇",
			"东沟镇", "定海路街道", "丁栅镇", "淀山湖镇", "大盈镇", "大新镇", "大团镇", "大同镇", "大桥镇",
			"大桥街道", "大宁路街道", "大洪镇", "大港镇", "大场镇", "打浦桥街道", "川沙镇", "川沙新镇",
			"程家桥街道", "城桥镇", "陈家镇", "陈行镇", "车墩镇", "常乐镇", "漕泾镇", "漕河泾街道",
			"曹杨新村街道", "曹王镇", "曹路镇", "曹家渡街道", "曹行镇", "仓桥镇", "北站街道", "北新镇",
			"北新泾街道", "北桥镇", "北蔡镇", "堡镇镇", "堡镇", "宝山镇", "宝山路街道", "半淞园路街道",
			"白鹤镇", "安亭镇", "芦潮港镇", "芦朝港镇", "老港乡", "庄行镇", "龙华镇", "施湾镇", "六灶镇",
			"外岗镇", "横沔镇" };
	public static final String[] DAOQUARRAY = { "浦东北路", "长宁支路", "崇明支路", "金山大道",
			"浦东大道", "浦东南路", "浦东水泥厂二路", "浦东水泥厂路", "金山工业区大道" };
	public static final Pattern P = Pattern.compile("[a-zA-z]");

	@Test
	public void mainTest() throws Exception {
		readIndex();
	}

	public void readIndex() throws Exception {
		XSSFWorkbook wb2 = new XSSFWorkbook();
		Sheet sheet2 = wb2.createSheet();
		// String path =
		// "C:/Users/yyyoushen/Desktop/"+(int)((Math.random()*9+1)*100000)+".xlsx";
		String path = "C:/Users/yyyoushen/Desktop/对比结果.xlsx";
		ResultSet rs = null;
		try {

			rs = getResidenceNameList();

			Row rowTitle = sheet2.createRow(0);
			Cell cellt1 = rowTitle.createCell(0);
			cellt1.setCellValue("未匹配地址");
			int i = 1;
			Map<String, String> addressMap = new HashMap<String, String>();
			while (rs.next()) {
				addressMap.put(rs.getString("lianjia_residence_name"),
						rs.getString("lianjia_residence_name"));
				addressMap.put(rs.getString("soufang_residence_name"),
						rs.getString("soufang_residence_name"));
				addressMap.put(rs.getString("lezhi_residence_name"),
						rs.getString("lezhi_residence_name"));
				addressMap.put(rs.getString("centanet_residence_name"),
						rs.getString("centanet_residence_name"));
				addressMap.put(rs.getString("fangjia_residence_name"),
						rs.getString("fangjia_residence_name"));
				addressMap.put(rs.getString("iwjw_residence_name"),
						rs.getString("iwjw_residence_name"));
			}
			addressMap.remove(null);
			Set<String> keys = addressMap.keySet();
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String[] str = addressMap.get(key).split("、");
				for (String string : str) {
					if(string.equals("解放街6号")){
						System.out.println("in");
					}
					StdModel stdModel = AddressExtractor.parseAll(string+"1号101室");
					if(stdModel==null||"nullnull".equals(stdModel.getResidence())||"null".equals(stdModel.getResidence())) {
						Row row = sheet2.createRow(i);
						Cell cell = row.createCell(0);
						cell.setCellValue("\""+string+"\"");
						System.out.println(string);
						i++;
					}
				}
			}
			OutputStream outputStream = new FileOutputStream(path);
			wb2.write(outputStream);
			outputStream.flush();
			outputStream.close();
			// 入库操作
//			insertAddressInfo(aList, startName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}


	/**
	 * 查询数据库
	 * 
	 * @param startName
	 * @return
	 * @throws Exception
	 */
	public static ResultSet getResidenceNameList() throws Exception {
		// 加载驱动

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://192.168.201.26:3306/ocn_address";
		String userName = "wangyh";
		String password = "wangyh";
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		String sql = "SELECT o.lianjia_residence_name,o.soufang_residence_name,o.lezhi_residence_name,o.centanet_residence_name,o.fangjia_residence_name,o.iwjw_residence_name FROM ocn_address.of_road_lane_match o;";

		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
}
