package com.zk.zy.util;


import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class LocalDateUtil {

    /**
     * 日期格式yyyy-MM-dd.
     */
    public static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss.
     */
    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间时间戳（秒）.
     *
     * @return long.
     */
    public static long getCurrentEpochSecond() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 获取指定时间时间戳（秒）.
     *
     * @param localDateTime localDateTime,
     * @return long.
     */
    public static long getEpochSecond(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * String 转 LocalDate.
     *
     * @param date date.
     * @return LocalDate.
     */
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    /**
     * String 转 LocalDateTime.
     *
     * @param datetime datetime.
     * @return LocalDateTime.
     */
    public static LocalDateTime stringToLocalDateTime(String datetime) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    /**
     * LocalDate 转 String.
     *
     * @param localDate localDate.
     * @return String.
     */
    public static String localDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }


    /**
     * LocalDateTime  转 String.
     *
     * @param localDateTime localDateTime.
     * @return String.
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    /**
     * LocalDate转Date.
     *
     * @param localDate localDate.
     * @return Date.
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());

    }

    /**
     * Date转LocalDate.
     *
     * @param date date.
     * @return LocalDate.
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }

    /**
     * Date转LocalDateTime.
     *
     * @param date Date对象.
     * @return LocalDateTime.
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date.
     *
     * @param dateTime LocalDateTime对象.
     * @return Date.
     */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式化时间-默认yyyy-MM-dd HH:mm:ss格式.
     *
     * @param dateTime LocalDateTime对象 .
     * @return String.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATE_TIME_PATTERN);
    }

    /**
     * 按pattern格式化时间-默认yyyy-MM-dd HH:mm:ss格式.
     *
     * @param dateTime LocalDateTime对象.
     * @param pattern  要格式化的字符串.
     * @return String.
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        if (pattern == null || pattern.isEmpty()) {
            pattern = DATE_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 获取今天的00:00:00.
     *
     * @return String.
     */
    public static String getDayStart() {
        return getDayStart(LocalDateTime.now());
    }

    /**
     * 获取今天的23:59:59.
     *
     * @return String.
     */
    public static String getDayEnd() {
        return getDayEnd(LocalDateTime.now());
    }

    /**
     * 获取某天的00:00:00.
     *
     * @param dateTime dateTime.
     * @return String.
     */
    public static String getDayStart(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(LocalTime.MIN));
    }

    /**
     * 获取某天的00:00:00.
     * @param dateTime
     * @return
     */
    public static LocalDateTime getHeadTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.with(LocalTime.MIN);
    }

    /**
     * 获取某天的00:00:00.
     * @param date
     * @return
     */
    public static LocalDateTime getHeadTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 获取某天的23:59:59.
     *
     * @param dateTime dateTime.
     * @return String.
     */
    public static String getDayEnd(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(LocalTime.MAX));
    }

    /**
     * 获取某天的23:59:59.
     * @param dateTime
     * @return
     */
    public static LocalDateTime getTailTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.with(LocalTime.MAX);
    }

    /**
     * 获取某天的23:59:59.
     * @param date
     * @return
     */
    public static LocalDateTime getTailTime(LocalDate date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 获取本月第一天的00:00:00.
     *
     * @return String.
     */
    public static String getFirstDayOfMonth() {
        return getFirstDayOfMonth(LocalDateTime.now());
    }

    /**
     * 获取本月最后一天的23:59:59.
     *
     * @return String.
     */
    public static String getLastDayOfMonth() {
        return getLastDayOfMonth(LocalDateTime.now());
    }

    /**
     * 获取某月第一天的00:00:00
     *
     * @param dateTime LocalDateTime对象.
     * @return String.
     */
    public static String getFirstDayOfMonth(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN));
    }

    /**
     * 获取某月最后一天的23:59:59.
     *
     * @param dateTime LocalDateTime对象.
     * @return String.
     */
    public static String getLastDayOfMonth(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX));
    }

    /**
     * 获取指定时间是周几.
     *
     * @param time time.
     * @return int .
     */
    public static int getWeek(LocalDateTime time) {
        return time.getDayOfWeek().getValue();
    }


    /**
     * 获取指定时间为字符串每周几.
     *
     * @param time time.
     * @return String.
     */
    public static String geteveryWeekStr(LocalDateTime time) {
        String weekStr = "";
        int weekIndex = time.getDayOfWeek().getValue();
        switch (weekIndex) {
            case 1:
                weekStr = "每周一";
                break;
            case 2:
                weekStr = "每周二";
                break;
            case 3:
                weekStr = "每周三";
                break;
            case 4:
                weekStr = "每周四";
                break;
            case 5:
                weekStr = "每周五";
                break;
            case 6:
                weekStr = "每周六";
                break;
            case 7:
                weekStr = "每周日";
                break;
            default:
        }
        return weekStr;
    }

    /**
     * 判断t1早于t2.
     *
     * @param t1 LocalDateTime.
     * @param t2 LocalDateTime.
     * @return boolean.
     */
    public static boolean isBefore(LocalDateTime t1, LocalDateTime t2) {
        return t1.isBefore(t2);
    }

    /**
     * 判断t1晚于t2.
     *
     * @param t1 LocalDateTime.
     * @param t2 LocalDateTime.
     * @return boolean.
     */
    public static boolean isAfter(LocalDateTime t1, LocalDateTime t2) {
        return t1.isAfter(t2);
    }

    /**
     * 判断时间(LocalDateTime)是否在本月之内.
     *
     * @param localDateTime localDateTime.
     * @return boolean.
     */
    public static boolean isInCurrentMonth(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDate now = LocalDate.now();
        return localDate.isAfter(now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())) &&
                localDate.isBefore(now.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 判断时间(LocalDate)是否在本月之内.
     *
     * @param localDate localDate.
     * @return boolean.
     */
    public static boolean isInCurrentMonth(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        return localDate.isAfter(now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())) &&
                localDate.isBefore(now.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
    }

    /**
     * 获取指定日期的毫秒.
     *
     * @param time LocalDateTime.
     * @return Long.
     */
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的秒.
     *
     * @param time LocalDateTime.
     * @return Long.
     */
    public static Long getSecondsByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*.
     *
     * @param startTime startTime.
     * @param endTime   endTime.
     * @param field     单位(年月日时分秒).
     * @return long.
     **/
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12 + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.* .
     *
     * @param startDate startDate.
     * @param endDate   endDate.
     * @return Double.
     **/
    public static Double betweenTwoTime(Date startDate, Date endDate) {
        Integer datePoor = getDatePoor(startDate, endDate);
        BigDecimal b = BigDecimal.valueOf((double) datePoor / 60);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * getDatePoor.
     *
     * @param startDate startDate.
     * @param endDate   endDate.
     * @return Integer.
     */
    public static Integer getDatePoor(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        Long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        Long mymint = diff / 1000 / 60;
        int intValue = mymint.intValue();
        return intValue;
    }

    /**
     * 获取当天还剩多少时间.
     *
     * @param field 单位(时分秒).
     * @return 当天还剩多少时间.
     */
    public static long getTodayRemainSecond(ChronoUnit field) {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return field.between(LocalDateTime.now(), midnight);
    }

    /**
     * 获取指定日期比次日0点已过去时间.
     *
     * @param time  指定日期.
     * @param field 单位(时分秒).
     * @return 已过去时间.
     */
    public static long getPassedTimeOfTheDay(Date time, ChronoUnit field) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
        return field.between(localDateTime.with(LocalTime.MIN), localDateTime);
    }

}
