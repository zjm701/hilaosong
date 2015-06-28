package com.hi.service;

import java.util.Date;
import java.util.List;

import com.hi.model.Store;

public interface StoreService {

	List<Store> getStores(String cityId);

	boolean isClose(String storeId, Date date);

	boolean isShutdown(String storeId);
	
	String getShutdownNotice(String storeId);
	
	String getDinningTimeType(String date,String storeId);
}
