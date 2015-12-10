package com.hi.control;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.model.SysConfig;
import com.hi.service.SysConfigService;

@Path("/")
public class SysConfigAction  extends BaseAction {

	@Autowired
	private SysConfigService sysConfigService;
	

	/**
	 * 外卖须知
	 * @return
	 */
	@GET
	@Path(value = "/getxuzhi0")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getxuzhi0() {
		//List<SysConfig> getSysConfigs(String paramType, String paramCode, String paramSrc);
		List<SysConfig> address = sysConfigService.getSysConfigs("-1", "0", "0");
		return getSuccessJsonResponse(address.get(0));
	}
/*
	*//**
	 * 外卖须知
	 * @return
	 *//*
	@GET
	@Path(value = "/getxuzhi1")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getxuzhi1() {
		List<SysConfig> address = sysConfigService.getSysConfigs("-1", "0", "1");
		return getSuccessJsonResponse(address.get(0));
	}

	*//**
	 * 外卖须知
	 * @return
	 *//*
	@GET
	@Path(value = "/getxuzhi2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getxuzhi2() {
		List<SysConfig> address = sysConfigService.getSysConfigs("-1", "0", "2");
		return getSuccessJsonResponse(address.get(0));
	}*/

	/**
	 * 提示
	 * @return
	 */
	@GET
	@Path(value = "/getTeminder")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeminder() {
		List<SysConfig> address = sysConfigService.getSysConfigs("-1", "0", "3");
		if(address.size()>0){
			return getSuccessJsonResponse(address.get(0));
		}else{
			return getFailedJsonResponse("null");
		}
	}
}
