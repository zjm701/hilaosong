package com.hi.service.impl;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.StoreDao;
import com.hi.dao.SysConfigDao;
import com.hi.model.Store;
import com.hi.model.SysConfig;
import com.hi.service.StoreService;
import com.hi.tools.StringTools;

@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao sdao;

	@Autowired
	private SysConfigDao cdao;

	public List<Store> getStores(String cityId) {
		if (cityId == null) {
			cityId = CityServiceImpl.CITY_BEIJING;
		}
		return sdao.getStores(cityId);
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
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = df.parse(StringTools.clobToString(cfg.getStartValue()) + ":00");
				endDate = df.parse(cfg.getEndValue() + ":59");
				if (startDate.before(date) && endDate.after(date)) {
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
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
		}else{
			return null;
		}
	}
	
	public String getDinningTimeType(String date,String storeId){
		String result = "";
		String morningTime = "10";
		String middleTime = "17";
		String nightTime = "22";
		List<SysConfig> sysList = cdao.getSysConfigs(storeId, "15");
		if (sysList != null) {
			for (SysConfig sysConfig : sysList) {
				if ("0".equals(sysConfig.getParamSrc())) {
					morningTime = getStartTimeValue(sysConfig.getStartValue());
					morningTime = morningTime == null ? "10" : morningTime;
				} else if ("1".equals(sysConfig.getParamSrc())) {
					middleTime = getStartTimeValue(sysConfig.getStartValue());
					middleTime = middleTime == null ? "17" : middleTime;
				} else if ("2".equals(sysConfig.getParamSrc())) {
					nightTime = getStartTimeValue(sysConfig.getStartValue());
					nightTime = nightTime == null ? "22" : nightTime;
				}
			}
		}
		String hour = date.split(" |:")[1];
		int intHour = Integer.valueOf(hour);
		int intMorning = Integer.valueOf(morningTime);
		int intMiddle = Integer.valueOf(middleTime);
		int intNight =Integer.valueOf(nightTime);
		if (intHour >= intMorning && intHour < intMiddle) {
			result = "1"; //午市
		}
		if (intHour >= intMiddle && intHour < intNight) {
			result = "2"; //晚市
		}
		if (intHour >= intNight || intHour < intMorning) {
			result = "3"; //夜市
		}
		return result;
	} 

	private String getStartTimeValue(Clob inputValue) {
		String input = StringTools.clobToString(inputValue);
		if (StringUtils.isNotEmpty(input)) {
			if (input.indexOf(":") > 0) {
				return input.split(":")[0];
			} else {
				return input;
			}
		}
		return null;
	}
}
