package com.hi.service;

import java.util.List;

import com.hi.model.Product;

public interface ProductService {
	List<Product> getProductsByCategory(int categoryId);

	int getProductPackageId(int productId);

	List<Product> getProductsByPackageId(int packageId);
}
