package com.hi.tools;

import java.util.Calendar;
import java.util.Date;

public class CalendarTools {
	/* 日历 */

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
}
