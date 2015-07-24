package com.hi.control;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.common.HIConstants;
import com.hi.common.SystemSetting;
import com.hi.model.City;
import com.hi.model.Order;
import com.hi.model.OrderDish;
import com.hi.model.OrderPack;
import com.hi.service.CityService;
import com.hi.service.DishService;
import com.hi.service.OrderService;
import com.hi.service.StoreService;
import com.hi.tools.HttpUtil;

@Path("/")
public class TakeOutAction extends BaseAction {

	@Autowired
	private CityService cityService;

	@Autowired
	private DishService dishService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private OrderService orderService;

	@GET
	@Path("/getcities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCities() {
		List<City> cities = cityService.getDeliveryCities();
		getSession().setAttribute(HIConstants.CITYID, cities.get(0).getCityId());
		return getSuccessJsonResponse(cities);
	}

	@GET
	@Path("/getcategories")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories(@FormParam("cityId") String cityId) {
		saveIntoSession(cityId);
		String areaStoreId = (String) getSession().getAttribute(HIConstants.AREASTOREID);
		return getSuccessJsonResponse(dishService.getCategories(areaStoreId));
	}

	@GET
	@Path("/cntdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public String countDishes(@FormParam("cityId") String cityId, @FormParam("catId") String categoryId) {
		saveIntoSession(cityId);
		String areaStoreId = (String) getSession().getAttribute(HIConstants.AREASTOREID);
		return getJsonString(dishService.countDishes(areaStoreId, categoryId));
	}

	@GET
	@Path("/getdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDishes(@FormParam("cityId") String cityId, @FormParam("catId") String categoryId,
			@FormParam("pageIndex") int pageIndex) {
		saveIntoSession(cityId);
		String areaStoreId = (String) getSession().getAttribute(HIConstants.AREASTOREID);
		return getSuccessJsonResponse(dishService.getDishes(areaStoreId, categoryId, pageIndex));
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
	public String countPacks(@FormParam("cityId") String cityId, @FormParam("catId") String categoryId) {
		saveIntoSession(cityId);
		String areaStoreId = (String) getSession().getAttribute(HIConstants.AREASTOREID);
		return getJsonString(dishService.countPacks(areaStoreId, categoryId));
	}

	@GET
	@Path("/getpacks")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPacks(@FormParam("cityId") String cityId, @FormParam("catId") String categoryId,
			@FormParam("pageIndex") int pageIndex) {
		saveIntoSession(cityId);
		String areaStoreId = (String) getSession().getAttribute(HIConstants.AREASTOREID);
		return getSuccessJsonResponse(dishService.getPacks(areaStoreId, categoryId, pageIndex));
	}

	@GET
	@Path("/getpackdishes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPackDishes(@FormParam("dishId") String packId) {
		return getSuccessJsonResponse(dishService.getPackDishes(packId));
	}

	private void saveIntoSession(String cityId) {
		System.out.println("==> cityId:" + cityId);
		if (cityId != null && !cityId.equals((String) getSession().getAttribute(HIConstants.CITYID))) {
			getSession().setAttribute(HIConstants.CITYID, cityId);
			String storeId = storeService.getDefaultStore(cityId).getStoreId();
			System.out.println("<== storeId:" + storeId);
			getSession().setAttribute(HIConstants.STOREID, storeId);
			String areaStoreId = storeService.getAreaStore(storeId).getStoreId();
			System.out.println("<== areaStoreId:" + areaStoreId);
			getSession().setAttribute(HIConstants.AREASTOREID, areaStoreId);
		}
	}

	@GET
	@Path("/switchcity")
	@Produces("application/json")
	public Response switchCity(@FormParam("cityId") String cityId) {
		City city = cityService.getCity(cityId);
		if (city == null) {
			return this.getFailedJsonResponse("not valid city");
		} else {
			getSession().setAttribute("cityId", cityId);
			return this.getSuccessJsonResponse(city);
		}
	}

	public boolean showNotice(String userId, boolean checkHisOrder) {
		Boolean isNoticeReaded = (Boolean) getSession().getAttribute(
				HIConstants.IS_NOTICE_READED);
		if (isNoticeReaded == null || !isNoticeReaded) {
			boolean hasOrdered = checkHisOrder
					&& orderService.getHistoryOrders(userId, 1).isEmpty();
			if (hasOrdered) {
				getSession().setAttribute(HIConstants.IS_NOTICE_READED, true);
				return false;
			}
		} else {
			return false;
		}
		getSession().setAttribute(HIConstants.IS_NOTICE_READED, true);
		return true;

	}

	@GET
	@Path("/user/adddish")
	@Produces("application/json")
	public Response addDish(@FormParam("dishId") String dishId,
			@FormParam("fromNotice") String fromNotice,
			@FormParam("noticeTest") String noticeTest) {
		String userId = (String) getSession()
				.getAttribute(HIConstants.LOGIN_ID);
		if (userId == null) {
			return this.getFailedJsonResponse("please login first");
		}

		boolean showNotice = false;
		if (fromNotice == null || !fromNotice.equals("true")) {
			showNotice = showNotice(userId,
					(noticeTest == null || noticeTest.equals(0)) ? true : false);
		}

		if (showNotice) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("showNotice", "true");
			result.put("directURL", "/user/adddish?dishId=" + dishId
					+ "&fromNotice=true");
			return this.getSuccessJsonResponse(result);
		}

		Order dummy = (Order) getSession()
				.getAttribute(HIConstants.DUMMY_ORDER);
		if (dummy == null) {
			dummy = new Order();
		}

		OrderDish newDish = null;
		List<OrderDish> dishes = dummy.getDishes();
		for (OrderDish d : dishes) {
			if (d.getDishId().equals(dishId)) {
				newDish = d;
				break;
			}
		}

		if (newDish != null) {
			newDish.setDishNumber(newDish.getDishNumber().add(BigDecimal.ONE));
		} else {
			newDish = new OrderDish();
			newDish.setDishId(dishId);
			newDish.setDishNumber(BigDecimal.ONE);
			dummy.addDish(newDish);
		}

		getSession().setAttribute(HIConstants.DUMMY_ORDER, dummy);
		return this.getSuccessJsonResponse(dummy);

	}

