package com.hi.service;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DiyGuodi;
import com.hi.model.Pack;
import com.hi.model.PackDish;

public interface DishService {
	List<DishType> getCategories();

	int countDishes(String storeId, String catId);
	
	List<Dish> getDishes(String storeId, String catId, int pageIndex);
	
	Dish getDishDetail(String dishId);

	int countPacks(String storeId, String catId);
	
	List<Pack> getPacks(String storeId, String catId, int pageIndex);
	
	List<PackDish> getPackDishes(String packId);
	
	int countDiyGuodis(String userId);
	
	List<DiyGuodi> getDiyGuodis(String userId, int pageIndex);

	String createDiyGuodi(DiyGuodi guodi);

	String updateDiyGuodi(DiyGuodi guodi);

	boolean deleteDiyGuodi(long id);
}
