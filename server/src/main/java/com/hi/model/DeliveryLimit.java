package com.hi.model;

import java.util.ArrayList;
import java.util.List;

public class DeliveryLimit {

	// 普通外送最低金额
	private String deliveryLimitMoney = "";

	// 预定开始日期
	private String startDay = "";

	// 预定结束日期
	private String endDay = "";

	// 首日预定开始时间
	private String firstDayStartTime = "";

	// 每日预定开始时间
	private String startTime = "";

	// 每日预定结束时间
	private String endTime = "";

	private String message = "";

	// 繁忙时间起始点
	private List<TimePeriod> busyTimes = new ArrayList<TimePeriod>();

	// 停业时间
	private List<TimePeriod> closeTimes = new ArrayList<TimePeriod>();

	public String getDeliveryLimitMoney() {
		return deliveryLimitMoney;
	}

	public void setDeliveryLimitMoney(String deliveryLimitMoney) {
		this.deliveryLimitMoney = deliveryLimitMoney;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getFirstDayStartTime() {
		return firstDayStartTime;
	}

	public void setFirstDayStartTime(String firstDayStartTime) {
		this.firstDayStartTime = firstDayStartTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<TimePeriod> getBusyTimes() {
		return busyTimes;
	}

	public void setBusyTimes(List<TimePeriod> busyTimes) {
		this.busyTimes = busyTimes;
	}

	public void addBusyTime(TimePeriod busyTime) {
		this.busyTimes.add(busyTime);
	}

	public List<TimePeriod> getCloseTimes() {
		return closeTimes;
	}

	public void setCloseTimes(List<TimePeriod> closeTimes) {
		this.closeTimes = closeTimes;
	}

	public void addCloseTime(TimePeriod closeTime) {
		this.closeTimes.add(closeTime);
	}
}
