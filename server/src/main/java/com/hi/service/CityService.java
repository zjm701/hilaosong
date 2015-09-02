package com.hi.service;

import java.util.List;

import com.hi.model.City;
import com.hi.model.DeliveryLimit;

public interface CityService {

	List<City> getDeliveryCities();

	City getCity(String cityId);

	/**
	 * 获得外送费单价
	 * 
	 * @param cityId
	 * @return
	 */
	double getDeliveryUnitPrice(String cityId);

	/**
	 * 获得起送标准
	 * 
	 * @param cityId
	 * @return
	 */
	String getMinAmount(String cityId);

	/**
	 * 获得送餐的所有限制
	 * 
	 * @param storeId
	 * @param cityId
	 * @param orderType
	 * @return
	 */
	DeliveryLimit getDeliveryLimit(String storeId, String cityId, String orderType);
}
