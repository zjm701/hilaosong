package com.hi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.CityDao;
import com.hi.dao.DishDao;
import com.hi.dao.DiyGuodiDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;
import com.hi.model.DiyGuodi;
import com.hi.model.PackDish;
import com.hi.service.DishService;

@Service("dishService")
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao ddao;
	
	@Autowired
	private CityDao cdao;
	
	@Autowired
	private DiyGuodiDao gdao;

	public List<DishType> getCategories() {
		return ddao.getCategories();
	}

	public List<DishVO> getDishes(String storeId, String catId, int pageIndex) {
		return ddao.getDishes(storeId, catId, pageIndex);
	}

	public Dish getDishDetail(String dishId) {
		return ddao.getDishDetail(dishId);
	}
	
	public List<DishVO> getPacks(String storeId, String catId, int pageIndex) {
		String areaStoreId = cdao.getAreaStore(storeId).getStoreId();
		return ddao.getDishes(areaStoreId, catId, pageIndex);
	}
	
	public List<PackDish> getPackDishes(String packId) {
		return ddao.getPackDishes(packId);
	}

	@Override
	public long countDiyGuodis(String userId) {
		return gdao.countDiyGuodis(userId);
	}

	@Override
	public List<DiyGuodi> getDiyGuodis(String userId, int pageIndex) {
		return gdao.getDiyGuodis(userId, pageIndex);
	}
	
	@Override
	public String createDiyGuodi(DiyGuodi guodi){
		if(gdao.createDiyGuodi(guodi)){
			return guodi.getGuodiId()+"";
		}else{
			return null;
		}
	}

	@Override
	public boolean deleteDiyGuodi(long id) {
		return gdao.deleteDiyGuodi(id);
	}
}
