package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.StoreDao;
import com.hi.model.Store;
import com.hi.service.StoreService;

@Service("storeService")
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreDao dao;

	public List<Store> getStores(String cityId) {
		if (cityId == null) {
			cityId = CityServiceImpl.CITY_BEIJING;
		}
		return dao.getStores(cityId);
	}

}
