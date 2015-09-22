package com.hi.dao;

import java.util.List;

import com.hi.model.Store;

public interface StoreDao {

	Store getDefaultStore(String cityId);
	
	Store getAreaStore(String storeId);

	List<Store> getStores(String provinceId, String cityId, String orderType, String cuspoint);

	Store getStore(String storeId);

	Store getStore(String storeId, String cuspoint);
}
