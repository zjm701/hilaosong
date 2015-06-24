package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.StoreDao;
import com.hi.model.Store;

@Repository("storeDao")
public class StoreDaoImpl extends AbstractDao implements StoreDao {

	public List<Store> getStores(String cityId) {
		String sql = "select s.storeid as \"storeId\", s.storeName as \"storeName\", s.storeAddress as \"storeAddress\", "
				+ " s.storeTele as \"storeTele\", s.storeCode as \"storeCode\", s.storeType as \"storeType\", "
				+ " s.provinceId as \"provinceId\", s.cityId as \"cityId\", s.postCode as \"postCode\", "
				+ " s.coordinate as \"coordinate\", s.baiduIid as \"baiduIid\", s.deptType as \"deptType\" "
				+ " from T_CATER_STORE s "
				+ " where s.cityId = :cityId and s.deptType='4' and s.memo4 = '1' "
				+ " order by storeid ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		return this.getBeansBySql(Store.class, sql, params);
	}
}
