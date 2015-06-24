package com.hi.dao;

import java.util.List;

import com.hi.model.Store;

public interface StoreDao {

	List<Store> getStores(String cityId);

}
