package com.shiln.user.util;

import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {
    public static <T> boolean isNull(T... objects) {
        for (T obj : objects) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean notNull(T... objects) {
        for (T obj : objects) {
            if (null == obj) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(String... objects) {
        for (String obj : objects) {
            if (null == obj || obj.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notBlank(String... objects) {
        for (String obj : objects) {
            if (null == obj || obj.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmpty(Collection collection) {

        if (isNull(collection)) {
            return false;
        }
        if (collection.size() <= 0) {
            return false;
        }

        return true;
    }

    public static boolean isEmpty(Collection collection) {
        if (isNull(collection)) {
            return true;
        }
        if (collection.size() <= 0) {
            return true;
        }
        return false;
    }

    public static <T extends Collection> boolean hasTheSameItem(T main, T second) {
        if (CommonUtils.notEmpty(main) && CommonUtils.notEmpty(second)) {
            for (Object objects : main) {
                if (second.contains(main)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> trimAndSplitBySemicolon(String s) {
        return (ArrayList) Arrays.asList(s.trim().split("\\s*;\\s*"));
    }

    public static List<String> listForStr(List<String> reses, String reg) {
        if (reses == null || reses.isEmpty() || StringUtils.isEmpty(reg)) {
            return null;
        }
        Set<String> set = new HashSet<String>();
        for (String res : reses) {
            if (!StringUtils.isEmpty(res)) {
                set.addAll(new HashSet<String>(Arrays.asList(res.split(reg))));
            }
        }
        return new ArrayList<String>(set);
    }


    /**时间+随机数的生产方法 **/
    public static String random() {

        Random random = new Random();
        return Integer.toString(random.nextInt(Integer.MAX_VALUE), 36);
    }

    public static String time(String format) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return df.format(date);
    }

    public static String newTraceId(){
        String date = time("yyyyMMddHHmmssSSS");
        String rnd = random();
        return date + rnd;
    }



}
