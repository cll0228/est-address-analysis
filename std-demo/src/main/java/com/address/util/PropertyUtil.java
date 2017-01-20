package com.address.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by chendl on 2016/07/11.
 */
public class PropertyUtil {
    private static final HashMap<String, Properties> props = new HashMap<String, Properties>();

    public static final String get(String type, String key) {
        return getProps(type).getProperty(key);
    }

    private static final Properties getProps(String type) {
        Properties prop = props.get(type);
        if (null == prop) {
            prop = loadProps(type);
            props.put(type, prop);
        }
        return prop;
    }

    private static final Properties loadProps(String type) {
        Properties prop = new Properties();
//        BufferedReader in = null;
        InputStream in = null;
        try {
//            in = new BufferedReader(new InputStreamReader(PropertyUtil.class.getResourceAsStream(type + ".properties")));
            in = PropertyUtil.class.getResourceAsStream(type + ".properties");
            prop.load(in);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
}
