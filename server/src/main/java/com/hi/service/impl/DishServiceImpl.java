package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.DishDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;
import com.hi.service.DishService;

@Service("dishService")
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao dao;

	public List<DishType> getCategories() {
		return dao.getCategories();
	}

	public List<DishVO> getDishes(String storeId, String catId, int pageIndex) {
		return dao.getDishes(storeId, catId, pageIndex);
	}

	public Dish getDishDetail(String dishId, String storeId) {
		return dao.getDishDetail(dishId, storeId);
	}

}
