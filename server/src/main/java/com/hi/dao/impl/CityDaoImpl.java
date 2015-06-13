package com.hi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.CityDao;
import com.hi.model.City;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao implements CityDao {

	public List<City> getDeliveryCities() {
		String sql = "select id as \"id\", cityId as \"cityId\", cityname as \"city\", provinceId as \"provinceId\" from T_CATER_SORTCITY c " +
				" where exists (select 1 from T_CATER_STORE s where s.cityId = c.cityId and s.DEPTTYPE='4' and s.MEMO3 = '1') " +
				" and ordertype = '0' and isavail = '1' order by sortnum ";
		List<City> cities = this.getBeansBySql(City.class, sql, null);
		return cities;
	}

}
