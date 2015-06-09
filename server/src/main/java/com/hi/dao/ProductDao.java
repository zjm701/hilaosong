package com.hi.dao;

import java.util.List;

import com.hi.model.Product;

public interface ProductDao {
	List<Product> getProductsByCategory(int categoryId);
	
	int getProductPackageId(int productId);
	
	List<Product> getProductsByPackageId(int packageId);
}
