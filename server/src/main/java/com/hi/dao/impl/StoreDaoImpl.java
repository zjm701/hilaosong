package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.OrderType;
import com.hi.dao.AbstractDao;
import com.hi.dao.StoreDao;
import com.hi.model.Store;
import com.hi.tools.StringTools;

@Repository("storeDao")
public class StoreDaoImpl extends AbstractDao implements StoreDao {

	/*
	 * 不带 距离
	 * 
	 */
	public List<Store> getStores(String cityId, String orderType) {
		return getStores(cityId, orderType, null);
	}

	/*
	 * 带 距离
	 * 
	 */
	public List<Store> getStores(String cityId, String orderType, String cuspoint) {
		// memo1='1': 网络订餐
		// orderType : SEND_OUT:s.memo3=1 TAKE_AWAY:s.memo4=1

		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityId", cityId);
		sb.append("select s.storeid as \"storeId\", s.storeName as \"storeName\", s.storeAddress as \"storeAddress\", ")
				.append(" s.storeTele as \"storeTele\", s.storeCode as \"storeCode\", s.storeType as \"storeType\", ")
				.append(" s.provinceId as \"provinceId\", s.cityId as \"cityId\", s.postCode as \"postCode\", ")
				.append(" s.deptid as \"deptId\", s.deptname as \"deptName\", s.deptType as \"deptType\", ")
				.append(" s.coordinate as \"coordinate\", s.baiduIid as \"baiduIid\"");
		if (StringTools.isNotEmpty(cuspoint)) {
			sb.append(", func_distance(:cuspoint, s.coordinate) as \"distance\"");
			params.put("cuspoint", cuspoint);
		}
		sb.append(" from T_CATER_STORE s ").append(
				" where s.cityId = :cityId and s.deptType='4' and s.memo1= '1' and s.isactive = 'Y' and s.isDisplay = '1'");
		if (OrderType.SEND_OUT.getKey().equals(orderType)) {
			sb.append(" and s.memo3 = '1'");
		} else if (OrderType.TAKE_AWAY.getKey().equals(orderType)) {
			sb.append(" and s.memo4 = '1'");
		}
		sb.append(" order by storeid ");
		return this.getBeansBySql(Store.class, sb.toString(), params);
	}
	
	/*
	 * 不带 距离
	 * 
	 */
	public Store getStore(String storeId) {
		return this.getStore(storeId, null);
	}
	
	/*
	 * 带 距离
	 * 
	 */
	public Store getStore(String storeId, String cuspoint) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		sb.append("select s.storeid as \"storeId\", s.storeName as \"storeName\", s.storeAddress as \"storeAddress\", ")
				.append(" s.storeTele as \"storeTele\", s.storeCode as \"storeCode\", s.storeType as \"storeType\", ")
				.append(" s.provinceId as \"provinceId\", s.cityId as \"cityId\", s.postCode as \"postCode\", ")
				.append(" s.deptid as \"deptId\", s.deptname as \"deptName\", s.deptType as \"deptType\", ")
				.append(" s.coordinate as \"coordinate\", s.baiduIid as \"baiduIid\"");
		if (StringTools.isNotEmpty(cuspoint)) {
			sb.append(", func_distance(:cuspoint, s.coordinate) as \"distance\"");
			params.put("cuspoint", cuspoint);
		}
		sb.append(" from T_CATER_STORE s where s.storeid = :storeId ");
		return this.getUniqueBeanBySql(Store.class, sb.toString(), params);
	}
}
