package com.hi.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/helloservice")
public interface HelloWorld {
	@GET
	@Path(value = "/sayHelloString")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sayHelloString(@QueryParam("name") String name);

	@GET
	@Path(value = "/sayHelloJson")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHelloJson(@QueryParam("name") String name);

	@GET
	@Path(value = "/sayHelloXml")
	@Produces(MediaType.APPLICATION_XML)
	public Response sayHelloXml(@QueryParam("name") String name);
}
