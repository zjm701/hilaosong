package com.hi.control;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.City;
import com.hi.service.CityService;

@Path("/")
public class TakeOutAction extends BaseAction {

	@Autowired
	private CityService cityService;

	@GET
	@Path("/getcities")
	@Produces("application/json")
	public Response getcities() {
		List<City> cities = cityService.getDeliveryCities();
		return getSuccessJsonResponse(cities);
	}

}
