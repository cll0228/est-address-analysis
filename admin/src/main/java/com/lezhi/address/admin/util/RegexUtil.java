package com.lezhi.address.admin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Colin Yan on 2016/11/25.
 */
public class RegexUtil {

    public static String[] regexGroup(String str, Pattern p) {
        try {
            Matcher m = p.matcher(str);
            if (m.find()) {
                List<String> result = new ArrayList<>();
                int gn = m.groupCount();
                for (int i = 1; i <= gn; i++) {
                    String value = m.group(i);
                    result.add(value);
                }
                return result.toArray(new String[result.size()]);
            }
            return null;
        } catch (Throwable t) {
            System.err.println("str:" + str + ",p:" + p);
            t.printStackTrace();
            throw t;
        }
    }

    public static Map<String, String> regexGroup(String str, Pattern p, String... groupNames) {
        try {
            Matcher m = p.matcher(str);
            if (m.find()) {
                Map<String, String> result = new HashMap<>();
                for (String n : groupNames) {
                    result.put(n, m.group(n));
                }
                return result;
            }
            return null;
        } catch (Throwable t) {
            System.err.println("str:" + str + ",p:" + p);
            t.printStackTrace();
            throw t;
        }
    }

    public static String regexGroup(String str, Pattern p, int group) {
        try {
            Matcher m = p.matcher(str);
            String value = null;

            if (m.find()) {
                if (group > m.groupCount())
                    return null;
                value = m.group(group);
            }
            return value;
        } catch (Throwable t) {
            System.err.println("str:" + str + ",p:" + p + ",group:" + group);
            t.printStackTrace();
            throw t;
        }
    }

}
