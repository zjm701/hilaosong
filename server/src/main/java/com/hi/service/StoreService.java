package com.hi.service;

import java.util.List;

import com.hi.model.Store;

public interface StoreService {
	
	List<Store> getStores(String cityId);
	
}
