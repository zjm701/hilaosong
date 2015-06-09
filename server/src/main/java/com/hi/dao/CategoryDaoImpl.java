package com.hi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.model.Category;


@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDao implements CategoryDao {
	@SuppressWarnings("unchecked")
	public List<Category> getAllCategories() {
		List<Category> categories = getSession().createQuery("from Category")
				.list();
		return categories;
	}
}
