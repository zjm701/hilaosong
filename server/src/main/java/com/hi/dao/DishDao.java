package com.hi.dao;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;

public interface DishDao {
	List<DishType> getCategories();
	
	List<DishVO> getDishes(String storeId, String categoryId, int pageIndex);
	
	Dish getDishDetail(String dishId, String storeId);
}
