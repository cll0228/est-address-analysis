package com.lezhi.address.admin.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lezhi.address.admin.pojo.StdModel;


@SuppressWarnings("all")
public class AddressExtractor {

    private static final String allResidence;

    private static Set<String> allResidenceNames = new HashSet<>();

    static {
        String RESIDENCE_DIC = "/SH_Residence2016-07-14 16-17-21.csv";

        Set<String> residenceNameList = new HashSet<>();

        InputStream is = null;
        try {
            is = AddressExtractor.class.getResourceAsStream(RESIDENCE_DIC);
            List<String> lines = IOUtils.readLines(is, "utf-8");
            for (String s : lines) {
                s = StringUtils.substringBetween(s, "\"", "\"");
                allResidenceNames.add(s);
                if (!s.matches(".+(?:弄|号|村|坊|道|苑|园|城|庭|大厦|湾|公寓|名邸|墅|小区|小区）|东门|西门|南门|北门|东区|西区|南区|北区)$")) {
                    residenceNameList.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        residenceNameList.add("南林家港");
        residenceNameList.add("镇坪路赵家宅");
        residenceNameList.add("光复西路西合德里");
        residenceNameList.add("城隍庙");
        residenceNameList.add("管弄新村");
        residenceNameList.add("维园道");
        residenceNameList.add("仓城七村");
        residenceNameList.add("公园新村");
        residenceNameList.add("凉城二村");
        residenceNameList.add("东新路《新湖明珠城一期》");
        residenceNameList.add("《春申景城(一期)》");
        residenceNameList.add("《金沙雅苑-舒诗康庭》");
        residenceNameList.add("凤城五村");
        allResidenceNames.addAll(residenceNameList);
        final String residenceNames[] = residenceNameList.toArray(new String[residenceNameList.size()]);

        StringBuilder sb = new StringBuilder();
        for (String residenceName : residenceNames) {
            sb.append("|");
            sb.append(residenceName);
        }
        allResidence = sb.toString().substring(1);
    }

    // partial
    private static final Pattern __buildingRegex = Pattern
            .compile("(?<b>[\\d0-9a-zA-Z\\-_]*?[东西南北上中下甲乙丙丁戊己庚辛壬癸一二三四五六七八九十]*?)" + "(?:号楼?|单元|幢|楼|座)"
                    + "(?<be>[东西南北上中下甲乙丙丁戊己庚辛壬癸]?)");

    private static final Pattern residenceBuilding_regex = Pattern.compile("(?:(?<rn2>" + allResidence
            + ")|(?<rn0>.+弄))" + ".*?" + __buildingRegex.pattern());

    private static final Pattern residenceBuilding_lowThanRoad_regex = Pattern
            .compile("(?<rn1>.+?(?:号|村|坊|道|苑|典|园|城|庭|大厦|湾|公寓|名邸|墅|小区|小区）|东门|西门|南门|北门|东区|西区|南区|北区)" + ".*?)"
                    + __buildingRegex.pattern());

    // 直接解析出室号
    private static final Pattern residenceBuildingRoom_regex = Pattern.compile("(?:(?<rn2>" + allResidence
            + ")|(?<rn0>.+弄)|(?<rn1>.+?(?:号|村|坊|道|苑|园|城|庭|大厦|湾|公寓|名邸|墅|小区|小区）|东门|西门|南门|北门|东区|西区|南区|北区)))"
            + ".*?" + "[\\-/\\\\|](?<b>\\d+)号?[\\-/\\\\|](?<r>\\d+)室?");

    private static final Pattern roadBuilding_regex = Pattern.compile("(?:(?<rn0>.+[路街]))" + ".*?"
            + __buildingRegex.pattern());

    private static final Pattern roadBuildingRoom_regex = Pattern.compile("(?:(?<rn0>.+[路街]))" + ".*?"
            + "[\\-/\\\\|](?<b>\\d+)号?[\\-/\\\\|](?<r>\\d+)室?");

    // private static final Pattern
    private static String extractRoomNo(String input) {

        Pattern p = Pattern.compile("[^\\d](?<r0>\\d{1,2})[楼层]+(?<r1>\\d{2})室?");
        Matcher m = p.matcher(input);
        String n = null;

        if (m.find()) {
            n = m.group("r0") + m.group("r1");
        }

        if (n == null) {
            // 静安延平路区123弄5号3层I室
            p = Pattern.compile("(?<r0>\\d+[楼层]+[a-zA-Z])室?");
            m = p.matcher(input);

            if (m.find()) {
                n = m.group("r0");
            }
        }
        if (n == null) {
            // 甲乙丙丁置前会与楼栋号的标记混淆
            p = Pattern
                    .compile("(?<r0>[\\d\\-－_a-zA-Z一二三四五六七八九十/\\\\()]+[甲乙丙丁戊己庚辛壬癸]?)室(?<r4>[a-zA-Z甲乙丙丁戊己庚辛壬癸]?)");
            m = p.matcher(input);
            if (m.find()) { // 出现多次要第一个，如 ...503室3室
                n = m.group("r0");
                String r4 = m.group("r4");
                if (r4 != null) {
                    n += r4;
                }
            }
        }
        /*
         * 七莘路3885弄1-84号6-10幢全幢 三新北路1333弄17号1层全幢室
         */
        if (n == null) {
            p = Pattern.compile("(?<r10>(?:[\\d\\-/\\\\]+幢)?(?:[\\d\\-－/\\\\]+层)?全幢)室?");
            m = p.matcher(input);
            if (m.find()) {
                n = m.group("r10");// "全幢";
            }
        }

        if (n == null) {
            p = Pattern.compile("(?<r1>[\\d\\-_a-zA-Z一二三四五六七八九十]+[甲乙丙丁戊己庚辛壬癸]?层)室$");
            m = p.matcher(input);
            while (m.find()) { // 出现多次要最后一个
                n = m.group("r1");
            }
        }

        if (n == null) {
            p = Pattern.compile("(?<r1>(?<u>地下)?[\\d\\-_a-zA-Z一二三四五六七八九十]+[层楼])$");
            m = p.matcher(input);
            while (m.find()) { // 出现多次要最后一个
                n = m.group("r1");
            }
        }
        if (n == null) {
            p = Pattern.compile(".+层(?<room>[\\d\\-－_a-zA-Z]+)");
            m = p.matcher(input);
            if (m.find()) {
                n = m.group("room");
            }
        }
        if (n == null) {
            p = Pattern.compile(__buildingRegex.pattern() + "(?<room>[\\d\\-－_a-zA-Z]+)");
            m = p.matcher(input);
            if (m.find()) {
                n = m.group("room");
            }
        }
        if (n == null) {
            if (input.endsWith("全")) {
                n = "全";
            }
        }

        if (n != null && n.startsWith("-")) {
            return n.substring(1);
        }
        return n;
    }

    public static void main(String[] args) {
        // 10层全幢室
        // System.out.println(extractRoomNo("上海市浦东长清路773弄31号401室"));
    	System.out.println(parseAll(new StdModel("上海市浦东长清路773弄31号401室")));
    }

    private static boolean isMatch(String str, Pattern p) {
        Matcher m = p.matcher(str);
        return m.find();
    }

    private static StdModel preProcess(StdModel model) {

        String line = model.getAddress();

        if (line.contains("车库")) {
            return null;
        }

        line = StringUtils.removeStart(line, "：");
        line = StringUtils.removeStart(line, ".");

        // 预处理
        line = line.replaceAll("区\\(县\\)", "区");
        if((line.contains("农场"))) {
        	if(!line.contains("农场村")&&!line.contains("农场路")) {
            	line = line.substring(line.indexOf("农场")+2, line.length());
            }
        }
        Pattern pattern = Pattern.compile("^(-)[\\u4E00-\\u9FA5]"); // TODO
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceFirst(matcher.group(1), "");
        }
        model.setAddress(line);
        model = PreHandle.handle(model);
        line = model.getAddress();
        pattern = Pattern.compile(".+?([\\d]{1,2}层[\\d]{1,2}室)");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            String re = matcher.group(0);
            line = line.replaceFirst(matcher.group(0), re.replace("层", ""));
        }

        line = line.replace("号幢", "号");

        pattern = Pattern.compile(".+?(\\d+层)([a-zA-Z\\d_一二三四五六七八九十]+.*层?)室");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            if (matcher.group(2).length() > 2) {
                line = line.replaceFirst(matcher.group(1), "");
            }
        }

        // 康桥镇沪南路3468弄25幢65号6层602室
        pattern = Pattern.compile(".+?([a-zA-Z\\d_]+幢)[a-zA-Z\\d东西南北上中下甲乙丙丁戊己庚辛壬癸一二三四五六七八九十]+号");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceFirst(matcher.group(1), "");
        }

