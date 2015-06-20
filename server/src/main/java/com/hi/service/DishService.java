package com.hi.service;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;

public interface DishService {
	List<DishType> getCategories();
	
	List<DishVO> getDishes(String storeId, String catId, int pageIndex);
	
	Dish getDishDetail(String dishId, String storeId);
}
