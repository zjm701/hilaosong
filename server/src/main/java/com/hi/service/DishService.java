package com.hi.service;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;

public interface DishService {
	List<DishType> getCategories();
	
	List<Dish> getDishes(String storeId, String catId);
}
