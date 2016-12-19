package com.address.util;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                    line = line.replaceFirst(jiezhen.replace("镇", ""), "");
                    model.setAddress(line);
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
        model.setAddress(result);

        return model;
    }

    private static String[] districts = new String[] { "杨浦区", "宝山区", "嘉定区", "闸北区", "静安区", "浦东新区", "黄浦区",
            "青浦区", "闵行区", "奉贤区", "普陀区", "南汇区", "金山区", "松江区", "崇明区", "崇明县", "虹口区", "长宁区", "徐汇区", "卢湾区" };
}
