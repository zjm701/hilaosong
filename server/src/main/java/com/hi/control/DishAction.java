package com.hi.control;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.hi.common.HIConstants;
import com.hi.model.DiyGuodi;
import com.hi.model.User;
import com.hi.service.DishService;

@Path("/")
public class DishAction extends BaseAction {

	@Autowired
	private DishService dishService;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/getgdname")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDefaultGuodiName(@FormParam("userId") String userId) {
		long count = dishService.countDiyGuodis(userId);
		String name = userId;
		User u = (User) getSession().getAttribute(HIConstants.USER);
		if (u != null && userId.equals(u.getUser_entity_id()+"")) {
			name = u.getNickname();
		}
		name = name + "\u7684" + (count + 1) + "\u53f7\u9505\u5e95"; // **的*号锅底
		return "{\"guodiName\":\"" + name + "\"}";
	}

	/**
	 * 
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@GET
	@Path("/getgds")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGuodis(@FormParam("userId") String userId, @FormParam("pageIndex") int pageIndex) {
		List<DiyGuodi> guodis = dishService.getDiyGuodis(userId, pageIndex);
		return getSuccessJsonResponse(guodis);
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	@POST
	@Path("/creategd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createGuodi(String content) {
		getSession().removeAttribute(HIConstants.DIYGUODI_ID);
		System.out.println("==> content:" + content);

		Gson gson = new Gson();
		DiyGuodi guodi = gson.fromJson(content, DiyGuodi.class);

		guodi.setUserId((String) getSession().getAttribute(HIConstants.LOGIN_ID));
		String guodiId = dishService.createDiyGuodi(guodi);
		String message = "";
		if (guodiId != null) {
			getSession().setAttribute(HIConstants.DIYGUODI_ID, guodiId);
			message = "\u521b\u5efaDIY\u9505\u5e95\u6210\u529f, \u9505\u5e95ID: " + guodiId; // 创建DIY锅底成功, 锅底ID:
		} else {
			message = "\u521b\u5efaDIY\u9505\u5e95\u5931\u8d25"; // 创建DIY锅底失败
		}
		return getMessageJsonResult(message);
	}
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	@POST
	@Path("/updategd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateGuodi(String content) {
		System.out.println("==> content:" + content);

		Gson gson = new Gson();
		DiyGuodi guodi = gson.fromJson(content, DiyGuodi.class);

		String guodiId = dishService.updateDiyGuodi(guodi);
		String message = "";
		if (guodiId != null) {
			message = "\u66f4\u65b0DIY\u9505\u5e95\u6210\u529f"; // 更新DIY锅底成功
		} else {
			message = "\u66f4\u65b0DIY\u9505\u5e95\u5931\u8d25"; // 更新DIY锅底失败
		}
		return getMessageJsonResult(message);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/deletegd")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGuodi(@FormParam("id") long id) {
		boolean s = dishService.deleteDiyGuodi(id);
		String message = s?"\u5220\u9664DIY\u9505\u5e95\u6210\u529f":"\u5220\u9664DIY\u9505\u5e95\u5931\u8d25"; //删除DIY锅底成功 删除DIY锅底失败
		return getSuccessJsonResponse(message);
	}
}