	@GET
	@Path("/user/addpack")
	@Produces("application/json")
	public Response addPack(@FormParam("packId") String packId,
			@FormParam("fromNotice") String fromNotice,
			@FormParam("noticeTest") String noticeTest) {
		String userId = (String) getSession()
				.getAttribute(HIConstants.LOGIN_ID);
		if (userId == null) {
			return this.getFailedJsonResponse("please login first");
		}

		boolean showNotice = false;
		if (fromNotice == null || !fromNotice.equals("true")) {
			showNotice = showNotice(userId,
					(noticeTest == null || noticeTest.equals(0)) ? true : false);
		}

		if (showNotice) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("showNotice", "true");
			result.put("directURL", "/user/addpack?packId=" + packId
					+ "&fromNotice=true");
			return this.getSuccessJsonResponse(result);
		}

		Order dummy = (Order) getSession()
				.getAttribute(HIConstants.DUMMY_ORDER);
		if (dummy == null) {
			dummy = new Order();
		}

		OrderPack newPack = null;
		List<OrderPack> dishes = dummy.getPacks();
		for (OrderPack d : dishes) {
			if (d.getPackId().equals(packId)) {
				newPack = d;
				break;
			}
		}

		if (newPack != null) {
			newPack.setPackCount(String.valueOf((Integer.valueOf(newPack
					.getPackCount()) + 1)));
		} else {
			newPack = new OrderPack();
			newPack.setPackId(packId);
			newPack.setPackCount("1");
			dummy.addPack(newPack);
		}

