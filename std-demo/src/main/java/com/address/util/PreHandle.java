package com.address.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.address.model.StdModel;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Colin Yan on 2016/7/18.
 */
public class PreHandle {

    public static StdModel handle(StdModel model) {
        String input = model.getAddress();
        if (input == null)
            return null;
        String temp;
        model = removeDistrict(model);
        temp = filterReduplicate2_3(model.getAddress());
        model.setAddress(trimFushi(temp));
        return model;
    }

    /**
     *
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
            }
            result = StringUtils.removeStart(result, d);
        }
        Pattern pattern = Pattern.compile("^([\\u4E00-\\u9FA5]{2,3}[区县])");
        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            result = result.replaceFirst(matcher.group(1), "");
        }

        model.setAddress(result);

        return model;
    }

    private static String[] districts = new String[] { "杨浦区", "宝山区", "嘉定区", "闸北区", "静安区", "浦东新区", "黄浦区",
            "青浦区", "闵行区", "奉贤区", "普陀区", "南汇区", "金山区", "松江区", "崇明区", "崇明县" };
}
