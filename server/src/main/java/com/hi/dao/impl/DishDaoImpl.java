package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.common.Pagination;
import com.hi.dao.AbstractDao;
import com.hi.dao.DishDao;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;
import com.hi.model.PackDish;

@Repository("dishDao")
public class DishDaoImpl extends AbstractDao implements DishDao {

	public List<DishType> getCategories() {
		String sql = "select dishtypeid as \"dishTypeId\", dishtypename as \"dishTypeName\", parentid as \"parentId\", isrequired as \"isRequired\" from T_CATER_DISHTYPE "
				+ " where parentid = 0 and identifier = 1 order by typesort ";
		List<DishType> categories = this.getBeansBySql(DishType.class, sql);
		return categories;
	}

	public List<DishVO> getDishes(String storeId, String categoryId,
			int pageIndex) {
		String sql = "select d.dishId as \"dishId\", d.storeDishId as \"storeDishId\", d.storeDishName as \"storeDishName\", d.unitPrice as \"unitPrice\", "
				+ " d.bigImageAddr as \"bigImageAddr\", d.type as \"type\", hd.dishId as \"halfDishId\", hd.storeDishId as \"halfStoreDishId\", hd.unitPrice as \"halfPrice\" "
				+ " from T_CATER_DISH d"
				+ " left join (select linkStoreDishId, dishId, storeDishId, unitPrice from T_CATER_DISH "
				+ "		where storeId = :storeId and dishcategory = :categoryId and dishShareType = 2) hd"
				+ "	on hd.linkStoreDishId = d.storeDishId "
				+ " where storeId = :storeId and dishcategory = :categoryId and dishShareType != 2 "
				+ " order by dishSort";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("categoryId", categoryId);

		Pagination pagn = new Pagination();
		pagn.setPageIndex(pageIndex);

		List<DishVO> dishes = this.getBeansBySql(DishVO.class, sql, params,
				pagn);
		return dishes;
	}

	public Dish getDishDetail(String dishId) {
		String sql = "select dishid as \"dishId\", dishname as \"dishName\", guideprice as \"guidePrice\", unitprice as \"unitPrice\", "
				+ " description as \"description\", isrequired as \"isRequired\", dishunit as \"dishUnit\", dishweight as \"dishWeight\", "
				+ " dishsharetype as \"dishShareType\", isrecommend as \"isRecommend\", linkstoredishid as \"linkStoreDishId\", type as \"type\", "
				+ " bigImageAddr as \"bigImageAddr\", mediumImageAddr as \"mediumImageAddr\", storeDishId as \"storeDishId\" "
				+ " from T_CATER_DISH " 
				+ " where dishId = :dishId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dishId", dishId);
		List<Dish> dishes = this.getBeansBySql(Dish.class, sql, params);
		return dishes.isEmpty() ? null : dishes.get(0);
	}

	public List<PackDish> getPackDishes(String packId) {
		String sql = "select p.packId as \"packId\", p.dishId as \"dishId\", p.dishNumber as \"dishNumber\", "
				+ " p.innerId as \"innerId\", p.innerNumber as \"innerNumber\", p.innerName as \"innerName\", "
				+ " d.dishName as \"dishName\", d.unitPrice as \"unitPrice\", "
				+ " d.bigImageAddr as \"bigImageAddr\", d.mediumImageAddr as \"mediumImageAddr\" "
				+ " from T_CATER_PACKDISH p "
				+ " inner join T_CATER_DISH d on d.dishId = p.dishId "
				+ " where p.packId = :packId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("packId", packId);
		List<PackDish> dishes = this.getBeansBySql(PackDish.class, sql, params);
		return dishes;
	}
}
