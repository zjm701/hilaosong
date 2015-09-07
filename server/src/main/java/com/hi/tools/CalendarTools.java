package com.hi.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.hi.model.DeliveryLimit;
import com.hi.model.SysConfig;
import com.hi.model.TimePeriod;

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

	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
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

	public static boolean isValidDinningTime(Date dinningTime, DeliveryLimit limit) {
		if (dinningTime == null) {
			return false;
		} else {
			if (limit != null) {
				// "busyTimes":[{"start":"01:15:00","end":"03:30:00"}],"closeTimes":[{"start":"09:45:00","end":"00:00:00"}]}
				int startDay = 0;
				int endDay = 60;
				if (StringTools.isNotEmpty(limit.getStartDay())) {
					startDay = Integer.parseInt(limit.getStartDay());
				}
				if (StringTools.isNotEmpty(limit.getEndDay())) {
					endDay = Integer.parseInt(limit.getEndDay());
				}

				Date now = now();
				Date startDate = addDays(now, startDay);
				Date endDate = addDays(now, endDay);
				if (dinningTime.before(startDate) || dinningTime.after(endDate)) {
					return false; // 在日期范围外
				}

				Calendar tmp = Calendar.getInstance();
				tmp.setTime(dinningTime);
				if (startDay == 0 && DateUtils.isSameDay(dinningTime, now)) { // first
																				// day
					if (StringTools.isNotEmpty(limit.getFirstDayStartTime())) {
						String[] firstDayStartTimeStrs = limit.getFirstDayStartTime().split(":");
						tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(firstDayStartTimeStrs[0]));
						tmp.set(Calendar.MINUTE, Integer.parseInt(firstDayStartTimeStrs[1]));
						tmp.set(Calendar.SECOND, Integer.parseInt(firstDayStartTimeStrs[2]));
						if (dinningTime.before(tmp.getTime())) { // 在首日最早时间 之前
							return false;
						}
					}
				} else {
					if (StringTools.isNotEmpty(limit.getStartTime())) {
						String[] startTimeStrs = limit.getStartTime().split(":");
						tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeStrs[0]));
						tmp.set(Calendar.MINUTE, Integer.parseInt(startTimeStrs[1]));
						tmp.set(Calendar.SECOND, Integer.parseInt(startTimeStrs[2]));
						if (dinningTime.before(tmp.getTime())) {// 在最早时间 之前
							return false;
						}
					}
				}
				if (StringTools.isNotEmpty(limit.getEndTime())) {
					String[] endTimeStrs = limit.getEndTime().split(":");
					tmp.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTimeStrs[0]));
					tmp.set(Calendar.MINUTE, Integer.parseInt(endTimeStrs[1]));
					tmp.set(Calendar.SECOND, Integer.parseInt(endTimeStrs[2]));
					if (dinningTime.after(tmp.getTime())) {// 在最晚时间 之后
						return false;
					}
				}
				if (limit.getBusyTimes() != null && limit.getBusyTimes().size() > 0) {
					for (TimePeriod tp : limit.getBusyTimes()) {
						if (tp.contains(dinningTime)) {
							return false;
						}
					}
				}
				if (limit.getCloseTimes() != null && limit.getCloseTimes().size() > 0) {
					for (TimePeriod tp : limit.getCloseTimes()) {
						if (tp.contains(dinningTime)) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}
}
