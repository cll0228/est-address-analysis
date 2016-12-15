package com.address.util;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.address.model.StdModel;

@SuppressWarnings("all")
public class AddressExtractor {

    private static final String allResidence;

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
    
    static {
        String RESIDENCE_DIC = "/SH_Residence2016-07-14 16-17-21.csv";

        Set<String> residenceNameList = new HashSet<>();
        InputStream is = null;
        try {
            is = AddressExtractor.class.getResourceAsStream(RESIDENCE_DIC);
            List<String> lines = IOUtils.readLines(is, "utf-8");
            for (String s : lines) {
                s = StringUtils.substringBetween(s, "\"", "\"");
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
            .compile("(?<rn1>.+?(?:号|村|坊|道|苑|典|园|城|庭|大厦|湾|公寓|名邸|墅|小区|小区）|东门|西门|南门|北门|东区|西区|南区|北区))" + ".*?"
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
        // System.out.println(extractRoomNo("石门路39弄89号9层"));

        System.out.println(parseAll(new StdModel("田林七村8号301室")));
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
        return model;
    }

    public static StdModel parseAll(StdModel model) {
        String line = model.getAddress();
        for (String jiezhen : JIEZHENARRAY) {
			if(line.contains(jiezhen)) {
				line = line.substring(line.indexOf(jiezhen)+jiezhen.length(), line.length());
				model.setAddress(line);
			} else if(line.contains(jiezhen.replace("镇", ""))) {
				if(!line.contains(jiezhen.replace("镇", "")+"路")&&!line.contains(jiezhen.replace("镇", "")+"街")) {
					line = line.replaceFirst(jiezhen.replace("镇", ""), "");
					model.setAddress(line);
				}
			}
		}
        if (line != null)
            line = line.trim();
        else
            return null;

        model = preProcess(model);
        if (model.getAddress() == null)
            return null;

        return parseAll__(model);
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
