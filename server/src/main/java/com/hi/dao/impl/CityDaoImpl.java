package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.CityDao;
import com.hi.model.City;
import com.hi.model.Store;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao implements CityDao {

	public List<City> getDeliveryCities() {
		String sql = "select id as \"id\", cityId as \"cityId\", cityname as \"city\", provinceId as \"provinceId\" from T_CATER_SORTCITY c " +
				" where exists (select 1 from T_CATER_STORE s where s.cityId = c.cityId and s.DEPTTYPE='4' and (s.MEMO3 = '1' or s.MEMO4 = '1') ) " +
				" and ordertype = '0' and isavail = '1' order by sortnum ";
		List<City> cities = this.getBeansBySql(City.class, sql, null);
		return cities;
	}

	public Store getDefaultStore(String cityId) {
		String sql = "select s.storeid as \"storeId\" from T_CATER_STORE s" +
				" where s.cityId = :cityId and s.DEPTTYPE='4' and (s.MEMO3 = '1' or s.MEMO4 = '1') " +
				" order by storeid ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		List<Store> stores = this.getBeansBySql(Store.class, sql, params);
		return stores.size() > 0 ? stores.get(0) : null;
	}

}
