package com.hi.control;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hi.common.OrderType;
import com.hi.model.Store;
import com.hi.service.StoreService;
import com.hi.tools.BaiduTools;
import com.hi.tools.CityTools;
import com.hi.tools.ResponseTools;
import com.hi.tools.StringTools;

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
	@Path("/getstores0")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStores(@FormParam("cityId") String cityId, @FormParam("address") String address) {
		try {
			List<Store> stores = null;
			if(StringTools.isNotEmpty(address)){
				stores = storeService.getStores(cityId, OrderType.SEND_OUT.getKey(),
						BaiduTools.getCusPointByAddress(URLEncoder.encode(address, "ISO-8859-1")));
				Collections.sort(stores, new Comparator<Store>() {
					public int compare(Store arg0, Store arg1) {
						return arg0.getDistance().compareTo(arg1.getDistance());
					}
				});
			}else{
				stores = storeService.getStores(cityId, OrderType.SEND_OUT.getKey());
			}
			return getSuccessJsonResponse(stores);
		} catch (UnsupportedEncodingException e) {
			return ResponseTools.getResponse("\u7f16\u7801\u5f02\u5e38"); // 编码异常
		}
	}

	@GET
	@Path("/getstores2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStores(@FormParam("cityId") String cityId) {
		List<Store> stores = storeService.getStores(cityId, OrderType.TAKE_AWAY.getKey());
		return getSuccessJsonResponse(stores);
	}

	/**
	 * /wap3/countDeliveryFee
	 * 
	 * @param storeId
	 * @param address
	 * @return
	 */
	@GET
	@Path("/calcdeliveryfee")
	@Produces(MediaType.APPLICATION_JSON)
	public String calculateDeliveryFee(@FormParam("storeId") String storeId, @FormParam("address") String address) {
		try {
			Store s = storeService.getStore(storeId, BaiduTools.getCusPointByAddress(URLEncoder.encode(address, "ISO-8859-1")));
			if (s != null) {
				return "{\"deliveryFee\":\"" + calculateDeliveryFee0(s) + "\"}";
			} else {
				return getMessageJsonResult("\u672a\u53d6\u5230\u6570\u636e"); // 未取到数据
			}
		} catch (UnsupportedEncodingException e) {
			return getMessageJsonResult("\u7f16\u7801\u5f02\u5e38"); // 编码异常
		}
	}

	private int calculateDeliveryFee0(Store s) {
		if (s != null) {
			String cityId = CityTools.isDirectMunicipalities(s.getProvinceId()) ? s.getProvinceId() : s.getCityId();
			double unitPrice = storeService.getDeliveryUnitPrice(cityId);
			Double dis = s.getDistance().doubleValue() * 1d / 1000;
			if (dis.compareTo(1d) < 0) {
				dis = 1d;
			}
			Double totalPrice = unitPrice * dis;
			// 总价四舍五入到整数位
			return Integer.parseInt(new java.text.DecimalFormat("0").format(totalPrice));
		} else {
			return 0;
		}
	}

	@GET
	@Path("/getcuspoint")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCusPointByAddress(@FormParam("address") String address) {
		try {
			return BaiduTools.getCusPointByAddress(URLEncoder.encode(address, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			return getMessageJsonResult("\u7f16\u7801\u5f02\u5e38"); // 编码异常
		}
	}
}
