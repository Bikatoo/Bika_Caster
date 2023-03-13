package com.bikatoo.lancer.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtils {
  /**
   * 计算两天之间的时间间隔
   *
   * @param d1
   * @param d2
   * @return
   */
  public static int calIntervalBetweenDays(long d1, long d2) {
    long rst = (long) Math.floor((double) (d1 - d2) / (1000 * 3600 * 24));
    return Integer.parseInt(String.valueOf(rst));
  }

  /**
   * 获取当天0点时间戳
   *
   * @return
   */
  public static long getStartTimeInCurrentDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTimeInMillis();
  }

  public static int getTimespan(long startDate, long endDate) {
    return (int) ((endDate - startDate) / 86400000L) + 1;
  }

  /** 获取几天前的日期 */
  public static String getPassTime(int day) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(new Date());
    calendar.add(calendar.DATE, -day);
    String date = sdf.format(calendar.getTime());
    return date;
  }

  /** 获取指定日期零点 */
  public static long getTimeStampByDay(long date) {
    return date - (date + TimeZone.getDefault().getRawOffset()) % 86400000L;
  }

  /** date转时间戳 */
  public static long dateToTimStamp(Date date) {
    return date.getTime();
  }

  /**
   * 获取指定某一天的开始时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getDailyStartTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取指定某一天的结束时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getDailyEndTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取当月开始时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getMonthStartTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance(); // 获取当前日期
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    calendar.add(Calendar.YEAR, 0);
    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号,当前日期既为本月第一天
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取当月的结束时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getMonthEndTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance(); // 获取当前日期
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    calendar.add(Calendar.YEAR, 0);
    calendar.add(Calendar.MONTH, 0);
    calendar.set(
        Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // 获取当前月最后一天
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取当年的开始时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getYearStartTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance(); // 获取当前日期
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    calendar.add(Calendar.YEAR, 0);
    calendar.add(Calendar.DATE, 0);
    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_YEAR, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTimeInMillis();
  }

  /**
   * 获取当年的最后时间戳
   *
   * @param timeStamp 毫秒级时间戳
   * @param timeZone 如 GMT+8:00
   * @return
   */
  public static Long getYearEndTime(Long timeStamp, String timeZone) {
    Calendar calendar = Calendar.getInstance(); // 获取当前日期
    calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
    calendar.setTimeInMillis(timeStamp);
    int year = calendar.get(Calendar.YEAR);
    calendar.clear();
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    calendar.roll(Calendar.DAY_OF_YEAR, -1);
    return calendar.getTimeInMillis();
  }

  public static String transformDateIntoStr(Date date) {
    SimpleDateFormat simpleDateFormat = getDateType(date);
    return simpleDateFormat.format(date);
  }

  public static SimpleDateFormat getDateType(Date date) {
    SimpleDateFormat simpleDateFormat;

    long dateTime = date.getTime();

    long currentTimeMillis = System.currentTimeMillis();
    long dailyStartTime = TimeUtils.getDailyStartTime(currentTimeMillis, "GMT+8:00");
    long yearStartTime = TimeUtils.getYearStartTime(currentTimeMillis, "GMT+8:00");

    if (dateTime > dailyStartTime) {
      simpleDateFormat = new SimpleDateFormat("HH:mm");
    } else if (dateTime > yearStartTime) {
      simpleDateFormat = new SimpleDateFormat("MM/dd");
    } else {
      simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    }

    return simpleDateFormat;
  }

  /**
   * 获取本周的第一天
   *
   * @return String *
   */
  public static long getWeekStart() {
    Calendar cal = Calendar.getInstance();
    int d;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      d = -6;
    } else {
      d = 2 - cal.get(Calendar.DAY_OF_WEEK);
    }
    cal.add(Calendar.DAY_OF_WEEK, d);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    return cal.getTime().getTime() - ((cal.getTime().getTime() + 28800000) % 86400000);
  }
  /**
   * 获取本周的最后一天
   *
   * @return String *
   */
  public static long getWeekEnd() {
    Calendar cal = Calendar.getInstance();
    int d;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      d = -6;
    } else {
      d = 2 - cal.get(Calendar.DAY_OF_WEEK);
    }
    cal.add(Calendar.DAY_OF_WEEK, d);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.add(Calendar.DAY_OF_WEEK, 6);
    return cal.getTime().getTime() - ((cal.getTime().getTime() + 28800000) % 86400000);
  }

  /**
   * 获取给定时间那一周的第一天
   *
   * @return String *
   */
  public static long getWeekStart(long dateTime) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(dateTime));
    int d;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      d = -6;
    } else {
      d = 2 - cal.get(Calendar.DAY_OF_WEEK);
    }
    cal.add(Calendar.DAY_OF_WEEK, d);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    return cal.getTime().getTime() - ((cal.getTime().getTime() + 28800000) % 86400000);
  }
  /**
   * 获取给定时间那一周的最后一天
   *
   * @return String *
   */
  public static long getWeekEnd(long dateTime) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(dateTime));
    int d;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      d = -6;
    } else {
      d = 2 - cal.get(Calendar.DAY_OF_WEEK);
    }
    cal.add(Calendar.DAY_OF_WEEK, d);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.add(Calendar.DAY_OF_WEEK, 6);
    return cal.getTime().getTime() - ((cal.getTime().getTime() + 28800000) % 86400000);
  }

  /**
   * 计算两个日期之间相差的天数
   *
   * @param smdate 较小的时间
   * @param bdate 较大的时间
   * @return 相差天数
   */
  public static int daysBetween(Date smdate, Date bdate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    smdate = sdf.parse(sdf.format(smdate));
    bdate = sdf.parse(sdf.format(bdate));
    Calendar cal = Calendar.getInstance();
    cal.setTime(smdate);
    long time1 = cal.getTimeInMillis();
    cal.setTime(bdate);
    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / (1000 * 3600 * 24);

    return Integer.parseInt(String.valueOf(between_days));
  }

  /**
   * 获取给定日期之后的n天的日期
   * @param date
   * @param plusDays
   * @return
   */
  public static Date getDatePlusNDayDate(Date date, Integer plusDays) {
    return new Date(date.getTime() + 86400000L * plusDays);
  }

  public static Date getDateDiffCurrentDayByOffSet(int dayPast) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date(getStartTimeInCurrentDay()));
    calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - dayPast);
    return calendar.getTime();
  }

  public static void main(String[] args) throws ParseException {

    System.out.println(getDateDiffCurrentDayByOffSet(7).getTime());
    System.out.println(getDateDiffCurrentDayByOffSet(-7).getTime());
  }
}
