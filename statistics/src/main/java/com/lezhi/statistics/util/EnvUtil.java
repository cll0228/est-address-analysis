package com.lezhi.statistics.util;

/**
 * Created by Colin Yan on 2017/5/25.
 */
public class EnvUtil {

    private static final boolean MOCK_MODE = "true".equals(System.getProperty("mock_mode"));

    public static boolean isMockMode() {
        return MOCK_MODE;
    }
}
