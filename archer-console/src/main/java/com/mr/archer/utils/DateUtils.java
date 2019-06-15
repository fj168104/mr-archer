package com.mr.archer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

  /**
   * 将日期对象(date)按提供的格式(sFormat)格式化
   *
   * @param date
   *          日期对象
   * @param sFormat
   *          目的格式
   * @return
   */
  public static String parseDateFormat(Date date, String sFormat) {
    SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(date);
    return sdf.format(gc.getTime());
  }

  /**
   * 按照format指定格式返回当前时间
   *
   * @param format
   *          时间格式
   * @return
   */
  public static String getNowTime(String format) {
    return parseDateFormat(new Date(), format);
  }

  /**
   * 获取当前时间，精确到秒
   *
   * @return
   */
  public static String getNowTime() {
    return getNowTime("yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 获取当前日期
   *
   * @return
   */
  public static String getToday() {
    return getNowTime("yyyy-MM-dd");
  }
}
