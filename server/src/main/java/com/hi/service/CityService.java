package com.hi.service;

import java.util.List;

import com.hi.model.City;

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
	String getDeliveryLimitMoney(String cityId);
}
