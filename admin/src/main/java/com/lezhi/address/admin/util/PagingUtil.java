package com.lezhi.address.admin.util;

import java.io.IOException;
import java.util.*;

/**
 * Created by Colin Yan on 2016/8/5.
 */
public class PagingUtil {


    public interface CallbackCollection<T> {
        boolean onPage(int pageNo, Collection<T> sub, int begin, int end, int realPageSize, int pageSize, boolean isFirst, boolean isLast, int totalSize, int pageCount);
    }

    public static <T> void pageCollection(Collection<T> collection, int pageSize, CallbackCollection<T> callback) {
        if (collection == null || collection.isEmpty() || pageSize <= 0 || callback == null)
            return;

        Collection<T> sub;
        if (collection instanceof List) {
            sub = new ArrayList<T>();
        } else if (collection instanceof Set) {
            sub = new HashSet<>();
        } else {
            throw new RuntimeException("not support Collection type");
        }

        final int totalSize = collection.size();
        final int pageCount = totalSize / pageSize + (totalSize % pageSize > 0 ? 1 : 0);
        Iterator<T> it = collection.iterator();
        for (int i = 0; i < pageCount; i++) {
            final int begin = i * pageSize;
            final int end = i == pageCount - 1 ? totalSize : begin + pageSize;
            sub.clear();
            for (int n = begin; n < end; n++) {
                if (!it.hasNext()) {
                    throw new RuntimeException();
                }
                sub.add(it.next());
            }
            if (!callback.onPage(i + 1, sub, begin, end, end - begin, pageSize, i == 0, i == pageCount - 1, totalSize, pageCount))
                break;
        }
    }

    public interface CallbackIndex {
        boolean onPage(int pageNo, int begin, int end, int realPageSize, int pageSize, boolean isFirst, boolean isLast, int totalSize, int pageCount) throws IOException;
    }

    public static void pageIndex(int totalSize, int pageSize, CallbackIndex callbackIndex) throws IOException {

        final int pageCount = totalSize / pageSize + (totalSize % pageSize > 0 ? 1 : 0);
        for (int i = 0; i < pageCount; i++) {
            final int begin = i * pageSize;
            final int end = i == pageCount - 1 ? totalSize : begin + pageSize;
            if (!callbackIndex.onPage(i + 1, begin, end, end - begin, pageSize, i == 0, i == pageCount - 1, totalSize, pageCount))
                break;
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + 1);
        }
        pageCollection(list, 10, (pageNo, sub, begin, end, realPageSize, pageSize, isFirst, isLast, totalSize, pageCount) -> {
            System.out.println(sub);
            return true;
        });
    }
}
