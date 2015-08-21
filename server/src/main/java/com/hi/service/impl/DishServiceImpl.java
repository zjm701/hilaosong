package com.hi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.common.SystemSetting;
import com.hi.dao.DishDao;
import com.hi.dao.DiyGuodiDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DiyGuodi;
import com.hi.model.Pack;
import com.hi.model.PackDish;
import com.hi.service.DishService;

@Service("dishService")
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao ddao;
	
	@Autowired
	private DiyGuodiDao gdao;
	
	public List<DishType> getCategories(String storeId) {
		List<DishType> menus = new ArrayList<DishType>();
		DishType pack = new DishType(SystemSetting.getSetting("pack"), "\u5957\u9910"); // 套餐
		DishType guodi = new DishType(SystemSetting.getSetting("guodi"), "\u9505\u5E95"); // 锅底

		menus.add(pack);
		menus.add(guodi);
		for (DishType dt : ddao.getCategories4Dish(storeId)) {
			menus.add(dt);
		}
		for (DishType dt : ddao.getCategories4Wine(storeId)) {
			menus.add(dt);
		}
		return menus;
	}
	
	@Override
	public int countDishes(String storeId, String catId) {
		return ddao.countDishes(storeId, catId);
	}
	
	public List<Dish> getDishes(String storeId, String catId, int pageIndex) {
		return ddao.getDishes(storeId, catId, pageIndex);
	}

	public Dish getDishDetail(String dishId) {
		return ddao.getDishDetail(dishId);
	}
	
	@Override
	public int countPacks(String storeId, String catId) {
		return ddao.countPacks(storeId, catId);
	}
	
	public List<Pack> getPacks(String storeId, String catId, int pageIndex) {
		return ddao.getPacks(storeId, catId, pageIndex);
	}
	
	public List<PackDish> getPackDishes(String packId) {
		return ddao.getPackDishes(packId);
	}

	@Override
	public int countDiyGuodis(String userId) {
		return gdao.countDiyGuodis(userId);
	}

	@Override
	public List<DiyGuodi> getDiyGuodis(String userId, int pageIndex) {
		return gdao.getDiyGuodis(userId, pageIndex);
	}
	
	@Override
	public String createDiyGuodi(DiyGuodi guodi){
		if (gdao.createDiyGuodi(guodi)) {
			return guodi.getGuodiId() + "";
		} else {
			return null;
		}
	}
	
	@Override
	public String updateDiyGuodi(DiyGuodi guodi){
		if (gdao.updateDiyGuodi(guodi)) {
			return guodi.getGuodiId() + "";
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteDiyGuodi(long id) {
		return gdao.deleteDiyGuodi(id);
	}
}
