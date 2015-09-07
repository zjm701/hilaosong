package com.hi.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.hi.common.HIConstants;
import com.hi.common.MessageCode;
import com.hi.model.DiyGuodi;
import com.hi.model.User;
import com.hi.service.DishService;

@Path("/")
public class DishAction extends BaseAction {

	@Autowired
	private DishService dishService;

	@GET
	@Path("/getcategories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories(@FormParam("storeId") String storeId) {
		return getSuccessJsonResponse(dishService.getCategories(storeId));
	}

	@GET
	@Path("/cntdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public String countDishes(@FormParam("storeId") String storeId, @FormParam("catId") String categoryId) {
		return getJsonString(dishService.countDishes(storeId, categoryId));
	}

	@GET
	@Path("/getdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDishes(@FormParam("storeId") String storeId, @FormParam("catId") String categoryId,
			@FormParam("pageIndex") int pageIndex, @FormParam("pageSize") int pageSize) {
		return getSuccessJsonResponse(dishService.getDishes(storeId, categoryId, pageIndex, pageSize));
	}

	@GET
	@Path("/getdishdetail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDishDetail(@FormParam("dishId") String dishId) {
		return getSuccessJsonResponse(dishService.getDishDetail(dishId));
	}

	@GET
	@Path("/cntpacks")
	@Produces(MediaType.APPLICATION_JSON)
	public String countPacks(@FormParam("storeId") String storeId, @FormParam("catId") String categoryId) {
		return getJsonString(dishService.countPacks(storeId, categoryId));
	}

	@GET
	@Path("/getpacks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPacks(@FormParam("storeId") String storeId, @FormParam("catId") String categoryId,
			@FormParam("pageIndex") int pageIndex) {
		return getSuccessJsonResponse(dishService.getPacks(storeId, categoryId, pageIndex));
	}

	@GET
	@Path("/getpackdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPackDishes(@FormParam("dishId") String packId) {
		return getSuccessJsonResponse(dishService.getPackDishes(packId));
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/getgdname")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDefaultGuodiName(@FormParam("userId") String userId) {
		int count = dishService.countDiyGuodis(userId);
		String name = userId;
		User u = (User) getSession().getAttribute(HIConstants.USER);
		if (u != null && userId.equals(u.getUser_entity_id() + "")) {
			name = u.getNickname();
		}
		name = name + "\u7684" + (count + 1) + "\u53f7\u9505\u5e95"; // **的*号锅底
		return "{\"guodiName\":\"" + name + "\"}";
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GET
	@Path("/cntgds")
	@Produces(MediaType.APPLICATION_JSON)
	public String countGuodis(@FormParam("userId") String userId) {
		return getJsonString(dishService.countDiyGuodis(userId));
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
		String userId = (String) getSession().getAttribute(HIConstants.LOGIN_ID);

		Gson gson = new Gson();
		DiyGuodi guodi = gson.fromJson(content, DiyGuodi.class);
		if (StringUtils.isEmpty(userId)) {
			return getJsonString(MessageCode.ERROR_NO_LOGGEDIN_USER);
		} else if (StringUtils.isEmpty(guodi.getGuodiName())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_NAME);
		} else if (StringUtils.isEmpty(guodi.getDishId())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_DISHID1);
		} else if (StringUtils.isEmpty(guodi.getDishId2())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_DISHID2);
		} else {
			guodi.setUserId(userId);
			String guodiId = dishService.createDiyGuodi(guodi);
			if (guodiId != null) {
				getSession().setAttribute(HIConstants.DIYGUODI_ID, guodiId);

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("guodiId", guodiId);
				m.put("respCode", MessageCode.SUCCESS_CREATE_GUODI.getKey());
				m.put("respMsg", MessageCode.SUCCESS_CREATE_GUODI.getDesc());
				return getJsonString(m);
			} else {
				return getJsonString(MessageCode.ERROR_CREATE_GUODI);
			}
		}
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

		if (StringUtils.isEmpty(guodi.getGuodiId() + "")) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODIID);
		} else if (StringUtils.isEmpty(guodi.getGuodiName())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_NAME);
		} else if (StringUtils.isEmpty(guodi.getDishId())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_DISHID1);
		} else if (StringUtils.isEmpty(guodi.getDishId2())) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODI_DISHID2);
		} else {
			String guodiId = dishService.updateDiyGuodi(guodi);
			if (guodiId != null) {
				getSession().setAttribute(HIConstants.DIYGUODI_ID, guodiId);

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("guodiId", guodiId);
				m.put("respCode", MessageCode.SUCCESS_UPDATE_GUODI.getKey());
				m.put("respMsg", MessageCode.SUCCESS_UPDATE_GUODI.getDesc());
				return getJsonString(m);
			} else {
				return getJsonString(MessageCode.ERROR_UPDATE_GUODI);
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/deletegd")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteGuodi(@FormParam("id") long id) {
		if (StringUtils.isEmpty(id + "")) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_GUODIID);
		} else {
			if (dishService.deleteDiyGuodi(id)) {
				return getJsonString(MessageCode.SUCCESS_DELETE_GUODI);
			} else {
				return getJsonString(MessageCode.ERROR_DELETE_GUODI);
			}
		}
	}
}