		getSession().setAttribute(HIConstants.DUMMY_ORDER, dummy);
		return this.getSuccessJsonResponse(dummy);

	}

	@GET
	@Path("/user/changenumofdish")
	@Produces("application/json")
	public Response updateDishNum(@FormParam("dishId") String dishId,
			@FormParam("num") String num) {
		Order dummy = (Order) getSession()
				.getAttribute(HIConstants.DUMMY_ORDER);
		if (dummy == null) {
			return this.getFailedJsonResponse("no temp order");
		}

		OrderDish newDish = null;
		List<OrderDish> dishes = dummy.getDishes();
		for (OrderDish d : dishes) {
			if (d.getDishId().equals(dishId)) {
				newDish = d;
				break;
			}
		}

		if (newDish != null) {
			try {
				int dishNum = Integer.valueOf(num);
				if (dishNum < 0) {
					dishNum = 0;
				}
				if (dishNum == 0) {
					dishes.remove(newDish);
				} else {
					newDish.setDishNumber(new BigDecimal(num));
				}
			} catch (Exception e) {
				return this.getFailedJsonResponse("dish num is not valid");
			}
		} else {
			return this.getFailedJsonResponse("this dish is not in temp order");
		}

		getSession().setAttribute(HIConstants.DUMMY_ORDER, dummy);
		return this.getSuccessJsonResponse(dummy);

	}

	@GET
	@Path("/user/changenumofpack")
	@Produces("application/json")
	public Response updatePackNum(@FormParam("packId") String packId,
			@FormParam("num") String num) {
		Order dummy = (Order) getSession()
				.getAttribute(HIConstants.DUMMY_ORDER);
		if (dummy == null) {
			return this.getFailedJsonResponse("no temp order");
		}

		OrderPack newPack = null;
		List<OrderPack> packs = dummy.getPacks();
		for (OrderPack d : packs) {
			if (d.getPackId().equals(packId)) {
				newPack = d;
				break;
			}
		}

		if (newPack != null) {
			try {
				int packNum = Integer.valueOf(num);
				if (packNum < 0) {
					packNum = 0;
				}
				if (packNum == 0) {
					packs.remove(newPack);
				} else {
					newPack.setPackCount(String.valueOf(packNum));
				}
			} catch (Exception e) {
				return this.getFailedJsonResponse("pack num is not valid");
			}
		} else {
			return this.getFailedJsonResponse("this pack is not in temp order");
		}

		getSession().setAttribute(HIConstants.DUMMY_ORDER, dummy);
		return this.getSuccessJsonResponse(dummy);

	}
	
	@GET
	@Path("/user/getpaychannel")
	@Produces("application/json")
	public Response getPayChannel(@FormParam("orderId") String orderId, @FormParam("test") String test) {
		if(orderId == null){
			orderId = (String) getSession()
					.getAttribute(HIConstants.ORDER_ID);
		}
		String payType = this.orderService.getOrderInfo(orderId).getPayChannel();
		if(!"1".equals(test)&&!"0".equals(payType)){
			return this.getFailedJsonResponse("\u8D27\u5230\u4ED8\u6B3E\u8BA2\u5355 not need to pay from internet");
		}
		String json;
		try {
			json = HttpUtil.sendGet(SystemSetting.getSetting("queryPayChannelsUrl"));
		} catch (Exception e) {
			return this.getFailedJsonResponse("can not get pay channels");
		}
		return this.getSuccessJsonResponse(json);

	}
	
	@GET
	@Path("/user/getpayurl")
	@Produces("application/json")
	public Response getpayurl(@FormParam("orderId") String orderId, @FormParam("channelNo") String channelNo, @FormParam("testPrice") String testPrice) {
		if(orderId == null){
			orderId = (String) getSession()
					.getAttribute(HIConstants.ORDER_ID);
		}
		if(channelNo == null){
			return this.getFailedJsonResponse("channelNo can not be null");
		}
		Order order = this.orderService.getOrderInfo(orderId);
		double price;
		if(testPrice!=null){
			price = 0.01;
		}else{
			price = order.getExpenses().getTotalPrice().doubleValue();
		}
		
		String payInitUrl = SystemSetting.getSetting("webPayInitUrl");
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderNo", orderId);
		params.put("channelNo", channelNo);
		params.put("orderAmount", ""+price);
		String json;
		try {
			json = HttpUtil.sendPost(payInitUrl, params);
		} catch (Exception e) {
			return this.getFailedJsonResponse("can not get pay url");
		}

		return this.getSuccessResponse(json);
	}

}
