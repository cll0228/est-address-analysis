package com.of.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.lezhi.statistics.pojo.LogGeneratorDistrict;

/**
 * 
 * @author yyyoushen
 *
 */
public class LogGenerator {

	@Test
	public void mainTest() throws Exception {
		/*
		 * readIndex("房价点评网", "residence_id", "address", "residence_name",
		 * "fangjia"); readIndex("爱屋吉屋", "code", "address", "name", "iwjw");
		 * readIndex("乐智", "ResidenceID", "ResidenceAddr", "ResidenceName",
		 * "lezhi"); readIndex("中原地产", "centanet_id", "address",
		 * "residence_name", "centanet"); readIndex("搜房",
		 * "soufang_residence_id", "address", "residence_name", "soufang");
		 */
		logGenerator();
	}

	public void logGenerator() throws Exception {
		List<LogGeneratorDistrict> logList = new ArrayList<LogGeneratorDistrict>();
		List<LogGeneratorDistrict> finalResult = new ArrayList<LogGeneratorDistrict>();
		ResultSet rs = null;
		try {
//			String startTime = PropertyUtil.getStartHour(new Date());
//			String endTime = PropertyUtil.getEndHour(new Date());
			String startTime = "2017-03-14 00:00:00";
			String endTime = "2017-03-14 00:59:59";
			/*String lessTime = PropertyUtil.getStartHour(new Date());
			if(startTime.contains("00:00:00")) {
				lessTime = PropertyUtil.getBeforeDay(new Date());
			}*/
			rs = getDistrictResult(startTime,endTime);
			while (rs.next()) {
				LogGeneratorDistrict log = new LogGeneratorDistrict();
				ResultSet rs2 = null;
				String mac = rs.getString("mac");
				String channel = rs.getString("channel");
				Long totalTop = rs.getLong("totalTop");
				Long begin = rs.getLong("begin");
				Long end = rs.getLong("end");
				Integer districtId = rs.getInt("districtId");
				Integer pv = rs.getInt("pv");
				String session = rs.getString("session");
				System.out.println("mac=" + mac + ",channel=" + channel
						+ ",totalTop=" + totalTop + ",begin=" + begin + ",end="
						+ end + ",districtId=" + districtId + ",pv=" + pv
						+ ",session=" + session);
				rs2 = getSpanResult(startTime,session);
				while (rs2.next()) {
					Integer count = rs2.getInt("count");
					Long spanTime = rs2.getLong("spanTime");
					if (count > 0) {
						totalTop = (end - spanTime)*10;
					}
				}
				log.setMac(mac);
				log.setTotalTop(totalTop);
				log.setPv(pv);
				log.setChannelNo(channel);
				log.setDistrictId(districtId);
				log.setSession(session);
				// log.setBeginTime(beginTime);
				// log.setEndTime(endTime);
				logList.add(log);
				System.out.println(log.toString());
			}
			Map<String, LogGeneratorDistrict> map = new HashMap<String, LogGeneratorDistrict>();
			for (LogGeneratorDistrict logGeneratorDistrict : logList) {
				if (map.get(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo()) == null) {
					map.put(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo(),
							logGeneratorDistrict);
				} else if (map.get(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo()) != null) {
					LogGeneratorDistrict temp = new LogGeneratorDistrict();
					temp = map.get(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo());
					logGeneratorDistrict.setPv(logGeneratorDistrict.getPv()
							+ temp.getPv());
					logGeneratorDistrict.setTotalTop(logGeneratorDistrict
							.getTotalTop() + temp.getTotalTop());
					map.remove(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo());
					map.put(logGeneratorDistrict.getMac()+logGeneratorDistrict.getChannelNo(),
							logGeneratorDistrict);
				}
			}
			Set set = map.entrySet();
			Iterator<?> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry mapentry = (Entry<?, ?>) iterator.next();
				System.out.println(mapentry.getKey() + "/"
						+ mapentry.getValue().toString());
			}
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
	 * 清空未匹配数据库
	 * 
	 * @throws Exception
	 */
	/*
	 * public static void truncateNoMatchTable() throws Exception { // 加载驱动
	 * Class.forName("com.mysql.jdbc.Driver"); String url =
	 * "jdbc:mysql://192.168.201.26:3306/sts"; String userName = "wangyh";
	 * String password = "wangyh"; Connection conn =
	 * DriverManager.getConnection(url, userName, password); Statement stmt =
	 * conn.createStatement(); String sql =
	 * "truncate table no_match_address_info"; stmt.execute(sql); }
	 */

	/**
	 * 插入未匹配数据库
	 * 
	 * @throws Exception
	 */
	/*
	 * public static void insertNoMatchTable(List<AddressInfo> aList) throws
	 * Exception { // 加载驱动 Class.forName("com.mysql.jdbc.Driver"); String url =
	 * "jdbc:mysql://192.168.201.26:3306/sts"; String userName = "wangyh";
	 * String password = "wangyh"; Connection conn =
	 * DriverManager.getConnection(url, userName, password); Statement stmt =
	 * conn.createStatement(); for (AddressInfo addressInfo : aList) { String
	 * sql =
	 * "insert into no_match_address_info (residence_id,residence_name,address,source) values('"
	 * +
	 * addressInfo.getResidenceId()+"','"+addressInfo.getResidenceName().replaceAll
	 * ("'", "")+"','"+addressInfo.getBeforeAddress().replaceAll("'",
	 * "")+"','"+addressInfo.getSource().replaceAll("'", "")+"')";
	 * System.out.println(sql); stmt.execute(sql); } }
	 */

	/**
	 * 获取区县LOG解析后的数据
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public static ResultSet getDistrictResult(String startTime, String endTime) throws Exception {
		// 加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://192.168.201.26:3306/statistics";
		String userName = "wangyh";
		String password = "wangyh";
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		String sql = "SELECT a.channel,a.mac,a.session,(unix_timestamp(MAX(a.timestamp)) - unix_timestamp(MIN(a.timestamp)))*10 totalTop,unix_timestamp(MIN(a.timestamp)) begin,unix_timestamp(MAX(a.timestamp)) end,b.district_id districtId, count(a.mac) pv FROM statistics.api_logs_shfctv a INNER JOIN statistics.mac_info b ON a.mac = b.mac WHERE a.`timestamp` >= '"+startTime+"' AND a.`timestamp` <= '"+endTime+"' GROUP BY a.mac,a.channel,a.session";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * 获取区县LOG解析后块时间段的数据
	 * 
	 * @return ResultSet
	 * @throws Exception
	 */
	public static ResultSet getSpanResult(String startTime, String session) throws Exception {
		// 加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://192.168.201.26:3306/statistics";
		String userName = "wangyh";
		String password = "wangyh";
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		String sql = "SELECT COUNT(*) count,unix_timestamp('"+startTime+"') spanTime FROM statistics.api_logs_shfctv a WHERE a.timestamp < '"+startTime+"' AND session = '"
				+ session + "'";
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	/**
	 * 其余5个数据来源数据
	 * 
	 * @param startName
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static ResultSet getResidenceResult(String startName) throws
	 * Exception { // 加载驱动 String sql = null;
	 * Class.forName("com.mysql.jdbc.Driver"); String url =
	 * "jdbc:mysql://192.168.201.26:3306/sts"; String userName = "wangyh";
	 * String password = "wangyh"; Connection conn =
	 * DriverManager.getConnection(url, userName, password); Statement stmt =
	 * conn.createStatement();
	 * 
	 * if(startName.equals("centanet")) { sql =
	 * "SELECT * FROM centanet_residence_info_data"; } else
	 * if(startName.equals("lezhi")) { sql =
	 * "SELECT * FROM sts.sh_residence_info where ResidenceTypeID in( 1,2,3,4,8)"
	 * ; } else { sql = "SELECT * FROM "+startName+"_residence_info"; }
	 * 
	 * ResultSet rs = stmt.executeQuery(sql); return rs; }
	 */

	/**
	 * 插入对应表
	 * 
	 * @param aList
	 * @param startName
	 * @throws Exception
	 */
	/*
	 * public static void insertAddressInfo(List<AddressInfo> aList, String
	 * startName) throws Exception { if(aList.size()>0) {
	 * Class.forName("com.mysql.jdbc.Driver"); String url =
	 * "jdbc:mysql://192.168.201.26:3306/sts"; String userName = "wangyh";
	 * String password = "wangyh"; Connection conn =
	 * DriverManager.getConnection(url, userName, password); Statement stmt =
	 * conn.createStatement(); String sql =
	 * "truncate table "+startName+"_address_info"; stmt.execute(sql); for
	 * (AddressInfo addressInfo : aList) { sql = "insert into "+startName+
	 * "_address_info (residence_id,residence_name,before_address,after_address) values('"
	 * +
	 * addressInfo.getResidenceId()+"','"+addressInfo.getResidenceName().replaceAll
	 * ("'", "")+"','"+addressInfo.getBeforeAddress().replaceAll("'",
	 * "")+"','"+addressInfo.getAfterAddress().replaceAll("'", "")+"')";
	 * System.out.println(sql); stmt.execute(sql); } } }
	 *//**
	 * 中文数字转int
	 * 
	 * @param chineseNumber
	 * @return
	 */
	/*
	 * @SuppressWarnings("unused") public static int chineseNumber2Int(String
	 * chineseNumber){ int result = 0; int temp = 1;//存放一个单位的数字如：十万 int count =
	 * 0;//判断是否有chArr boolean flag = true; char[] cnArr = new
	 * char[]{'一','二','三','四','五','六','七','八','九','零'}; char[] chArr = new
	 * char[]{'十','百','千','万','亿'}; for (int i = 0; i < chineseNumber.length();
	 * i++) { boolean b = true;//判断是否是chArr char c = chineseNumber.charAt(i);
	 * for (int j = 0; j < cnArr.length; j++) {//非单位，即数字 if (c == cnArr[j]) {
	 * if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中 result += temp; temp = 1; count
	 * = 0; } // 下标+1，就是对应的值 temp = j + 1; b = false; break; } }
	 * if(b){//单位{'十','百','千','万','亿'} flag = false; for (int j = 0; j <
	 * chArr.length; j++) { if (c == chArr[j]) { switch (j) { case 0: temp *=
	 * 10; break; case 1: temp *= 100; break; case 2: temp *= 1000; break; case
	 * 3: temp *= 10000; break; case 4: temp *= 100000000; break; default:
	 * break; } count++; } } } if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
	 * result += temp; } } String re = ""; int a = 0; if(flag) { for (int i = 0;
	 * i < chineseNumber.length(); i++) { char c = chineseNumber.charAt(i); for
	 * (int j = 0; j < cnArr.length; j++) {//数字 if (c == cnArr[j]) { if(j==9) {
	 * a = 0; } else { a = j+1; } re += String.valueOf(a); break; } } // if (i
	 * == chineseNumber.length() - 1) {//遍历到最后一个字符 // result += temp; // } } }
	 * if(re!="") { result = Integer.parseInt(re); return result; } else {
	 * return result; }
	 * 
	 * }
	 *//**
	 * 统计出现过几次
	 * 
	 * @param text
	 * @param sub
	 * @return
	 */
	/*
	 * public static int count(String text,String sub){ int count =0, start =0;
	 * while((start=text.indexOf(sub,start))>=0){ start += sub.length(); count
	 * ++; } return count; }
	 *//**
	 * 判断字符串是否是整数
	 */
	/*
	 * public static boolean isInteger(String value) { try {
	 * Integer.parseInt(value); return true; } catch (NumberFormatException e) {
	 * return false; } }
	 */
}
