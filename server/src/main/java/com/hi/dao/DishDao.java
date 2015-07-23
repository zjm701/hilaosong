package com.hi.dao;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.Pack;
import com.hi.model.PackDish;

public interface DishDao {
	List<DishType> getCategories();

	int countDishes(String storeId, String categoryId);

	List<Dish> getDishes(String storeId, String categoryId, int pageIndex);

	Dish getDishDetail(String dishId);
	
	int countPacks(String storeId, String categoryId);
	
	List<Pack> getPacks(String storeId, String categoryId, int pageIndex);

	List<PackDish> getPackDishes(String packId);
}
