package com.jd.uwp.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 时间处理工具
 *
 * @author xu shiheng
 */
public class DateUtil {

    public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static DateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取 当前年、半年、季度、月、日、小时 开始结束时间
     */
    public static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    ;
    public static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    public static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateUtil() {

    }

    public static String formatDate(Date date) {
        return format.format(date);
    }

    public static String SysTime() {
        return formatDate(new Date());
    }

    /**
     * 获取某年第几周的开始时间
     *
     * @param year       年
     * @param weekOfYear 第几周
     */
    public static String getFristOfWeek(int year, int weekOfYear) {
        Calendar calendar = getCalendar(year, weekOfYear);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar firstDayInThisWeek = (Calendar) calendar.clone();
        firstDayInThisWeek.add(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);
        Date startDate = firstDayInThisWeek.getTime();
        return shortSdf.format(startDate);
    }

    /**
     * 获取某年第几周的结束时间
     *
     * @param year       年
     * @param weekOfYear 第几周
     */
    public static String getLastDayOfWeek(int year, int weekOfYear) {
        Calendar calendar = getCalendar(year, weekOfYear);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar lastDayInThisWeek = (Calendar) calendar.clone();
        lastDayInThisWeek.add(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_WEEK) - dayOfWeek);
        Date endDate = lastDayInThisWeek.getTime();
        return shortSdf.format(endDate);
    }

    /**
     * 获取日历
     *
     * @param year       年
     * @param weekOfYear 第几周
     */
    private static Calendar getCalendar(int year, int weekOfYear) {
        Calendar calendar = new GregorianCalendar(year, Calendar.JANUARY, 1);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 7);
                break;
            case 2:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 6);
                break;
            case 3:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 5);
                break;
            case 4:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 4);
                break;
            case 5:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 3);
                break;
            case 6:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 2);
                break;
            case 7:
                calendar.set(Calendar.DATE, 7 * (weekOfYear - 2) + day + 1);
                break;
        }
        return calendar;
    }

    /**
     * 得到某年某月的第一天
     *
     * @param year
     * @param month
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return shortSdf.format(cal.getTime());
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 得到某年某月的最后一天
     *
     * @param year
     * @param month
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return shortSdf.format(cal.getTime());
    }

    /**
     * 天数减法
     */
    public String dateCut(String day, int x) {
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -x);
        date = cal.getTime();
        cal = null;
        return formatDay.format(date);

    }

    /**
     * 天数加法
     */
    public static String addDate(String day, int x) {
        Date date = null;
        try {
            date = formatDay.parse(day);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (date == null) return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, x);
        date = cal.getTime();
        cal = null;
        return formatDay.format(date);
    }


    public static void main(String[] args) {

        System.out.println(format.format(new Date()));
        int x = 2007;
        int n = 10;
        Calendar cal = new GregorianCalendar(x, Calendar.JANUARY, 1);

        int ff = cal.get(Calendar.DAY_OF_WEEK);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        System.out.println("week:" + week);
        System.out.println("ff:" + ff);
        switch (ff) {
            case 1:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 7);
                break;
            case 2:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 6);
                break;
            case 3:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 5);
                break;
            case 4:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 4);
                break;
            case 5:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 3);
                break;
            case 6:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 2);
                break;
            case 7:
                cal.set(Calendar.DATE, 7 * (n - 2) + ff + 1);
                break;
        }

        System.out.println("cal 1:" + cal.getTime());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayOfWeek :" + dayOfWeek);
        Calendar calFirstDayInThisWeek = (Calendar) cal.clone();
        calFirstDayInThisWeek.add(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);
        Calendar calLastDayInThisWeek = (Calendar) cal.clone();
        calLastDayInThisWeek.add(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_WEEK) - dayOfWeek);
        Date startDate = calFirstDayInThisWeek.getTime();
        Date endDate = calLastDayInThisWeek.getTime();
        System.out.println("calFirstDayInThisWeek:" + calFirstDayInThisWeek.getTime());
        System.out.println("calLastDayInThisWeek:" + calLastDayInThisWeek.getTime());
        System.out.println("startDate:" + startDate.getTime());
        System.out.println("endDate:" + endDate.getTime());
    }

    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return c.getTime();
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public Date getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获得本天的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本天的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(shortSdf.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的开始时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public Date getCurrentHourStartTime() {
        Date now = new Date();
        try {
            now = longHourSdf.parse(longHourSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本小时的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public Date getCurrentHourEndTime() {
        Date now = new Date();
        try {
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public Date getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前年的结束时间，即2012-12-31 23:59:59
     *
     * @return
     */
    public Date getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取前/后半年的开始时间
     *
     * @return
     */
    public Date getHalfYearStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 6);
            }
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    }

    /**
     * 获取前/后半年的结束时间
     *
     * @return
     */
    public Date getHalfYearEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获得当前日期的前一个工作日
     *
     * @return
     */
    public static Calendar getPreWorkDay() {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        if (today == Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -3);
        } else if (today == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -2);
        } else {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar;
    }

    /**
     * 获得当前日期的前一周的第一天
     *
     * @return
     */
    public static Calendar getFirstDayOfPreWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 1 - dayOfWeek);
        return calendar;
    }

    /**
     * 获得当前日期的前一月的第一天
     *
     * @return
     */
    public static Calendar getFirstDayOfPreMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1 - dayOfMonth);
        return calendar;
    }

    public static String formatDateTime(Date date) {
        return format.format(new Date());
    }
}
