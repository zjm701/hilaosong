package com.hi.dao;

import java.util.List;

import com.hi.model.Store;

public interface StoreDao {

	List<Store> getStores(String cityId, String orderType);

	List<Store> getStores(String cityId, String orderType, String cuspoint);

	Store getStore(String storeId);

	Store getStore(String storeId, String cuspoint);
}
