package com.hi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.common.OrderType;
import com.hi.dao.CityDao;
import com.hi.dao.SysConfigDao;
import com.hi.model.City;
import com.hi.model.DeliveryLimit;
import com.hi.model.SysConfig;
import com.hi.model.TimePeriod;
import com.hi.service.CityService;
import com.hi.tools.CalendarTools;
import com.hi.tools.StringTools;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {
	public static final String CITY_BEIJING = "110000";

	@Autowired
	private CityDao cdao;

	@Autowired
	private SysConfigDao sdao;

	@Override
	public List<City> getDeliveryCities() {
		List<City> cities = cdao.getDeliveryCities();
		return cities;
	}

	@Override
	public City getCity(String cityId) {
		return cdao.getCity(cityId);
	}

	@Override
	public double getDeliveryUnitPrice(String cityId) {
		SysConfig cfg = sdao.getSysConfig(cityId, "6", OrderType.SEND_OUT.getKey());
		if (cfg != null) {
			return Double.parseDouble(StringTools.clobToString(cfg.getStartValue()));
		} else {
			return 0.0D;
		}
	}

	@Override
	public String getMinAmount(String cityId) {
		return getMinAmount(cityId, OrderType.SEND_OUT.getKey());
	}

	@Override
	public DeliveryLimit getDeliveryLimit(String storeId, String cityId, String orderType) {
		DeliveryLimit ol = new DeliveryLimit();
		ol.setDeliveryLimitMoney(getMinAmount(cityId, orderType));
		SysConfig cfg = sdao.getSysConfig(cityId, "8", orderType);
		if (cfg != null) {
			ol.setStartDay(StringTools.clobToString(cfg.getStartValue()));
			ol.setEndDay(cfg.getEndValue());
		}
		cfg = sdao.getSysConfig(cityId, "9", orderType);
		if (cfg != null) {
			ol.setStartTime(CalendarTools.getStartValueFromSysConfig(cfg) + ":00:00");
			ol.setEndTime(cfg.getEndValue().split(":")[0] + ":00:00");
		}
		cfg = sdao.getSysConfig(cityId, "69", orderType);
		if (cfg != null) {
			int delayMin = Integer.parseInt(CalendarTools.getStartValueFromSysConfig(cfg));
			Date delayTime = CalendarTools.addMinutes(CalendarTools.now(), delayMin);
			Date startTime = CalendarTools.timeStr2Date(ol.getStartTime(), CalendarTools.TIME);
			Date endTime = CalendarTools.timeStr2Date(ol.getEndTime(), CalendarTools.TIME);
			if (delayTime.before(startTime)) {
				ol.setFirstDayStartTime(ol.getStartTime());
			} else if (delayTime.after(endTime)) {
				ol.setFirstDayStartTime(ol.getEndTime());
			} else {
				ol.setFirstDayStartTime(CalendarTools.date2TimeStr(delayTime, CalendarTools.TIME));
			}
		}
		cfg = sdao.getSysConfig(storeId, "108", orderType);
		if (cfg != null) {
			ol.setMessage(StringTools.clobToString(cfg.getStartValue()));
		}
		//busy time
		List<SysConfig> cfgs = sdao.getSysConfigs(storeId, "92", orderType);
		if (cfgs != null && cfgs.size() > 0) {
			for (SysConfig c : cfgs) {
				TimePeriod tp = new TimePeriod();
				tp.setStart(CalendarTools.date2TimeStr(
						CalendarTools.timeStr2Date(StringTools.clobToString(c.getStartValue()) + ":00", CalendarTools.DATETIME_SYSCONFIG),
						CalendarTools.DATETIME_DEFAULT));
				tp.setEnd(CalendarTools.date2TimeStr(CalendarTools.timeStr2Date(c.getEndValue() + ":00", CalendarTools.DATETIME_SYSCONFIG),
						CalendarTools.DATETIME_DEFAULT));
				ol.addBusyTime(tp);
			}
		}
		//close time
		cfgs = sdao.getSysConfigs(storeId, "106", orderType);
		if (cfgs != null && cfgs.size() > 0) {
			for (SysConfig c : cfgs) {
				TimePeriod tp = new TimePeriod();
				tp.setStart(CalendarTools.date2TimeStr(
						CalendarTools.timeStr2Date(StringTools.clobToString(c.getStartValue()) + ":00", CalendarTools.DATETIME_SYSCONFIG),
						CalendarTools.DATETIME_DEFAULT));
				tp.setEnd(CalendarTools.date2TimeStr(CalendarTools.timeStr2Date(c.getEndValue() + ":00", CalendarTools.DATETIME_SYSCONFIG),
						CalendarTools.DATETIME_DEFAULT));
				ol.addCloseTime(tp);
			}
		}
		return ol;
	}

	private String getMinAmount(String cityId, String orderType) {
		SysConfig cfg = sdao.getSysConfig(cityId, "3", orderType);
		if (cfg != null) {
			return StringTools.clobToString(cfg.getStartValue());
		} else {
			return null;
		}
	}
}
