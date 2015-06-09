package com.hi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.ProductDao;
import com.hi.model.Product;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	public List<Product> getProductsByCategory(int categoryId) {
		return dao.getProductsByCategory(categoryId);
	}

	public int getProductPackageId(int productId) {
		return dao.getProductPackageId(productId);
	}

	public List<Product> getProductsByPackageId(int packageId) {
		return dao.getProductsByPackageId(packageId);
	}

}
