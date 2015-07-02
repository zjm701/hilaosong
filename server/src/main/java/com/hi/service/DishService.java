package com.hi.service;

import java.util.List;

import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;
import com.hi.model.DiyGuodi;
import com.hi.model.PackDish;

public interface DishService {
	List<DishType> getCategories();
	
	List<DishVO> getDishes(String storeId, String catId, int pageIndex);
	
	Dish getDishDetail(String dishId);
	
	List<DishVO> getPacks(String storeId, String catId, int pageIndex);
	
	List<PackDish> getPackDishes(String packId);
	
	long countDiyGuodis(String userId);
	
	List<DiyGuodi> getDiyGuodis(String userId, int pageIndex);

	String createDiyGuodi(DiyGuodi guodi);

	String updateDiyGuodi(DiyGuodi guodi);

	boolean deleteDiyGuodi(long id);
}
