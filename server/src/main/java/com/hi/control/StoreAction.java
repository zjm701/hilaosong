package com.hi.control;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.Store;
import com.hi.service.StoreService;

@Path("/")
public class StoreAction extends BaseAction {

	@Autowired
	private StoreService storeService;

	/**
	 * "/wap/toSendDishesPage" (Mobile version)
	 * 
	 * @param cityId
	 * @return
	 */
	@GET
	@Path("/getstores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStores(@FormParam("cityId") String cityId) {
		List<Store> stores = storeService.getStores(cityId);
		return getSuccessJsonResponse(stores);
	}
}
