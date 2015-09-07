package com.hi.model;

import java.util.Date;

import com.hi.tools.CalendarTools;

public class TimePeriod {

	// 繁忙时间起始点
	private String start = "";

	// 繁忙时间结束点
	private String end = "";

	public TimePeriod() {
	}

	public TimePeriod(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public boolean contains(Date date) {
		if (date != null) {
			return date.after(CalendarTools.timeStr2Date(this.start, CalendarTools.DATETIME_DEFAULT))
					&& date.before(CalendarTools.timeStr2Date(this.end, CalendarTools.DATETIME_DEFAULT));
		}
		return false;
	}
}
