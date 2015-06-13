package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.DishDao;
import com.hi.model.DishType;
import com.hi.service.DishService;

@Service("dishService")
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao dao;

	public List<DishType> getCategories() {
		return dao.getCategories();
	}

}
