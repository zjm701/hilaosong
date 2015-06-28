package com.hi.control;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.GsonBuilder;
import com.hi.model.Category;
import com.hi.model.Product;
import com.hi.service.CategoryService;
import com.hi.service.ProductService;
import com.hi.tools.BaiduTools;

@Path("/")
public class IndexAction {
	private static Logger logger = LogManager.getLogger(IndexAction.class);
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GET
	@Path("/getCategories")
	@Produces("application/json")
	public Response getCategories() {
		logger.info("get categories");
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		List<Category> categories = categoryService.getAllCategories();
		b.entity(gb.create().toJson(categories));
		return b.build();
	}
	
	@GET
	@Path("/getProductsByCat")
	@Produces("application/json")
	public Response getProductsByCat(@FormParam("catId") int categoryId ) {
		logger.info("get products by category");
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson(productService.getProductsByCategory(categoryId)));
		return b.build();
	}
	
	@GET
	@Path("/getProductsByPrd")
	@Produces("application/json")
	public Response getProductsByPrd(@FormParam("prdId") int productId) {
		logger.info("get products by package product");
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		int packageId = productService.getProductPackageId(productId);
		List<Product> products = productService.getProductsByPackageId(packageId);
		b.entity(gb.create().toJson(products));
		return b.build();
	}
	
	@GET
	@Path("/testbaidu")
	@Produces("application/json")
	public Response testbaiduapi() {
		System.out.println(BaiduTools.getDIstance("40.056878,116.30815", "39.915285,116.403857"));
		
		System.out.println(BaiduTools.getDescByLocation("116.420593,40.076381"));
		
		//System.out.println(BaiduTools.getCusPointByAddress("北京市 海淀区 学院南路"));
		Response.ResponseBuilder b = Response.status(Status.OK);
		GsonBuilder gb = new GsonBuilder();
		b.entity(gb.create().toJson("good"));
		return b.build();
	}
}
