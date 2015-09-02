package com.hi.model;

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
}
