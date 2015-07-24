package com.hi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.common.SystemSetting;
import com.hi.dao.DishDao;
import com.hi.dao.DiyGuodiDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DiyGuodi;
import com.hi.model.Menu;
import com.hi.model.Pack;
import com.hi.model.PackDish;
import com.hi.service.DishService;

@Service("dishService")
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao ddao;
	
	@Autowired
	private DiyGuodiDao gdao;
	
	private List<Menu> menus = null;
	
	public List<Menu> getCategories(String areaStoreId) {
		generateCategories();
		for (DishType dt : ddao.getCategories4Dish(areaStoreId)) {
			menus.add(new Menu(dt));
		}
		for (DishType dt : ddao.getCategories4Wine(areaStoreId)) {
			menus.add(new Menu(dt));
		}
		return menus;
	}
	
	private void generateCategories() {
		if (menus == null) {
			Menu pack = new Menu(new DishType(SystemSetting.getSetting("pack"), "\u5957\u9910"), Menu.FIRST_LEVEL); // 套餐
			Menu guodi = new Menu(new DishType(SystemSetting.getSetting("guodi"), "\u9505\u5E95"), Menu.FIRST_LEVEL); // 锅底
			
			menus = new ArrayList<Menu>();
			menus.add(pack);
			menus.add(guodi);
		}
	}
	
	/**
	 * old
	 */
//	private void generateCategories() {
//		if (menus == null) {
//			Menu guodi = new Menu("guodi", "\u9505\u5E95", Menu.FIRST_LEVEL); // 锅底
//			guodi.addChild(new Menu(new DishType(SystemSetting.getSetting("guodi"), "\u9505\u5E95"))); // 锅底
//			guodi.addChild(new Menu("mydiygd", "\u6211\u7684\u9505\u5E95")); // 我的锅底
//
//			Menu pack = new Menu(new DishType(SystemSetting.getSetting("pack"), "\u5957\u9910"), Menu.FIRST_LEVEL); // 套餐
//			pack.setId("pack");
//			
//			dish = new Menu("dish", "\u83DC\u54C1", Menu.FIRST_LEVEL); // 菜品
//
//			wine = new Menu("wine", "\u9152\u6C34", Menu.FIRST_LEVEL); // 酒水
//
//			Menu historyorders = new Menu("historyorders", "\u5386\u53F2\u8BA2\u5355", Menu.FIRST_LEVEL); // 历史订单
//
//			menus = new ArrayList<Menu>();
//			menus.add(guodi);
//			menus.add(pack);
//			menus.add(dish);
//			menus.add(wine);
//			menus.add(historyorders);
//		}
//	}

	@Override
	public int countDishes(String areaStoreId, String catId) {
		return ddao.countDishes(areaStoreId, catId);
	}
	
	public List<Dish> getDishes(String areaStoreId, String catId, int pageIndex) {
		return ddao.getDishes(areaStoreId, catId, pageIndex);
	}

	public Dish getDishDetail(String dishId) {
		return ddao.getDishDetail(dishId);
	}
	
	@Override
	public int countPacks(String areaStoreId, String catId) {
		return ddao.countPacks(areaStoreId, catId);
	}
	
	public List<Pack> getPacks(String areaStoreId, String catId, int pageIndex) {
		return ddao.getPacks(areaStoreId, catId, pageIndex);
	}
	
	public List<PackDish> getPackDishes(String packId) {
		return ddao.getPackDishes(packId);
	}

	@Override
	public int countDiyGuodis(String userId) {
		return gdao.countDiyGuodis(userId);
	}

	@Override
	public List<DiyGuodi> getDiyGuodis(String userId, int pageIndex) {
		return gdao.getDiyGuodis(userId, pageIndex);
	}
	
	@Override
	public String createDiyGuodi(DiyGuodi guodi){
		if (gdao.createDiyGuodi(guodi)) {
			return guodi.getGuodiId() + "";
		} else {
			return null;
		}
	}
	
	@Override
	public String updateDiyGuodi(DiyGuodi guodi){
		if (gdao.updateDiyGuodi(guodi)) {
			return guodi.getGuodiId() + "";
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteDiyGuodi(long id) {
		return gdao.deleteDiyGuodi(id);
	}
}
