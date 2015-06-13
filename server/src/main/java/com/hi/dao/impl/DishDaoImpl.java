package com.hi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.DishDao;
import com.hi.model.DishType;

@Repository("dishDao")
public class DishDaoImpl extends AbstractDao implements DishDao {

	public List<DishType> getCategories() {
		String sql = "select dishtypeid as \"dishTypeId\", dishtypename as \"dishTypeName\", parentid as \"parentId\", isrequired as \"isRequired\" from T_CATER_DISHTYPE " +
				" where parentid = 0 and identifier = 1 order by typesort ";
		List<DishType> categories = this.getBeansBySql(DishType.class, sql, null);
		return categories;
	}

}
