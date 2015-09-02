package com.hi.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.hi.model.SysConfig;

public class CalendarTools {
	/* 日历 */

	public static final String TIME = "HH:mm:ss";

	public static final String BIRTHDAY = "yyyy-MM-dd";

	public static final String YEAR = "yyyy";

	public static final String MONTH = "MM";

	public static final String DAY = "dd";

	public static final String DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATETIME_SYSCONFIG = "yyyy-MM-dd:HH:mm:ss";
	
	public static final String DATATIME_DATEBASE = "yyyy-mm-dd hh24:mi:ss";
	
	public static final String DATATIME_FILE = "yyyyMMddHHmmssS";

	public static final String DATATIME_REDENVELOPE = "yyyyMMddHHmmssSSS";

	/**
	 * 获得当前日历
	 * 
	 * @return
	 */
	public synchronized static Calendar current() {
		return Calendar.getInstance();
	}

	/**
	 * 获得日历
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static Calendar getCalendar(long milliseconds) {
		Calendar cal = current();
		cal.setTimeInMillis(milliseconds);
		return cal;
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public synchronized static Date now() {
		return getTime(current());
	}

	public static Date addMinutes(Date date, int amount) {
		return DateUtils.addMinutes(date, amount);
	}

	/**
	 * 获得指定日历的时间
	 * 
	 * @param cal
	 * @return
	 */
	public static Date getTime(Calendar cal) {
		return cal.getTime();
	}

	/**
	 * 获得日历
	 * 
	 * @param milliseconds
	 * @return
	 */
	public static Date getTime(long milliseconds) {
		return getTime(getCalendar(milliseconds));
	}

	/**
	 * 将字符串转换成日期形 参数：time，String，日期字符串 pattern, String, 解析的格式 返回：Date，日期形
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return Date
	 * @see [类、类#方法、类#成员]
	 */
	public static Date timeStr2Date(String time, String pattern) {
		if (null == time) {
			throw new IllegalArgumentException("time parameter can not be null");
		}
		if (null == pattern) {
			throw new IllegalArgumentException("pattern parameter can not be null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			throw new IllegalArgumentException("using [" + pattern + "] parse [" + time + "] failed");
		}
	}

	/**
	 * 将日期型转换成字符串 参数：time，Date pattern, String, 转换的目标格式<一句话功能简述> <功能详细描述>
	 * 
	 * @param time
	 *            time
	 * @param pattern
	 *            pattern
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	public static String date2TimeStr(Date time, String pattern) {
		if (null == pattern) {
			throw new IllegalArgumentException("pattern parameter can not be null");
		}
		if (null == time) {
			throw new IllegalArgumentException("time parameter can not be null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(time);
	}

	/**
	 * @param pattern
	 * @return
	 */
	public static String nowString(String pattern) {
		return date2TimeStr(now(), pattern);
	}

	public static String getStartValueFromSysConfig(SysConfig cfg) {
		if (cfg != null) {
			String input = StringTools.clobToString(cfg.getStartValue());
			if (StringUtils.isNotEmpty(input)) {
				if (input.indexOf(":") > 0) {
					return input.split(":")[0];
				} else {
					return input;
				}
			}
		}
		return null;
	}
}
