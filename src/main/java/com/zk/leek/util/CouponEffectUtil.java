package com.zk.leek.util;

/**
 * CouponEffectUtil 工具类.
 */
public class CouponEffectUtil {

    public static final String HOUR = "HOUR";

    public static final String WEEK = "WEEK";

    public static final String DAY = "DAY";

    public static final String MONTH = "MONTH";

    public static final String YEAR = "YEAR";

    /**
     * 获取 value .
     *
     * @param util util.
     * @return String.
     */
    public static String getValue(String util) {
        if (util.equals(HOUR)) {
            return "小时";
        } else if (util.equals(WEEK)) {
            return "周";
        } else if (util.equals(DAY)) {
            return "天";
        } else if (util.equals(MONTH)) {
            return "月";
        } else if (util.equals(YEAR)) {
            return "年";
        }
        return null;
    }
}
