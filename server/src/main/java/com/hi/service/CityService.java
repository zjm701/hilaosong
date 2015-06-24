package com.hi.service;

import java.util.List;

import com.hi.model.City;
import com.hi.model.Store;

public interface CityService {
	List<City> getDeliveryCities();
	
	City getCity(String cityId);
	
	Store getDefaultStore(String cityId);
	
	Store getAreaStore(String storeId);
}
