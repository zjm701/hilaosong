package com.hi.control;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.hi.common.HIConstants;
import com.hi.common.MessageCode;
import com.hi.common.provider.CouponProvider;

@Path("/")
public class CouponAction extends BaseAction {

	/**
	 * 获得优惠劵列表
	 * 
	 * @return
	 */
	@GET
	@Path("/getcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCoupons() {
		String customerKey = (String) getSession().getAttribute(HIConstants.CUSTOMER_KEY);
		if (StringUtils.isEmpty(customerKey)) {
			return getJsonString(MessageCode.ERROR_NO_LOGGEDIN_USER);
		} else {
			JSONObject r = CouponProvider.list(customerKey);
			if (r == null) {
				return getJsonString(MessageCode.ERROR_COUPON);
			} else {
				return r.toString();
			}
		}
	}

	/**
	 * 获得优惠劵详细信息
	 * 
	 * @param couponId
	 * @return
	 */
	@GET
	@Path("/getcouponinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCouponInfo(@FormParam("couponId") String couponId) {
		String customerKey = (String) getSession().getAttribute(HIConstants.CUSTOMER_KEY);
		if (StringUtils.isEmpty(customerKey)) {
			return getJsonString(MessageCode.ERROR_NO_LOGGEDIN_USER);
		} else if (StringUtils.isEmpty(couponId)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_COUPONID);
		} else {
			JSONObject r = CouponProvider.get(customerKey, couponId);
			if (r == null) {
				return getJsonString(MessageCode.ERROR_COUPON);
			} else {
				return r.toString();
			}
		}
	}

	/**
	 * 使用优惠劵
	 * 
	 * @param couponId
	 * @return
	 */
	@GET
	@Path("/usecoupon")
	@Produces(MediaType.APPLICATION_JSON)
	public String useCoupon(@FormParam("orderId") String orderId, @FormParam("couponId") String couponId) {
		String customerKey = (String) getSession().getAttribute(HIConstants.CUSTOMER_KEY);
		if (StringUtils.isEmpty(customerKey)) {
			return getJsonString(MessageCode.ERROR_NO_LOGGEDIN_USER);
		} else if (StringUtils.isEmpty(orderId)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_ORDERID);
		} else if (StringUtils.isEmpty(couponId)) {
			return getJsonString(MessageCode.VERIFICATION_EMPTY_COUPONID);
		} else {
			JSONObject r = CouponProvider.use(customerKey, orderId, couponId);
			if (r == null) {
				return getJsonString(MessageCode.ERROR_COUPON);
			} else {
				return r.toString();
			}
		}
	}
}
