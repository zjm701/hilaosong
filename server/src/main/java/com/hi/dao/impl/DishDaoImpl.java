package com.hi.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.DishDao;
import com.hi.model.Dish;
import com.hi.model.DishType;

@Repository("dishDao")
public class DishDaoImpl extends AbstractDao implements DishDao {

	public List<DishType> getCategories() {
		String sql = "select dishtypeid as \"dishTypeId\", dishtypename as \"dishTypeName\", parentid as \"parentId\", isrequired as \"isRequired\" from T_CATER_DISHTYPE " +
				" where parentid = 0 and identifier = 1 order by typesort ";
		List<DishType> categories = this.getBeansBySql(DishType.class, sql, null);
		return categories;
	}

	public List<Dish> getDishes(String storeId, String categoryId) {
		String sql = "select dishid as \"dishId\", dishname as \"dishName\", guideprice as \"guidePrice\", unitprice as \"unitPrice\", " +
				" description as \"description\", isrequired as \"isRequired\", dishunit as \"dishUnit\", dishweight as \"dishWeight\", " +
				" dishsharetype as \"dishShareType\", isrecommend as \"isRecommend\", linkstoredishid as \"linkStoreDishId\", type as \"type\", " +
				" bigImageAddr as \"bigImageAddr\", mediumImageAddr as \"mediumImageAddr\", storeDishId as \"storeDishId\" " +
				" from T_CATER_DISH " +
				" where storeId = :storeId and dishcategory = :categoryId and type = 1 " +
				" order by dishSort";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("storeId", storeId);
		params.put("categoryId", categoryId);
		List<Dish> dishes = this.getBeansBySql(Dish.class, sql, params);
		return dishes;
	}

}
