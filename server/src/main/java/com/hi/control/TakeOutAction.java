package com.hi.control;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.City;
import com.hi.model.DishType;
import com.hi.service.CityService;
import com.hi.service.DishService;

@Path("/")
public class TakeOutAction extends BaseAction {

	@Autowired
	private CityService cityService;
	
	@Autowired
	private DishService dishService;

	@GET
	@Path("/getcities")
	@Produces("application/json")
	public Response getCities() {
		List<City> cities = cityService.getDeliveryCities();
		return getSuccessJsonResponse(cities);
	}
	
	@GET
	@Path("/getcategories")
	@Produces("application/json")
	public Response getCategories() {
		List<DishType> cats = dishService.getCategories();
		return getSuccessJsonResponse(cats);
	}

}
