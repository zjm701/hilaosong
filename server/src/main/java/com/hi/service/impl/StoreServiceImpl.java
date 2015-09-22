package com.hi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.StoreDao;
import com.hi.dao.SysConfigDao;
import com.hi.model.Store;
import com.hi.model.SysConfig;
import com.hi.service.StoreService;
import com.hi.tools.CalendarTools;
import com.hi.tools.StringTools;

@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao sdao;

	@Autowired
	private SysConfigDao cdao;

	/**
	 * 不带距离
	 */
	public List<Store> getStores(String provinceId, String cityId, String orderType) {
		return getStores(provinceId, cityId, orderType, null);
	}

	/**
	 * 带距离
	 * 
	 * @param cityId
	 * @param orderType
	 * @param cuspoint
	 * @return
	 */
	public List<Store> getStores(String provinceId, String cityId, String orderType, String cuspoint) {
		if (cityId == null) {
			cityId = CityServiceImpl.CITY_BEIJING;
		}
		return sdao.getStores(provinceId, cityId, orderType, cuspoint);
	}

	public Store getDefaultStore(String cityId) {
		return sdao.getDefaultStore(cityId);
	}

	public Store getAreaStore(String storeId) {
		return sdao.getAreaStore(storeId);
	}
	
	public Store getStore(String storeId) {
		return sdao.getStore(storeId);
	}

	public Store getStore(String storeId, String cuspoint) {
		return sdao.getStore(storeId, cuspoint);
	}

	/**
	 * only for orderType = 1 （订座）
	 * 
	 * @param storeId
	 * @param date
	 * @return
	 */
	@Deprecated
	public boolean isClose(String storeId, Date date) {
		SysConfig cfg = cdao.getSysConfig(storeId, "105");
		if (cfg != null) {
			Date startDate = CalendarTools.timeStr2Date(StringTools.clobToString(cfg.getStartValue()) + ":00",
					CalendarTools.DATETIME_SYSCONFIG);
			Date endDate = CalendarTools.timeStr2Date(cfg.getEndValue() + ":59", CalendarTools.DATETIME_SYSCONFIG);

			if (startDate.before(date) && endDate.after(date)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * only for orderType = 1 （订座）
	 * 
	 * @param storeId
	 * @param date
	 * @return
	 */
	@Deprecated
	public boolean isShutdown(String storeId) {
		SysConfig cfg = cdao.getSysConfig(storeId, "107");
		return (cfg != null);
	}

	/**
	 * only for orderType = 1 （订座）
	 * 
	 * @param storeId
	 * @param date
	 * @return
	 */
	@Deprecated
	public String getShutdownNotice(String storeId) {
		SysConfig cfg = cdao.getSysConfig(storeId, "107");
		if (cfg != null) {
			return StringTools.clobToString(cfg.getStartValue());
		} else {
			return null;
		}
	}

	public String getDinningTimeType(String date, String storeId) {
		String result = "";
		String morningTime = "10";
		String middleTime = "17";
		String nightTime = "22";
		List<SysConfig> sysList = cdao.getSysConfigs(storeId, "15");
		if (sysList != null) {
			for (SysConfig sysConfig : sysList) {
				if ("0".equals(sysConfig.getParamSrc())) {
					morningTime = CalendarTools.getStartValueFromSysConfig(sysConfig);
					morningTime = morningTime == null ? "10" : morningTime;
				} else if ("1".equals(sysConfig.getParamSrc())) {
					middleTime = CalendarTools.getStartValueFromSysConfig(sysConfig);
					middleTime = middleTime == null ? "17" : middleTime;
				} else if ("2".equals(sysConfig.getParamSrc())) {
					nightTime = CalendarTools.getStartValueFromSysConfig(sysConfig);
					nightTime = nightTime == null ? "22" : nightTime;
				}
			}
		}
		String hour = date.split(" |:")[1];
		int intHour = Integer.valueOf(hour);
		int intMorning = Integer.valueOf(morningTime);
		int intMiddle = Integer.valueOf(middleTime);
		int intNight = Integer.valueOf(nightTime);
		if (intHour >= intMorning && intHour < intMiddle) {
			result = "1"; // 午市
		}
		if (intHour >= intMiddle && intHour < intNight) {
			result = "2"; // 晚市
		}
		if (intHour >= intNight || intHour < intMorning) {
			result = "3"; // 夜市
		}
		return result;
	}
}
