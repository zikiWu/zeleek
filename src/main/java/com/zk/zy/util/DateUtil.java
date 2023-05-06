package com.zk.zy.util;


import com.zk.zy.constant.DurationConstants;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 日期工具类 不建议使用ThreadLocal保持线程安全,尽量使用局部变量,抽取公共方法,传入pattern和locate
 * 该工具类目前引用地方较多,不做较大改动.基本保持原样
 */
public class DateUtil {

    public static final String[] WEEK_STR = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    public static final String[] WEEK_STR_NU = {"一", "二", "三", "四", "五", "六", "日"};

    public static final String MONTH_DATE = "MM-dd";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_MONTH_DATE_FORMAT = new ThreadLocal();

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_DATE_FORMAT = new ThreadLocal();

    public static final String YEAR_MONTH = "yyyy-MM";
    private static final ThreadLocal<SimpleDateFormat> YEAR_MONTH_FORMAT = new ThreadLocal();

    public static final String TIME_FORMAT = "HH:mm:ss";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_TIME_FORMAT = new ThreadLocal();

    public static final String PARAM_DISPLAY_DATEFORMAT = "dd-MMM-yyyy";

    public static final String FULL = "yyyy-MM-dd HH:mm:ss";

    public static final String DAY_FORMAT = "yyyy.MM.dd";

    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";

    public static final String HHmm = "HH:mm";

    public static final String DATE_TIME_FORMAT_FOR_FILE_NAME = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final int NO_CHANGE = -1;

    public static final int MILLIS_OF_DAY = 24 * 60 * 60 * 1000;

    public static final String YEAR_MONTH_INT = "yyyyMM";

    /**
     * 获取 SimpleDateFormat .
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleMonthDateFormat() {
        SimpleDateFormat format = THREAD_LOCAL_MONTH_DATE_FORMAT.get();
        if (format == null) {

            format = new SimpleDateFormat(MONTH_DATE);
            THREAD_LOCAL_MONTH_DATE_FORMAT.set(format);
        }
        return format;
    }

    /**
     * 将时间秒数转成 计算成时分秒 HH:mm:ss，，如果millis为0 则返回 00：00：00.
     *
     * @param millis 秒数.
     * @return 返回的 HH:mm:ss 字符串.
     */
    public static String timeMillis2HMS(Long millis) {
        String hoursStr = "00", minutesStr = "00", secondsStr = "00";
        if (millis <= 0) {
            return hoursStr + ":" + minutesStr + ":" + secondsStr;
        }

        // 时
        long hour = millis / 3600;
        if (hour < 10) {
            hoursStr = "0" + hour;
        } else {
            hoursStr = hour + "";
        }

        // 分
        long minute = (millis % 3600) / 60;
        if (minute < 10) {
            minutesStr = "0" + minute;
        } else {
            minutesStr = minute + "";
        }

        // 秒
        long second = millis % 60;
        if (second < 10) {
            secondsStr = "0" + second;
        } else {
            secondsStr = second + "";
        }

        return hoursStr + ":" + minutesStr + ":" + secondsStr;
    }

