package com.hi.control;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.City;
import com.hi.model.Dish;
import com.hi.model.DishType;
import com.hi.model.DishVO;
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
		getSession().setAttribute("cityId", cities.get(0).getCityId());
		return getSuccessJsonResponse(cities);
	}
	
	@GET
	@Path("/getcategories")
	@Produces("application/json")
	public Response getCategories() {
		List<DishType> cats = dishService.getCategories();
		return getSuccessJsonResponse(cats);
	}
	
	@GET
	@Path("/getdishes")
	@Produces("application/json")
	public Response getDishes(@FormParam("cityId") String cityId, @FormParam("catId") String categoryId, @FormParam("pageIndex") int pageIndex) {
		if(cityId == null){
			cityId = (String)getSession().getAttribute("cityId");
		}else{
			getSession().setAttribute("cityId", cityId);
		}
		String storeId = cityService.getDefaultStore(cityId).getStoreId();
		getSession().setAttribute("storeId", storeId);
		List<DishVO> dishes = dishService.getDishes(storeId, categoryId, pageIndex);
		return getSuccessJsonResponse(dishes);
	}
	
	@GET
	@Path("/getdishdetail")
	@Produces("application/json")
	public Response getDishes(@FormParam("storeDishId") String dishId) {
		String storeId = (String)getSession().getAttribute("storeId");
		Dish dish = dishService.getDishDetail(dishId, storeId);
		return getSuccessJsonResponse(dish);
	}

	private HttpSession getSession() {
		return getRequest().getSession();
	}

}
