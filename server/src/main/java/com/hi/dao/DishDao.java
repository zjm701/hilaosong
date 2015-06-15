package com.hi.dao;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;

public interface DishDao {
	List<DishType> getCategories();
	
	List<Dish> getDishes(String storeId, String categoryId);
}
