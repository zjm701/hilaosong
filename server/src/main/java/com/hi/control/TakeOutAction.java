package com.hi.control;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.common.HIConstants;
import com.hi.model.Order;
import com.hi.model.OrderDish;
import com.hi.model.OrderPack;
import com.hi.service.OrderService;

@Path("/")
public class TakeOutAction extends BaseAction {

	@Autowired
	private OrderService orderService;

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
}