        pattern = Pattern.compile(".+?[a-zA-Z\\d]+号([a-zA-Z\\d_]+幢)");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceFirst(matcher.group(1), "");
        }

        pattern = Pattern.compile(".+?[a-zA-Z\\d]+号(.+单元)");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceFirst(matcher.group(1), "");
        }

        pattern = Pattern.compile(".+?[a-zA-Z\\d]+号([a-zA-Z\\d_一二三四五六七八九十]+层)[\\d\\-/\\\\]{3,}室");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceFirst(matcher.group(1), "");
        }
        model.setAddress(line);

        //奉贤区南桥镇阳光园环城南路1065弄77号77幢5层502室

        //奉贤区南桥镇环城南路阳光园1065弄77号77幢5层502室

        //环城南路阳光园1065弄
        pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+路([\\u4E00-\\u9FA5]+)\\d+弄");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            String f = matcher.group(1);
            if (allResidenceNames.contains(f)) {
                model.setAddress(line.replace(f, ""));
            }
        }

        pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+路\\d+弄");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            for (String ss : allResidenceNames) {
            	if(!PreHandle.hasDigit(ss)&&(!ss.contains("弄")||!ss.contains("路")||!ss.contains("支弄"))) {
            		if (line.contains(ss)) {
                        model.setAddress(line.replace(ss, ""));
                        break;
                    }
            	}
            }
        }
        return model;
    }

    public static StdModel parseAll(StdModel model) {
        String line = model.getAddress();

        if (line != null)
            line = line.trim();
        else
            return null;

        model = preProcess(model);
        if(model==null) {
        	return null;
        }
        if (model.getAddress() == null)
            return null;

        StdModel stdModel = parseAll__(model);
        return filterResult(stdModel);
    }

    private static StdModel filterResult(StdModel input) {
        if (input == null)
            return null;

        if (input.getRoad() != null) {
            for (String r :allResidenceNames) {
                if (input.getRoad().startsWith(r) && input.getRoad().endsWith("路")) {
                    input.setRoad(StringUtils.removeStart(input.getRoad(), r));
                }
            }
        }

        return input;
    }

    private static StdModel parseAll__(StdModel model) {

        String line = model.getAddress();

        String[] arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+[路街])(\\d+)号楼?(\\d+)室?$"));
        if (arr != null) {
            String room = PreHandle.filterReduplicate2_3(arr[2], 4);
            model.setRoad(arr[0]);
            model.setBuilding(arr[1] + "号");
            model.setHouseNum(room);
            return model;
        }

        // 大康路896弄恒达家园2号702
        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄([\\u4E00-\\u9FA5]+)(\\d+)号(\\d+)$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setResidence(arr[2]);
            model.setBuilding(arr[3] + "号");
            model.setHouseNum(arr[4]);
            return model;
        }
        
        // 海州路418弄62支弄11号
        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄(\\d+)支弄(\\d+)号$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄"+arr[2]+"支弄");
            model.setBuilding(arr[3] + "号");
            return model;
        }
        
        // 西营路33弄22支弄4号403室
        arr = RegexUtil.regexGroup(line,
        		Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄(\\d+)支弄(\\d+)号(\\d+)室$"));
        if (arr != null) {
        	model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄"+arr[2]+"支弄");
            model.setBuilding(arr[3] + "号");
            model.setHouseNum(arr[4]);
            return model;
        }

        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄([\\u4E00-\\u9FA5]+)(\\d+)号$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setResidence(arr[2]);
            model.setBuilding(arr[3] + "号");
            return model;
        }

        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            return model;
        }

        // 光新路129弄21号
        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄(\\d+)号$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setBuilding(arr[2] + "号");
            return model;
        }

        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)$"));
        if (arr != null) {
            model.setResidence(line);
            return model;
        }

        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄([\\u4E00-\\u9FA5]+)$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setResidence(arr[2]);
            return model;
        }

        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)号$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "号");
            return model;
        }

        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)号([\\u4E00-\\u9FA5]+)$"));
        if (arr != null) {
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "号");
            model.setResidence(arr[2]);
            return model;
        }

        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄(\\d+)号(\\d+)室?$"));
        if (arr != null) {
            String room = PreHandle.filterReduplicate2_3(arr[3], 4);
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setBuilding(arr[2] + "号");
            model.setHouseNum(room);
            return model;
        }
        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄[\\-/]?(\\d+)号?[\\-/](\\d+)$"));
        if (arr != null) {
            String room = PreHandle.filterReduplicate2_3(arr[3], 4);
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setBuilding(arr[2] + "号");
            model.setHouseNum(room);
            return model;
        }
        //长海路37弄7号楼604室
        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+)路(\\d+)弄(\\d+)号楼(\\d+)室?$"));
        if (arr != null) {
            String room = PreHandle.filterReduplicate2_3(arr[3], 4);
            model.setRoad(arr[0] + "路");
            model.setLane(arr[1] + "弄");
            model.setBuilding(arr[2] + "号");
            model.setHouseNum(room);
            return model;
        }
        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^(?:五村村|(.+))村([\\d一二三四五六七八九十]+)[组队](\\d+)号([a-zA-Z]?).*$"));
        if (arr != null) {
            model.setResidence(arr[0]);
            model.setBuilding(arr[2] + "号");
        }

        // 七莘路3333号12区17号202
        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+路\\d+号).*?(\\d+)号([\\-/\\d\\\\]+)室?$"));
        if (arr != null) {
            model.setRoad(arr[0]);
            model.setBuilding(arr[1] + "号");
            model.setHouseNum(arr[2]);
            return model;
        }
        //
        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+路\\d+号)(\\d+)号"));
        if (arr != null) {
            model.setRoad(arr[0]);
            model.setBuilding(arr[1] + "号");
            return model;
        }

        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+路\\d+号)(\\d+)"));
        if (arr != null) {
            model.setRoad(arr[0]);
            model.setBuilding(arr[1] + "号");
            return model;
        }

        // 西闸路75号别墅23幢全幢室
        arr = RegexUtil.regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+路\\d+号).*?(\\d+)幢"));
        if (arr != null) {
            String room = extractRoomNo(line);
            model.setRoad(arr[0]);
            model.setBuilding(arr[1] + "号");
            model.setHouseNum(room);
            return model;
        }

        Map<String, String> map = RegexUtil
                .regexGroup(line, residenceBuilding_regex, "rn0", "rn2", "b", "be");
        if (map != null && !map.isEmpty()) {
            String rn0 = map.get("rn0");
            String rn2 = map.get("rn2");

            String residenceName = (String) firstNotNull(rn0, rn2);
            Assert.notNull(residenceName);
            String building = map.get("b");
            Assert.notNull(building);
            String buildingExt = map.get("be");
            if (StringUtils.isNotBlank(buildingExt)) {
                building += buildingExt;
            }
            String roomNo = extractRoomNo(line);
            if (roomNo != null) {
                model.setHouseNum(roomNo);
            }
            if (StringUtils.isNotBlank(residenceName) && StringUtils.isNotBlank(building)) {
                model.setResidence(residenceName);
                model.setBuilding(building + "号");

                return model;
            }
        }

        arr = RegexUtil.regexGroup(line,
                Pattern.compile("^([\\u4E00-\\u9FA5]+)([\\d一二三四五六七八九十]+)[组队](\\d+)号$"));
        if (arr != null) {
            model.setBuilding(arr[2] + "号");
        }
        map = RegexUtil.regexGroup(line, residenceBuildingRoom_regex, "rn0", "rn1", "rn2", "b", "r");
        if (map != null && !map.isEmpty()) {
            String rn0 = map.get("rn0");
            String rn1 = map.get("rn1");
            String rn2 = map.get("rn2");

            String residenceName = (String) firstNotNull(rn1, rn2, rn0);
            Assert.notNull(residenceName);
            String building = map.get("b");
            Assert.notNull(building);
            String room = map.get("r");
            Assert.notNull(room);

            room = PreHandle.filterReduplicate2_3(room, 4);
            if (StringUtils.isNotBlank(residenceName) && StringUtils.isNotBlank(building)) {
                model.setResidence(residenceName);
                model.setBuilding(building + "号");
                if (StringUtils.isNotBlank(room))
                    model.setHouseNum(room);
                return model;
            }

        }

        map = RegexUtil.regexGroup(line, roadBuilding_regex, "rn0", "b", "be");
        if (map != null && !map.isEmpty()) {
            String residenceName = map.get("rn0");

            Assert.notNull(residenceName);
            String building = map.get("b");
            Assert.notNull(building);
            String buildingExt = map.get("be");
            if (StringUtils.isNotBlank(buildingExt)) {
                building += buildingExt;
            }
            String roomNo = extractRoomNo(line);
            if (roomNo != null) {
                model.setHouseNum(roomNo);
            }
            if (StringUtils.isNotBlank(residenceName) && StringUtils.isNotBlank(building)) {
                model.setResidence(model.getRoad() + model.getLane());
                model.setBuilding(building + "号");

                return model;
            }
        }

        map = RegexUtil.regexGroup(line, residenceBuilding_lowThanRoad_regex, "rn1", "b", "be");
        if (map != null && !map.isEmpty()) {
            String rn1 = map.get("rn1");
            String residenceName = (String) firstNotNull(rn1);
            Assert.notNull(residenceName);
            String building = map.get("b");
            Assert.notNull(building);
            String buildingExt = map.get("be");
            if (StringUtils.isNotBlank(buildingExt)) {
                building += buildingExt;
            }
            String roomNo = extractRoomNo(line);
            if (roomNo != null) {
                model.setHouseNum(roomNo);
            }
            if (StringUtils.isNotBlank(residenceName) && StringUtils.isNotBlank(building)) {
                model.setResidence(residenceName);
                model.setBuilding(building + "号");
                return model;
            }
        }

        /*
         * arr = regexGroup(line, Pattern.compile("^([\\u4E00-\\u9FA5]+?)(\\d+)号(\\d+)室$")); if (arr
         * != null) return new Address2(arr[0], arr[1], arr[2]);
         */
        return model;
    }

    /**
	 * 查询数据库
	 * 
	 * @param startName
	 * @return
	 * @throws Exception
	 */
	public static ResultSet connJDBC(String ip,String schema,String userName,String password,String sql) throws Exception {
		// 加载驱动

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://"+ip+":3306/"+schema;
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	/**
	 * 修改表结构
	 * 
	 * @param startName
	 * @return
	 * @throws Exception
	 */
	public static void alterTable(String ip,String schema,String userName,String password,String sql) throws Exception {
		// 加载驱动

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://"+ip+":3306/"+schema;
		Connection conn = DriverManager.getConnection(url, userName, password);
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
	}
    
    private static Object firstNotNull(Object... params) {
        if (params == null)
            return null;

        for (Object o : params) {
            if (o != null)
                return o;
        }
        return null;
    }
}
