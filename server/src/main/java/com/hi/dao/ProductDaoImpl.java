package com.hi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.model.Product;
import com.hi.model.ProductPackage;
import com.hi.model.ProductPackageDetail;

@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	@SuppressWarnings("unchecked")
	public List<Product> getProductsByCategory(int categoryId) {
		List<Product> products = getSession()
				.createQuery(
						"from Product where CategoryId = ? AND isAvalible = 1 ")
				.setParameter(0, categoryId).list();
		return products;
	}

	@SuppressWarnings("unchecked")
	public int getProductPackageId(int productId) {
		List<ProductPackage> packages = getSession()
				.createQuery("from ProductPackage where ProductId = ?")
				.setParameter(0, productId).list();
		return packages.size() >= 1 ? packages.get(0).getId() : -1;
	}

	@SuppressWarnings("unchecked")
	public List<Product> getProductsByPackageId(int packageId) {
		List<ProductPackageDetail> details = getSession()
				.createQuery(
						"from ProductPackageDetail where ProductPackageId = ?")
				.setParameter(0, packageId).list();

		List<Product> results = new ArrayList<Product>();
		for (ProductPackageDetail d : details) {
			List<Product> products = getSession()
					.createQuery(
							"from Product where id = ? AND isAvalible=1")
					.setParameter(0, d.getProductId()).list();
			results.addAll(products);
		}

		return results;
	}

}
