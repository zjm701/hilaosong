package com.hi.service;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DiyGuodi;
import com.hi.model.Menu;
import com.hi.model.Pack;
import com.hi.model.PackDish;

public interface DishService {
	List<Menu> getCategories(String areaStoreId);

	int countDishes(String areaStoreId, String catId);
	
	List<Dish> getDishes(String areaStoreId, String catId, int pageIndex);
	
	Dish getDishDetail(String dishId);

	int countPacks(String areaStoreId, String catId);
	
	List<Pack> getPacks(String areaStoreId, String catId, int pageIndex);
	
	List<PackDish> getPackDishes(String packId);
	
	int countDiyGuodis(String userId);
	
	List<DiyGuodi> getDiyGuodis(String userId, int pageIndex);

	String createDiyGuodi(DiyGuodi guodi);

	String updateDiyGuodi(DiyGuodi guodi);

	boolean deleteDiyGuodi(long id);
}
