package com.address.util;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.address.model.StdModel;

/**
 * Created by Colin Yan on 2016/7/18.
 */
@SuppressWarnings("all")
public class PreHandle {

    public static StdModel handle(StdModel model) {
        String input = model.getAddress();
        if (input == null)
            return null;
        String temp;
        model = removeDistrict(model);
        model = removeTowm(model);
        temp = filterReduplicate2_3(model.getAddress());
        model.setAddress(trimFushi(temp));
        return model;
    }

    static Set<String> townList = new HashSet<>();

    static {

        String town = "/SH_town_2016_12_16";
        InputStream is = null;
        try {
            is = AddressExtractor.class.getResourceAsStream(town);
            List<String> lines = IOUtils.readLines(is, "utf-8");
            for (String s : lines) {
                townList.add(s);
            }
            System.out.println(townList.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private static StdModel removeTowm(StdModel model) {
        if (null == model)
            return null;

        String line = model.getAddress();
        for (String jiezhen : townList) {
            if (line.contains(jiezhen)) {
                line = line.substring(line.indexOf(jiezhen) + jiezhen.length(), line.length());
                model.setAddress(line);
            } else if (line.contains(jiezhen.replace("镇", ""))) {
                if (!line.contains(jiezhen.replace("镇", "") + "路")
                        && !line.contains(jiezhen.replace("镇", "") + "街")&& jiezhen.replace("镇", "").length()>1) {
                	if(line.indexOf("路")!=-1) {
                		String road = line.substring(0, line.indexOf("路")+1);
                		if(road.length()<4) {
                			line = line.replaceFirst(jiezhen.replace("镇", ""), "");
                            model.setAddress(line);
                		}
                	}
                }
            }
        }
        model.setAddress(line);
        return model;
    }

    /**
     * @param input
     * @param minLen include
     * @return
     */
    public static String filterReduplicate2_3(String input, int minLen) {
        if (input == null)
            return null;
        if (input.length() > minLen) {
            return filterReduplicate2_3(input);
        }
        return input;
    }

    public static String filterReduplicate2_3(String input) {
        if (input == null)
            return null;
        input = input.trim();

        final int len = input.length();
        if (len % 2 == 0) {
            String part1 = input.substring(0, len / 2);
            String part2 = input.substring(len / 2, len);
            if (part1.equals(part2)) {
                return part1;
            }
        }
        if (len % 3 == 0) {
            String part1 = input.substring(0, len / 3);
            String part2 = input.substring(len / 3, len / 3 * 2);
            String part3 = input.substring(len / 3 * 2, len);
            if (part1.equals(part2) && part2.equals(part3)) {
                return part1;
            }
        }
        return input;
    }

    private static String trimFushi(String input) {
        if (input.contains("复式")) {
            return input.replaceAll("[（(]?复式[)）]?", "").replace("+复式", "").replace("[++]复式", "");
        }
        return input;
    }

    private static StdModel removeDistrict(StdModel model) {
        String result = model.getAddress();
        result = StringUtils.removeStart(result, "上海市");
        result = StringUtils.removeStart(result, "上海");
        for (String d : districts) {
            if (result.contains(d)) {
                model.setDistrict(d);
                result = result.replaceAll(d, "");
            }
        }
        for (String d : districtsNoQu) {
            if (result.contains(d)) {
            	if(result.contains("浦东")) {
            		model.setDistrict(d+"新区");
            	} else {
            		model.setDistrict(d+"区");
            	}
            	if(!hasDigit(result)) {
            		result = result.replace(d, "");
            	} else {
            		if(result.indexOf(getNumbers(result))>4) {
            			String comp = result.substring(0, result.indexOf(getNumbers(result)));
            			if(comp.length()>4) {
            				result = result.replace(d, "");
            			}
            		}
            	}
//                result = result.replaceAll(d, "");
            }
        }
        model.setAddress(result);

        return model;
    }
    public static boolean hasDigit(String content) {

    	boolean flag = false;

    	Pattern p = Pattern.compile(".*\\d+.*");

    	Matcher m = p.matcher(content);

    	if (m.matches())

    	flag = true;

    	return flag;

    	}
    private static String[] districts = new String[] { "杨浦区", "宝山区", "嘉定区", "闸北区", "静安区", "浦东新区", "黄浦区",
            "青浦区", "闵行区", "奉贤区", "普陀区", "南汇区", "金山区", "松江区", "崇明区", "崇明县", "虹口区", "长宁区", "徐汇区", "卢湾区" };
    public static String[] districtsNoQu = {"黄浦","徐汇","长宁","静安","普陀","闸北","虹口","杨浦","闵行","宝山","嘉定","浦东","金山","松江","青浦","奉贤","崇明","卢湾"};
    
    public static String getNumbers(String content) {  
        Pattern pattern = Pattern.compile("\\d+");  
        Matcher matcher = pattern.matcher(content);  
        while (matcher.find()) {  
           return matcher.group(0);  
        }  
        return "";  
    }  
}
