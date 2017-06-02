package com.lezhi.statistics.util;

import java.util.Map;

/**
 * Created by Colin Yan on 2017/6/2.
 */
public class PageUtil {

    public static void emptyPage(Map<String, Object> result) {
        result.put("pageNo", null);
        result.put("pageSize", null);
        result.put("realPageSize", null);
        result.put("totalPageCount", null);
        result.put("totalCount", 0);
        result.put("isFirstPage", null);
        result.put("isLastPage", null);
    }
}