    /**
     * 获取上个月第一天.
     *
     * @return String.
     */
    public static String getLastMonthFirstDayStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //获取当前时间上一个月
        cal.add(Calendar.MONTH, -1);
        //获取上个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return df.format(cal.getTime());

    }

    /**
     * 判断时间是否当天.
     *
     * @param date Date.
     * @return boolean.
     */
    public static boolean isNow(Date date) {
        // 当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);
    }

    /**
     * 返回小时.
     *
     * @param time Date.
     * @return int.
     */
    public static int Hour(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("HHmm");
        return Integer.parseInt(st.format(time));
    }

    /**
     * 返回天.
     *
     * @param time date
     * @return int.
     */
    public static int DAY(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(st.format(time));
    }

    /**
     * 获取明天的日期字符串.
     *
     * @return String.
     */
    public static String tomorrowDateStr() {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, 1);

        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 获取明天的日期字符串.
     *
     * @param date date.
     * @return. Date.
     */
    public static Date tomorrowDateStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, 1);

        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取昨天的日期字符串.
     *
     * @param date date.
     * @return. Date.
     */
    public static Date yesterdayDateStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, -1);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取上个月最后一天.
     *
     * @return. String.
     */
    public static String getLastMonthLastDayStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //获取当前时间上一个月
        cal.add(Calendar.MONTH, -1);
        //获取上个月的最后一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return df.format(cal.getTime());
    }


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat format = THREAD_LOCAL_DATE_FORMAT.get();
        if (format == null) {

            format = new SimpleDateFormat(DATE_FORMAT);
            THREAD_LOCAL_DATE_FORMAT.set(format);
        }
        return format;
    }


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleYearMonthFormat() {
        SimpleDateFormat format = YEAR_MONTH_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(YEAR_MONTH);
            YEAR_MONTH_FORMAT.set(format);
        }
        return format;
    }


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleTimeFormat() {
        SimpleDateFormat format = THREAD_LOCAL_TIME_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(TIME_FORMAT);
            THREAD_LOCAL_TIME_FORMAT.set(format);
        }
        return format;
    }

    private static final String TIME_FORMAT_NO_SECOND = "HH:mm";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_TIME_FORMAT_NO_SECOND = new ThreadLocal();

    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleTimeFormatNoSecond() {
        SimpleDateFormat format = THREAD_LOCAL_TIME_FORMAT_NO_SECOND.get();
        if (format == null) {
            format = new SimpleDateFormat(TIME_FORMAT_NO_SECOND);
            THREAD_LOCAL_TIME_FORMAT_NO_SECOND.set(format);
        }
        return format;
    }

    private static final String DATE_TIME_NO_SECOND_FORMAT = "yyyy-MM-dd HH:mm";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_DATE_TIME_NO_SECOND_FORMAT_FORMAT = new ThreadLocal();


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleDateTimeNoSecondFormat() {
        SimpleDateFormat format = THREAD_LOCAL_DATE_TIME_NO_SECOND_FORMAT_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(DATE_TIME_NO_SECOND_FORMAT);
            THREAD_LOCAL_DATE_TIME_NO_SECOND_FORMAT_FORMAT.set(format);
        }
        return format;
    }


    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_DATE_TIME_FORMAT = new ThreadLocal();

    public static final SimpleDateFormat getSimpleDateTimeFormat() {
        SimpleDateFormat format = THREAD_LOCAL_DATE_TIME_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(DATE_TIME_FORMAT);
            THREAD_LOCAL_DATE_TIME_FORMAT.set(format);
        }
        return format;
    }


    private static final String EMI_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final ThreadLocal<SimpleDateFormat> EMI_THREAD_LOCAL_DATE_TIME_FORMAT = new ThreadLocal();


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getEmiSimpleDateTimeFormat() {
        SimpleDateFormat format = EMI_THREAD_LOCAL_DATE_TIME_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(EMI_DATE_TIME_FORMAT);
            EMI_THREAD_LOCAL_DATE_TIME_FORMAT.set(format);
        }
        return format;
    }

    public static final String DATE_FORMAT_C = "yyyy年MM月dd日";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_DATE_FORMAT_C = new ThreadLocal();


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getSimpleDateFormatC() {
        SimpleDateFormat format = THREAD_LOCAL_DATE_FORMAT_C.get();
        if (format == null) {
            format = new SimpleDateFormat(DATE_FORMAT_C);
            THREAD_LOCAL_DATE_FORMAT_C.set(format);
        }
        return format;
    }

    private static final String ZERO_CLOCK_FORMAT = "yyyy-MM-dd 00:00:00";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_ZERO_CLOCK_FORMAT = new ThreadLocal();

    public static final SimpleDateFormat getZeroClockFormat() {
        SimpleDateFormat format = THREAD_LOCAL_ZERO_CLOCK_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(ZERO_CLOCK_FORMAT);
            THREAD_LOCAL_ZERO_CLOCK_FORMAT.set(format);
        }
        return format;
    }

    private static final String TWENTY_CLOCK_FORMAT = "yyyy-MM-dd 23:59:59";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL_TWENTY_CLOCK_FORMAT = new ThreadLocal();


    /**
     * 获取 SimpleDateFormat.
     *
     * @return SimpleDateFormat.
     */
    public static final SimpleDateFormat getTwentyClockFormat() {
        SimpleDateFormat format = THREAD_LOCAL_TWENTY_CLOCK_FORMAT.get();
        if (format == null) {
            format = new SimpleDateFormat(TWENTY_CLOCK_FORMAT);
            THREAD_LOCAL_TWENTY_CLOCK_FORMAT.set(format);
        }
        return format;
    }


    /**
     * Get an instance with given zone and date. *.
     *
     * @param zoneID zoneID.
     * @param date   date.
     * @return Calendar .
     */
    public static Calendar getCalendar(String zoneID, Date date) {
        TimeZone tz = TimeZone.getTimeZone(zoneID);
        Calendar c = Calendar.getInstance(tz);
        c.setTime(date);
        return c;
    }

    /**
     * Truncate the specified (signed) fields, based on the given time format and
     * the calendar's rules.
     *
     * @param zoneID zoneID.
     * @param date   date.
     * @param field  field.
     * @param amount
     * @param format : Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calend
     *               ar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND,
     *               Calendar.MILLISECOND.
     * @return Calendar.
     */
    public static Calendar truncTimeByZone(String zoneID, Date date, int field, int amount, int format) {
        /* Get an instance with given zone and date. */
        Calendar c = getCalendar(zoneID, date);

        /*
         * Adds the specified (signed) amount of time to the given time field, based on
         * the calendar's rules.
         */
        c.add(field, amount);

        /*
         * Trucate the specified (signed) fields, based on the given time form at and
         * the calendar's rules.
         */
        switch (format) {
            case Calendar.YEAR:
                c.set(Calendar.MONTH, 0);
            case Calendar.MONTH:
                c.set(Calendar.DAY_OF_MONTH, 0);
            case Calendar.DAY_OF_MONTH:
                c.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR_OF_DAY:
                c.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                c.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                c.set(Calendar.MILLISECOND, 0);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException();
        }
        return c;
    }

    /**
     * Usage return ceiling days between the date1 and date2 at the designated time
     * zone.
     *
     * @param zoneID zoneID.
     * @param date1  date1.
     * @param date2  date2.
     * @return int.
     */
    public static int ceilIntervalDay(String zoneID, Date date1, Date date2) {

        Calendar c1 = truncTimeByZone(zoneID, date1, Calendar.MILLISECOND, 0, Calendar.DAY_OF_MONTH);
        Calendar c2 = truncTimeByZone(zoneID, date2, Calendar.MILLISECOND, 0, Calendar.DAY_OF_MONTH);
        int interval = (int) Math.round(((double) (c2.getTimeInMillis() - c1.getTimeInMillis())) / 86400000.0) + 1;
        return interval;
    }

    /**
     * getFormattedDate.
     *
     * @param pattern pattern
     * @param val     val.
     * @return Date.
     */
    public static Date getFormattedDate(String pattern, String val) {
        if ("".equals(val) || val == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(val, pos);
    }

    /**
     * showFormatedDate .
     *
     * @param pattern pattern.
     * @param date    date.
     * @return String.
     */
    public static String showFormatedDate(String pattern, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(date);
        return dateString;
    }


    /**
     * showFormatedDate .
     *
     * @param longDate longDate.
     * @return String
     */
    public static String showFormatedDate(String longDate) {
        DateFormat format1 = new SimpleDateFormat("MM月dd日 hh:mm");
        return format1.format(new Date(new Long(longDate)));
    }

    /**
     * Usage return day different in double for date1 and date2.
     *
     * @param date1 date1.
     * @param date2 date2.
     * @return double
     */
    public static double diffDates(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        return (double) (t2 - t1) / 86400000;
    }

    /**
     * Usage return day different in double for date1 and date2.
     *
     * @param date1 date1.
     * @param date2 date2.
     * @return double.
     */
    public static double diffMinutes(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        return (double) (t2 - t1) / 60000;
    }

    /**
     * Usage return day different in int for date1 and date2.
     *
     * @param date1 date1.
     * @param date2 date2.
     * @return int.
     */
    public static int difHours(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        return (int) (t2 - t1) / 3600000;
    }

    /**
     * diffSeconds.
     *
     * @param date1 date1.
     * @param date2 date2.
     * @return double.
     */
    public static double diffSeconds(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        return (double) (t2 - t1) / 1000;
    }

    /**
     * diffSeconds.
     *
     * @param date1 date1.
     * @param date2 date2.
     * @return double.
     */
    public static long diffMs(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        return t2 - t1;
    }

    /**
     * 返回Date.
     *
     * @param date date.
     * @return Date.
     */
    public static Date roundToHour(Date date) {
        // msec in hour
        long dateLong = date.getTime() / 3600000L;
        return new Date(dateLong * 3600000L);
    }

    /**
     * 返回Date.
     *
     * @param date date.
     * @return Date.
     */
    public static Date roundToLaterHour(Date date) {
        // msec in hour
        long dateLong = date.getTime();
        if (dateLong % 3600000L == 0) {
            return new Date(dateLong);
        } else {
            return new Date((dateLong / 3600000L + 1) * 3600000L);
        }
    }

    /**
     * 返回Date.
     *
     * @param date   date.
     * @param second second.
     * @return Date.
     */
    public static Date addSeconds(Date date, int second) {
        long dateTime = date.getTime() + (long) second * 1000L;
        return new Date(dateTime);
    }

    /**
     * 返回Date.
     *
     * @param date   date.
     * @param minute minute.
     * @return Date.
     */
    public static Date addMinutes(Date date, int minute) {
        long dateTime = date.getTime() + (long) minute * 60000L;
        return new Date(dateTime);
    }

    /**
     * 返回Date.
     *
     * @param date   date.
     * @param minute minute.
     * @return Date.
     */
    public static Date subMinutes(Date date, int minute) {
        long dateTime = date.getTime() - (long) minute * 60000L;
        return new Date(dateTime);
    }

    /**
     * 返回Date.
     *
     * @param date date.
     * @param hour hour.
     * @return Date.
     */
    public static Date addHours(Date date, int hour) {
        // msec in hour
        long dateLong = date.getTime() + (long) hour * 3600000L;
        return new Date(dateLong);
    }

    /**
     * 返回Date.
     *
     * @param date date.
     * @param hour hour.
     * @return Date.
     */
    public static Date subHours(Date date, int hour) {
        // msec in hour
        long dateLong = date.getTime() - (long) hour * 3600000L;
        return new Date(dateLong);
    }

    /**
     * 返回Date.
     *
     * @param date date.
     * @param day  day.
     * @return Date.
     */
    public static Date addDays(Date date, int day) {
        return addHours(date, day * 24);
    }

    /**
     * getCurrentZeroDate.
     *
     * @return date.
     */
    public static Date getCurrentZeroDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 加月.
     *
     * @param date  date.
     * @param month month.
     * @return Date.
     */
    public static Date addMonths(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 加年.
     *
     * @param date date.
     * @param year year.
     * @return Date.
     */
    public static Date addYears(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * 加天.
     *
     * @param date     date.
     * @param duration duration.
     * @return Date.
     */
    public static Date addDays(Date date, String duration) {
        if (DurationConstants.DURATION_DAY.equals(duration)) {
            return addDays(date, 1);
        } else if (DurationConstants.DURATION_WEEK.equals(duration)) {
            return addDays(date, 7);
        } else if (DurationConstants.DURATION_TWO_WEEK.equals(duration)) {
            return addDays(date, 14);
        } else if (DurationConstants.DURATION_MONTH.equals(duration)) {
            return addMonths(date, 1);
        } else if (DurationConstants.DURATION_SEASON.equals(duration)) {
            return addMonths(date, 3);
        } else if (DurationConstants.DURATION_HALF_YEAR.equals(duration)) {
            return addMonths(date, 6);
        } else if (DurationConstants.DURATION_YEAR.equals(duration)) {
            return addYears(date, 1);
        }
        return null;
    }

    /**
     * precedingDays.
     *
     * @param date     date.
     * @param duration duration.
     * @return Date.
     */
    public static Date precedingDays(Date date, String duration) {
        if (DurationConstants.DURATION_DAY.equals(duration)) {
            return addDays(date, -1);
        } else if (DurationConstants.DURATION_WEEK.equals(duration)) {
            return addDays(date, -7);
        } else if (DurationConstants.DURATION_TWO_WEEK.equals(duration)) {
            return addDays(date, -14);
        } else if (DurationConstants.DURATION_MONTH.equals(duration)) {
            return addMonths(date, -1);
        } else if (DurationConstants.DURATION_SEASON.equals(duration)) {
            return addMonths(date, -3);
        } else if (DurationConstants.DURATION_HALF_YEAR.equals(duration)) {
            return addMonths(date, -6);
        } else if (DurationConstants.DURATION_YEAR.equals(duration)) {
            return addYears(date, -1);
        }
        return null;
    }

    /**
     * getDateInGMT.
     *
     * @return Date.
     */
    public static Date getDateInGMT() {
        return new Date(createDateToMinute().getTime() - TimeZone.getDefault().getRawOffset());
    }

    /**
     * getDateAccurateInGMT.
     *
     * @return Date.
     */
    public static Date getDateAccurateInGMT() {
        return new Date(createDateToSecond().getTime() - TimeZone.getDefault().getRawOffset());
    }

    /**
     * getDateInGMT.
     *
     * @param date date.
     * @return date.
     */
    public static Date getDateInGMT(Date date) {
        return new Date(date.getTime() - TimeZone.getDefault().getRawOffset());
    }

    /**
     * trimSeconds.
     *
     * @param date date.
     * @return date.
     */
    public static Date trimSeconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * trimSecondsToEnd.
     *
     * @param date date.
     * @return date.
     */
    public static Date trimSecondsToEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * createDateToMilliSecond.
     *
     * @return date.
     */
    public static Date createDateToMilliSecond() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    /**
     * createDateToSecond.
     *
     * @return date.
     */
    public static Date createDateToSecond() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * createDateToMinute.
     *
     * @return date.
     */
    public static Date createDateToMinute() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * createDateToHour.
     *
     * @return date.
     */
    public static Date createDateToHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * createDate.
     *
     * @return date.
     */
    public static Date createDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * createDate.
     *
     * @param day   day.
     * @param month month/.
     * @param year  month.
     * @return date.
     */
    public static Date createDate(int year, int month, int day) {
        return setCalendar(year, month - 1, day, 0, 0, 0, 0).getTime();
    }

    /**
     * createDate.
     *
     * @param day    day.
     * @param hour   hour.
     * @param minute minute.
     * @param month  month/.
     * @param year   month.
     * @return date.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = setCalendar(year, month, day, hour, minute, 0, 0);
        return cal.getTime();
    }

    /**
     * createDate.
     *
     * @param day         day.
     * @param hour        hour.
     * @param minute      minute.
     * @param month       month/.
     * @param year        month.
     * @param millisecond millisecond.
     * @param second      second.
     * @return date.
     */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar cal = setCalendar(year, month, day, hour, minute, second, millisecond);
        return cal.getTime();
    }

    /**
     * 设置日期类
     *
     * @param year        年
     * @param month       月
     * @param day         天
     * @param hour        时3
     * @param minute      分
     * @param second      秒
     * @param millisecond 毫秒
     * @return
     */
    private static Calendar setCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal;
    }

    /**
     * getDateInLocal.
     *
     * @param gmtDate gmtDate.
     * @return Date.
     */
    public static Date getDateInLocal(Date gmtDate) {
        return new Date(gmtDate.getTime() + TimeZone.getDefault().getRawOffset());
    }

    /**
     * getSQLDateString.
     *
     * @param date gmtDate.
     * @return Date.
     */
    public static String getSQLDateString(Date date) {
        return "to_date('" + showFormatedDate("MM/dd/yyyy HH:mm:ss", date) + "','MM/DD/YYYY HH24:MI:SS')";
    }

    /**
     * getSQLDateStringWithoutTime.
     *
     * @param date gmtDate.
     * @return Date.
     */
    public static String getSQLDateStringWithoutTime(Date date) {
        return "to_date('" + showFormatedDate("MM/dd/yyyy", date) + "','MM/DD/YYYY')";
    }

    /**
     * 获取年.
     *
     * @param aDate Date.
     * @return Date.
     */
    public static int getYear(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取字符串年份.
     *
     * @param aDate date.
     * @return String.
     */
    public static String getStringYear(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return String.valueOf(c.get(Calendar.YEAR));
    }

    /**
     * 获取月.
     *
     * @param aDate date.
     * @return int.
     */
    public static int getMonth(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.MONTH);
    }

    /**
     * 返回月份字符串（1-12） 未补0.
     *
     * @param date date.
     * @return String.
     */
    public static String getStringMonth(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer month = c.get(Calendar.MONTH);
        return String.valueOf(month + 1);

    }

    /**
     * 返回月份字符串，1-9月补0 （01-12）.
     *
     * @param date date.
     * @return String.
     */
    public static String getFullStringMonth(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer month = c.get(Calendar.MONTH);
        if (month < 10) {
            return String.format("0%d", month + 1);
        }
        return String.valueOf(month + 1);

    }

    /**
     * getDayOfWeek.
     *
     * @param aDate date.
     * @return int .
     */
    public static int getDayOfWeek(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * getDayOfMonth.
     *
     * @param aDate date.
     * @return int .
     */
    public static int getDayOfMonth(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * getDay.
     *
     * @param aDate date.
     * @return int.
     */
    public static int getDay(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * getDate.
     *
     * @param aDate date.
     * @return int.
     */
    public static int getDate(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * getHour.
     *
     * @param aDate date.
     * @return int.
     */
    public static int getHour(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * getHourStr.
     *
     * @param aDate date.
     * @return String.
     */
    public static String getHourStr(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            return String.format("0%d", hour);
        }
        return String.valueOf(hour);
    }

    /**
     * getMinuteStr.
     *
     * @param aDate date.
     * @return String.
     */
    public static String getMinuteStr(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            return String.format("0%d", minute);
        }
        return String.valueOf(minute);
    }

    /**
     * getMinute.
     *
     * @param aDate date.
     * @return String.
     */
    public static int getMinute(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.MINUTE);
    }

    /**
     * getSecond.
     *
     * @param aDate date.
     * @return String.
     */
    public static int getSecond(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        return c.get(Calendar.SECOND);
    }

    /**
     * setYear.
     *
     * @param aDate date.
     * @param year  year.
     */
    public static void setYear(Date aDate, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.YEAR, year);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setMonth.
     *
     * @param aDate date.
     * @param month month.
     */
    public static void setMonth(Date aDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.MONTH, month);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setDayOfWeek.
     *
     * @param aDate     date.
     * @param dayOfWeek dayOfWeek.
     */
    public static void setDayOfWeek(Date aDate, int dayOfWeek) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setDayOfMonth.
     *
     * @param aDate      date.
     * @param dayOfMonth dayOfMonth.
     */
    public static void setDayOfMonth(Date aDate, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setHour.
     *
     * @param aDate  date.
     * @param hour24 hour24.
     */
    public static void setHour(Date aDate, int hour24) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.HOUR_OF_DAY, hour24);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setMinute.
     *
     * @param aDate  date.
     * @param minute minute.
     */
    public static void setMinute(Date aDate, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.MINUTE, minute);
        aDate.setTime(c.getTime().getTime());
    }

    /**
     * setSecond.
     *
     * @param aDate  date.
     * @param second second.
     */
    public static void setSecond(Date aDate, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.SECOND, second);
        aDate.setTime(c.getTime().getTime());
    }


    /**
     * return a Date with only the year, month & day retained (i.e. the time  part is truncated).
     *
     * @param aDate date.
     * @return date.
     */
    public static Date trimTime(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    /**
     * return Date with 23:59:59:999.
     *
     * @param aDate date.
     * @return date.
     */
    public static Date trimTimeToEnd(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }


    /**
     * 返回一个月后的时间.
     *
     * @param aDate date.
     * @return date.
     */
    public static Date trimTimeToEnd2(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.add(Calendar.MONTH, 1); // 将当前日期加一个月
        return c.getTime();
    }


    /**
     * Based on time zone, return a Date with only the year, month & day retained (i.e. the time part is truncated)
     *
     * @param aDate date.
     * @return date.
     */
    public static Date trimTimeByZone(Date aDate) {
        return aDate;
    }


    /**
     * Based on time zone, return Date with 23:59:59:999.
     *
     * @param aDate date.
     * @return date.
     */
    public static Date trimTimeToEndByZone(Date aDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        c.add(Calendar.DATE, 1);
        c.add(Calendar.SECOND, -1);
        return c.getTime();
    }

    /**
     * alterDate.
     *
     * @param aDate       date.
     * @param year        year.
     * @param month       month.
     * @param date        date.
     * @param hour        hour.
     * @param minute      minute.
     * @param second      second.
     * @param milliSecond milliSecond.
     * @return date.
     */
    public static Date alterDate(Date aDate, int year, int month, int date, int hour, int minute, int second,
                                 int milliSecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(aDate);
        if (year != NO_CHANGE) {
            c.set(Calendar.YEAR, year);
        }
        if (month != NO_CHANGE) {
            c.set(Calendar.MONTH, month);
        }
        if (date != NO_CHANGE) {
            c.set(Calendar.DAY_OF_MONTH, date);
        }
        if (hour != NO_CHANGE) {
            c.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != NO_CHANGE) {
            c.set(Calendar.MINUTE, minute);
        }
        if (second != NO_CHANGE) {
            c.set(Calendar.SECOND, second);
        }
        if (milliSecond != NO_CHANGE) {
            c.set(Calendar.MILLISECOND, milliSecond);
        }
        return c.getTime();
    }

    /**
     * formatDateToString.
     *
     * @param date date.
     * @return String .
     */
    public static String formatDateToString(Date date) {
        return formatDateToString(date, PARAM_DISPLAY_DATEFORMAT);
    }

    /**
     * formatDateToString.
     *
     * @param date   date.
     * @param format format.
     * @return String .
     */
    public static String formatDateToString(Date date, String format) {
        return formatDateToString(null, date, format);
    }

    /**
     * formatDateRangeToString.
     *
     * @param dateFrom date.
     * @param dateTo   date.
     * @param format   format.
     * @return String .
     */
    public static String formatDateRangeToString(Date dateFrom, Date dateTo, String format) {
        if (isSameDay(dateFrom, dateTo)) {
            return formatDateToString(dateFrom, format) + "-" + formatDateToString(dateTo, "HH:mm");
        } else {
            return formatDateToString(dateFrom, format) + " -" + formatDateToString(dateTo, format);
        }
    }

    /**
     * 是否同一天.
     *
     * @param date1 date
     * @param date2 date.
     * @return boolean.
     */
    public static Boolean isSameDay(Date date1, Date date2) {
        return getYear(date1) == getYear(date2) && getMonth(date1) == getMonth(date2)
                && getDate(date1) == getDate(date2);
    }

    /**
     * 是否相同Date.
     *
     * @param date1 date
     * @param date2 date.
     * @return boolean.
     */
    public static Boolean isSameDate(Date date1, Date date2) {
        return date1.getTime() == date2.getTime();
    }

    /**
     * Format the date based on the time zone and the format.
     *
     * @param zoneId zoneId.
     * @param date   date.
     * @param format format.
     * @return String.
     */
    public static String formatDateToString(String zoneId, Date date, String format) {
        String result = null;
        if (date == null) {
            result = "";
        } else {
            SimpleDateFormat stdFormat = new SimpleDateFormat(format);
            if (zoneId != null && !"".equals(zoneId)) {
                stdFormat.setTimeZone(TimeZone.getTimeZone(zoneId));
            }
            result = stdFormat.format(date);
        }
        return result;
    }

    /**
     * convertDateforClient.
     *
     * @param date date.
     * @return date.
     */
    public static Date convertDateforClient(Date date) {
        Date result = date;
        if (result != null) {
            if (getYear(result) <= 2) {
                result = null;
            }
        }
        return result;
    }

    /**
     * getFirstDateInMonth.
     *
     * @param date date.
     * @return date.
     */
    public static Date getFirstDateInMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 格式化pattern  yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date getFirstDateInMonth2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String logEntry = formatter.format(date);
        Date today = null;
        try {
            today = formatter.parse(logEntry);
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * getFirstDateInMonth.
     *
     * @return Long.
     */
    public static Long getFirstDateInMonth() {
        return DateUtil.getFirstDateInMonth(trimTime(new Date())).getTime();
    }

    /**
     * isRecent.
     *
     * @param dateTime Date.
     * @param days     int .
     * @return Date.
     */
    public static Boolean isRecent(Date dateTime, int days) {
        if (dateTime == null) {
            return false;
        }
        return (new Date()).getTime() - dateTime.getTime() < 86400000 * days;

    }

    /**
     * getAge.
     *
     * @param birthDay birthDay.
     * @return Integer.
     */
    public static Integer getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }

        return age;
    }

    /**
     * defaultFormat yyyy-MM-dd HH:mm:ss .
     *
     * @param date date.
     * @return String.
     */
    public static String defaultFormat(Date date) {
        if (date == null) {
            return "";
        }
        return formatDateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期转星期.
     *
     * @param datetime datetime.
     * @return String.
     */
    public static String dateToWeek(String datetime) {
        if (datetime == null || datetime == "") {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * defaultShortFormat  yyyy/MM/dd .
     *
     * @param date date.
     * @return String.
     */
    public static String defaultShortFormat(Date date) {
        if (date == null) {
            return "";
        }
        return formatDateToString(date, "yyyy/MM/dd");
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致.
     *
     * @param nowTime   当前时间.
     * @param startTime 开始时间.
     * @param endTime   结束时间.
     * @return boolean.
     */
    public static boolean judgeTimeIsBetweenStarTimeAndEndTime(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    /**
     * differentDays.
     *
     * @param startDate startDate.
     * @param endDate   endDate.
     * @return int.
     */
    public static int differentDays(Date startDate, Date endDate) {
        LocalDateTime startTime = LocalDateTime.of(LocalDateUtil.dateToLocalDate(startDate), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDateUtil.dateToLocalDate(endDate), LocalTime.MIN);
        long days = LocalDateUtil.betweenTwoTime(startTime, endTime, ChronoUnit.DAYS);
        return new Long(days).intValue();
    }

    /**
     * getTomorrowBegin.
     *
     * @return Date.
     */
    public static Date getTomorrowBegin() {
        return addDays(trimTime(new Date()), 1);
    }

    /**
     * getTomorrowBegin.
     *
     * @param date date.
     * @return date.
     */
    public static Date getTomorrowBegin(Date date) {
        return addDays(trimTime(date), 1);
    }

    /**
     * convertQuantumStamp.
     *
     * @param timeStr timeStr.
     * @return int .
     */
    public static int convertQuantumStamp(String timeStr) {
        if (null == timeStr || "".equals(timeStr)) {
            return -1;
        }
        String[] timeStrArr = timeStr.split(":");
        if (timeStrArr.length == 2) {
            int timeStrArr0 = Integer.parseInt(timeStrArr[0]);
            int timeStrArr1 = Integer.parseInt(timeStrArr[1]);
            if (timeStrArr0 < 0 || timeStrArr0 > 23) {
                return -1;
            }
            if (timeStrArr1 < 0 || timeStrArr1 > 59) {
                return -1;
            }
            int stamp = timeStrArr0 * 3600 + timeStrArr1 * 60;
            return stamp;
        } else if (timeStrArr.length == 3) {
            int timeStrArr0 = Integer.parseInt(timeStrArr[0]);
            int timeStrArr1 = Integer.parseInt(timeStrArr[1]);
            int timeStrArr2 = Integer.parseInt(timeStrArr[2]);
            if (timeStrArr0 < 0 || timeStrArr0 > 23) {
                return -1;
            }
            if (timeStrArr1 < 0 || timeStrArr1 > 59) {
                return -1;
            }
            if (timeStrArr2 < 0 || timeStrArr2 > 59) {
                return -1;
            }
            int stamp = timeStrArr0 * 3600 + timeStrArr1 * 60 + timeStrArr2;
            return stamp;
        } else {
            return -1;
        }

    }

    /**
     * 日期转换成时分整数.
     *
     * @param date 日期.
     * @return int.
     */
    public static int convertQuantumStamp(Date date) {
        String time = formatDateToString(date, "HH:mm");
        return convertQuantumStamp(time);
    }

    /**
     * convertQuantumTimeNoSecond.
     *
     * @param stamp stamp.
     * @return String.
     */
    public static String convertQuantumTimeNoSecond(int stamp) {
        return convertQuantumTime(stamp, false);
    }

    /**
     * convertQuantumTimeDefault.
     *
     * @param stamp stamp.
     * @return String.
     */
    public static String convertQuantumTimeDefault(int stamp) {
        return convertQuantumTime(stamp, true);
    }

    /**
     * convertQuantumTime.
     *
     * @param stamp      stamp.
     * @param needSecond needSecond.
     * @return String.
     */
    public static String convertQuantumTime(int stamp, boolean needSecond) {
        StringBuilder time = new StringBuilder();
        if (stamp < 0 || stamp > 86400) {
            return null;
        }
        Integer hourDiv = stamp / 3600;
        Integer hourRemainder = stamp % 3600;

        Integer minuteDiv = hourRemainder / 60;
        Integer minuteRemainder = hourRemainder % 60;

        Integer secondRemainder = minuteRemainder % 60;

        if (hourDiv < 10) {
            time.append("0");
        }
        time.append(hourDiv).append(":");

        if (minuteDiv < 10) {
            time.append("0");
        }
        time.append(minuteDiv);
        if (needSecond) {
            time.append(":");
            if (secondRemainder < 10) {
                time.append("0");
            }
            time.append(secondRemainder);
        }

        return time.toString();
    }

    /**
     * 根据秒数格式化时间 如 1200秒 格式化成 02时00分00秒.
     *
     * @param time time .
     * @return String.
     */
    public static String secToTime(int time) {
        return secToTime(time, false);
    }

    /**
     * 根据秒数格式化时间 如 1200秒 格式化成 02时00分00秒 或者 02:00:00.
     *
     * @param time    秒数.
     * @param isColon 是否使用冒号 true 使用 false 不使用.
     * @return String.
     */
    public static String secToTime(int time, boolean isColon) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return isColon ? "00:00:00" : "00时00分00秒";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + (isColon ? ":" : "分") + unitFormat(second) + (isColon ? "" : "秒");
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return isColon ? "99:59:59" : "99时59分59秒";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + (isColon ? ":" : "时") + unitFormat(minute) + (isColon ? ":" : "分")
                        + unitFormat(second) + (isColon ? "" : "秒");
            }
        }
        return timeStr;
    }

    /**
     * unitFormat.
     *
     * @param i i.
     * @return String .
     */
    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * 获取指定时间之后的时间 比如获取 7小时之后的时间 unit = HOUR,amount = 7.
     *
     * @param unit   unit.
     * @param amount amount.
     * @return Date.
     */
    public static Date gettTmeAfterTheSpecifiedTime(String unit, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        if (unit.equals(CouponEffectUtil.HOUR)) {
            // 获取一个小时以后的时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + amount);
            return calendar.getTime();
        } else if (unit.equals(CouponEffectUtil.DAY)) {
            // 获取一天以后的时间
            calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + amount);
            return calendar.getTime();
        } else if (unit.equals(CouponEffectUtil.WEEK)) {
            // 获取一个星期以后的时间
            calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) + amount);
            return calendar.getTime();
        } else if (unit.equals(CouponEffectUtil.MONTH)) {
            // 获取一个月以后的时间
            calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, amount);
            return calendar.getTime();
        } else if (unit.equals(CouponEffectUtil.YEAR)) {
            // 获取一个月以后的时间
            calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, amount);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * getWeekdayFromMonday.
     *
     * @param date date.
     * @return date.
     */
    public static int getWeekdayFromMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            weekday += 7;
        }
        return --weekday;
    }

    /**
     * 获取当前时间的秒时间.
     *
     * @param theTime theTime.
     * @return int .
     */
    public static int getTimestamp(Date theTime) {
        return (int) (theTime.getTime() - DateUtil.trimTime(theTime).getTime()) / 1000;
    }

    /**
     * 获取当前时间毫秒时间戳.
     *
     * @return Long.
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 单位毫秒.
     *
     * @param t t.
     * @return String.
     */
    public static String convertTimeShow(long t) {
        t = t / 1000;
        if (t <= 60) {
            return t + "秒";
        } else if (t > 60 && t <= 3600) {
            long min = t / 60;
            long second = t % 60;
            return min + "分" + second + "秒";
        } else if (t > 3600 && t <= 86400) {
            long time = t / 3600;
            long min = (t % 3600) / 60;
            return time + "小时" + min + "分";
        } else if (t > 86400) {
            long day = t / 86400;
            long time = (t % 86400) / 3600;
            return day + "天" + time + "小时";
        }
        return null;
    }

    /**
     * 获取距离0点的毫秒时间戳
     *
     * @param date date.
     * @return long.
     */
    public static long getTodayMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        return (date.getTime() + calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) % 86400000;
    }

    /**
     * 获取多少年后的时间.
     *
     * @param date  开始时间.
     * @param count 传1就是开始时间的1年后.
     * @return Date.
     */
    public static Date getNextYearDate(Date date, Integer count) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + count);
        return curr.getTime();
    }


    /**
     * 获取多少月份之后的时间.
     *
     * @param date  date.
     * @param count count .
     * @return Date.
     */
    public static Date getNextMonthDate(Date date, Integer count) {
        Calendar curr = Calendar.getInstance();
        curr.setTime(date);
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + count);
        return curr.getTime();
    }


    /**
     * 与当前月份比较：-1（过去）,0（当前）,1（未来）
     *
     * @param month mont.
     * @return int.
     */
    public static int compareWithCurrentByMonth(String month) {
        String currentMonth = formatDateToString(new Date(), YEAR_MONTH_INT);
        int currentMonthInt = Integer.parseInt(currentMonth);
        int monthInt = Integer.parseInt(month);

        if (currentMonthInt > monthInt) {
            return -1;
        } else if (currentMonthInt == monthInt) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 当天的年月数据.
     *
     * @return String.
     */
    public static String getCurrentMonthStr() {
        return formatDateToString(new Date(), YEAR_MONTH_INT);
    }


    /**
     * 日期转成年月整数.
     *
     * @param yearMonthDay yearMonthDay.
     * @return Integer.
     */
    public static Integer getMonthByDate(String yearMonthDay) {
        Date theDate = getFormattedDate(DATE_FORMAT, yearMonthDay);
        String theMonth = formatDateToString(theDate, YEAR_MONTH_INT);

        return Integer.parseInt(theMonth);
    }


    /**
     * 返回指定日对应的月份第一天：yyyy-MM-dd.
     *
     * @param date date.
     * @return String.
     */
    public static String getBeginMonthByDate(Date date) {

        String yearMonth = formatDateToString(date, YEAR_MONTH);
        return yearMonth + "-01";
    }


    /**
     * 返回指定日对应的月份最后一天：yyyy-MM-dd.
     *
     * @param date date.
     * @return String .
     */
    public static String getEndMonthByDate(Date date) {

        String yearMonth = formatDateToString(date, YEAR_MONTH);
        return getLastDayOfMonth(yearMonth);
    }


    /**
     * 获取指定月份的最后一天.
     *
     * @param yearMonth yearMonth.
     * @return String.
     */
    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.add(Calendar.DATE, -1);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取一个月天数.
     *
     * @param date date.
     * @return int .
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 给时间添加或减去几个小时.
     *
     * @param date 当前时间.
     * @param hour 需要加的时间.
     * @return Date.
     */
    public static Date addOrSubtractHoursToTime(Date date, Double hour) {
        double min = hour * 60;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, (int) min);// 24小时制
        date = cal.getTime();
        return date;
    }

    /**
     * getDateWithMaxTime.
     *
     * @param date 当前时间.
     * @return Date.
     */
    public static Date getDateWithMaxTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);   //增加毫秒设置为0
        return calendar.getTime();
    }

    /**
     * getDateWithMinTime.
     *
     * @param date 当前时间.
     * @return Date.
     */
    public static Date getDateWithMinTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);   //增加毫秒设置为0
        return calendar.getTime();
    }

    /**
     * getDateWithMinTime.
     *
     * @param localDate localDate.
     * @return Date.
     */
    public static Date LocalDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前日期是星期几<br>.
     *
     * @param date .
     * @return 当前日期是星期几.
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * getDayOfWeek.
     *
     * @param dayOfWeek  dayOfWeek.
     * @param weekOffset weekOffset.
     * @param date       date.
     * @return date.
     */
    public static Date getDayOfWeek(int dayOfWeek, int weekOffset, Date date) {
        return getDayOfWeek(Calendar.MONDAY, dayOfWeek, weekOffset, date);
    }

    public static Date getDayOfWeek(int dayOfWeek, int weekOffset) {
        return getDayOfWeek(Calendar.MONDAY, dayOfWeek, weekOffset, new Date());
    }

    /**
     * 获取上(下)周周几的日期.
     *
     * @param firstDayOfWeek {@link Calendar}.
     *                       值范围 <code>SUNDAY</code>,.
     *                       <code>MONDAY</code>, <code>TUESDAY</code>, <code>WEDNESDAY</code>,.
     *                       <code>THURSDAY</code>, <code>FRIDAY</code>, and <code>SATURDAY</code>.
     * @param dayOfWeek      {@link Calendar}.
     * @param weekOffset     周偏移，上周为-1，本周为0，下周为1，以此类推.
     * @return date.
     */
    public static Date getDayOfWeek(int firstDayOfWeek, int dayOfWeek, int weekOffset, Date dateTime) {
        if (dayOfWeek > Calendar.SATURDAY || dayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        if (firstDayOfWeek > Calendar.SATURDAY || firstDayOfWeek < Calendar.SUNDAY) {
            return null;
        }
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setTime(dateTime);
        date.setFirstDayOfWeek(firstDayOfWeek);
        //周数减一，即上周
        date.add(Calendar.WEEK_OF_MONTH, weekOffset);
        //日子设为周几
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //时分秒全部置0
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }


    /**
     * 获取本周的开始时间.
     *
     * @return Date.
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        return getBeginDayOfWeek(date);
    }

    /**
     * 获取某天所在周的开始时间.
     *
     * @param date date.
     * @return Date.
     */
    public static Date getBeginDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取本周的结束时间.
     *
     * @return Date.
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(getBeginDayOfWeek()));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取某天所在周的结束时间.
     *
     * @param date date.
     * @return date.
     */
    public static Date getEndDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(getBeginDayOfWeek(date)));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取上周的开始时间.
     *
     * @return Date.
     */
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }


    /**
     * 获取上周的结束时间.
     *
     * @return Date.
     */
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取指定日期上周的开始时间.
     *
     * @param date date.
     * @return date.
     */
    public static Date getBeginDayOfLastWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取指定日期上周的结束时间.
     *
     * @param date date.
     * @return date.
     */
    public static Date getEndDayOfLastWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    /**
     * 获取某个日期的开始时间.
     *
     * @param d date.
     * @return Timestamp.
     */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
                0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取某个日期的结束时间.
     *
     * @param d date.
     * @return Timestamp.
     */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
                59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取当前时间minute分钟前/后的时间
    public static Date getMinuteAgoTime(Integer minute) {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);// 30分钟前
        return calendar.getTime();
    }


}
