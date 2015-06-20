package com.hi.dao;

import java.util.List;

import com.hi.model.City;
import com.hi.model.Store;

public interface CityDao {
	List<City> getDeliveryCities();
	
	Store getDefaultStore(String cityId);
	
	Store getAreaStore(String storeId);
	
}
