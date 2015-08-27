package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.CityDao;
import com.hi.dao.SysConfigDao;
import com.hi.model.City;
import com.hi.model.SysConfig;
import com.hi.service.CityService;
import com.hi.tools.StringTools;

@Service("cityService")
@Transactional
public class CityServiceImpl implements CityService {
	public static final String CITY_BEIJING = "110000";

	@Autowired
	private CityDao cdao;

	@Autowired
	private SysConfigDao sdao;

	public List<City> getDeliveryCities() {
		List<City> cities = cdao.getDeliveryCities();
		return cities;
	}

	public City getCity(String cityId) {
		return cdao.getCity(cityId);
	}

	public double getDeliveryUnitPrice(String cityId) {
		SysConfig cfg = sdao.getSysConfig(cityId, "6", "0");
		if (cfg != null) {
			return Double.parseDouble(StringTools.clobToString(cfg.getStartValue()));
		} else {
			return 0.0D;
		}
	}

	public String getDeliveryLimitMoney(String cityId) {
		SysConfig cfg = sdao.getSysConfig(cityId, "3", "0");
		if (cfg != null) {
			return StringTools.clobToString(cfg.getStartValue());
		} else {
			return null;
		}
	}
}
