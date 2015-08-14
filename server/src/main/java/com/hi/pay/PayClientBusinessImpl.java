package com.hi.pay;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

//import com.funguide.caterpaygateway.constants.PayGatewayConstants;
//import com.funguide.caterpaygateway.delegator.CaterDelegator;
//import com.funguide.caterpaygateway.delegator.ParameterDelegator;
//import com.funguide.caterpaygateway.forms.NativePayForm;
//import com.funguide.caterpaygateway.forms.RefundForm;
//import com.funguide.caterpaygateway.forms.WapPayForm;
//import com.funguide.caterpaygateway.forms.WebPayForm;
//import com.funguide.caterpaygateway.models.TCaterPayChannel;
//import com.funguide.caterpaygateway.models.TCaterThirdmerchantinfo;
//import com.funguide.caterpaygateway.models.TCaterThirdorderinfo;
//import com.funguide.caterpaygateway.services.TCaterPayChannelService;
//import com.funguide.caterpaygateway.services.TCaterThirdmerchantinfoService;
//import com.funguide.caterpaygateway.services.TCaterThirdorderinfoService;
//import com.funguide.caterpaygateway.utils.SpringBeanUtil;
//import com.funguide.caterpaygateway.utils.Tools;
//import com.funguide.general.constants.GlobalConstants;
//import com.funguide.general.helpers.CommonHelper;

/**
 * @author linjian
 * 
 *         2013-8-22
 */
@Component("payClientBusinessImpl")
public class PayClientBusinessImpl implements PayClientBusiness {

//	private static final Log log = LogFactory
//			.getLog(PayClientBusinessImpl.class);
//
//	@Resource(name = "springBeanUtil")
//	private SpringBeanUtil springBeanUtil;
//
//	@Resource(name = "tCaterPayChannelServiceImpl")
//	private TCaterPayChannelService tCaterPayChannelService;
//
//	@Resource(name = "tCaterThirdorderinfoServiceImpl")
//	private TCaterThirdorderinfoService tCaterThirdorderinfoService;
//
//	@Resource(name = "caterDelegatorImpl")
//	private CaterDelegator caterDelegator;
//
//	@Resource(name = "parameterDelegatorImpl")
//	private ParameterDelegator parameterDelegator;
//
//	@Resource(name = "tCaterThirdmerchantinfoService")
//	private TCaterThirdmerchantinfoService tCaterThirdmerchantinfoService;
//
//	/**
//	 * wap初始化订单
//	 * 
//	 */
//	public Map<String, Object> wapPayInit(WapPayForm wapPayForm,
//			HttpServletRequest request, HttpServletResponse response) {
//
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String respCode = GlobalConstants.SUCCESS_CODE;
//		String respMsg = "Success";
//
//		try {
//
//			// 调用校验订单接口
//			String payInfo = caterDelegator
//					.checkOrder(
//							parameterDelegator
//									.getProperty("com.funguide.caterpaygateway.cater.checkorder"),
//							wapPayForm.getOrderNo(), wapPayForm
//									.getOrderAmount());
//			String payStoreId = payInfo.split(":")[0];
//			String payStoreName = payInfo.split(":")[1];
//			if (CommonHelper.isEmpty(payStoreId)) {
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "获取支付订单异常，请稍候重试，再次点击支付渠道继续支付！";
//
//			} else {
//
//				// 根据支付渠道查询渠道信息
//				TCaterPayChannel tCaterPayChannel = tCaterPayChannelService
//						.findPayChannel(wapPayForm.getChannelNo());
//
//				if (CommonHelper.isEmpty(tCaterPayChannel)) {
//
//					respCode = GlobalConstants.NO_INFO_CODE;
//					respMsg = "支付渠道不存在！";
//
//				} else {
//					log.info("pay Store Info："+payInfo);
//					TCaterThirdmerchantinfo tCaterThirdmerchantinfo = tCaterThirdmerchantinfoService
//							.queryTCaterThirdmerchantinfo(
//									tCaterPayChannel.getChannelNo(),
//									PayGatewayConstants.ORDER_THIRD_PAYTYPE_WAP,
//									payStoreId);
//					if (CommonHelper.isEmpty(tCaterThirdmerchantinfo)) {
//
//						respCode = GlobalConstants.NO_INFO_CODE;
//						respMsg = "支付订单初始化失败！";
//
//					} else {
//
//						List<TCaterThirdorderinfo> tCaterThirdorderinfoList = tCaterThirdorderinfoService
//								.queryTCaterThirdorderinfoListByOrderId(wapPayForm
//										.getOrderNo());
//
//						// 已存在订单
//						if (!tCaterThirdorderinfoList.isEmpty()) {
//							for (TCaterThirdorderinfo orderInfo : tCaterThirdorderinfoList) {
//								// 已支付订单
//								if (orderInfo
//										.getPayStatus()
//										.equals(PayGatewayConstants.ORDER_PAY_SUCCESS_STATUS)) {
//									respCode = GlobalConstants.COMMON_ERROR_CODE;
//									respMsg = "您的订单已支付！";
//
//									jsonMap.put("respCode", respCode);
//									jsonMap.put("respMsg", respMsg);
//
//									return jsonMap;
//								}
//							}
//
//						}
//
//						// 第三方支付订单
//						String thirdOrderId = wapPayForm.getOrderNo() + "-"
//								+ CommonHelper.generateRandomCode(6);
//
//						// 创建支付订单 start
//						TCaterThirdorderinfo tCaterThirdorderinfo = new TCaterThirdorderinfo();
//
//						tCaterThirdorderinfo
//								.setMerchantNo(tCaterThirdmerchantinfo
//										.getMerchantNo());
//						tCaterThirdorderinfo.setThirdOrderId(thirdOrderId);
//						tCaterThirdorderinfo.setAmount(new BigDecimal(
//								wapPayForm.getOrderAmount()));
//						tCaterThirdorderinfo.setChannelName(tCaterPayChannel
//								.getChannelName());
//						tCaterThirdorderinfo.setChannelNo(wapPayForm
//								.getChannelNo());
//						Timestamp curTime = new Timestamp(
//								System.currentTimeMillis());
//						tCaterThirdorderinfo.setCreatedAt(curTime);
//						tCaterThirdorderinfo.setUpdatedAt(curTime);
//						tCaterThirdorderinfo
//								.setPayStatus(PayGatewayConstants.ORDER_CREATE_SUCCESS_STATUS);
//						tCaterThirdorderinfo
//								.setOrderId(wapPayForm.getOrderNo());
//						tCaterThirdorderinfo.setPayStoreId(payStoreId);
//						tCaterThirdorderinfo.setPayStoreName(payStoreName);
//						/////红包编码
//						log.info("customerKey:"+wapPayForm.getCustomerKey()+":getRedEnvelopeIds"+wapPayForm.getRedEnvelopeIds());
//						if(!CommonHelper.isEmpty(wapPayForm.getCustomerKey())&&!"null".equals(wapPayForm.getCustomerKey())){
//							tCaterThirdorderinfo.setCustomerKey(wapPayForm.getCustomerKey());
//						}
//						if(!CommonHelper.isEmpty(wapPayForm.getRedEnvelopeIds())&&!"null".equals(wapPayForm.getRedEnvelopeIds())){
//							tCaterThirdorderinfo.setRedEnvelopeIds(wapPayForm.getRedEnvelopeIds());
//						}
//						if(!CommonHelper.isEmpty(wapPayForm.getRedAmount())&&!"null".equals(wapPayForm.getRedAmount())){
//							tCaterThirdorderinfo.setRedAmount(wapPayForm.getRedAmount());
//						}
//						//////使用菜品劵信息
//						String dishMenus = Tools.parseStreamToString(request.getInputStream());
//						if(!CommonHelper.isEmpty(dishMenus)&&!"null".equals(dishMenus)){
//							tCaterThirdorderinfo.setDishMenus(dishMenus);
//						}												
//						tCaterThirdorderinfoService
//								.saveTCaterThirdorderinfo(tCaterThirdorderinfo);
//						// 创建支付订单 end
//
//						// 根据支付渠道编号得到支付渠道实现类
//						PayBusiness payBusiness = getPayBusinessImpl(tCaterPayChannel
//								.getInterface_());
//
//						// 初始化wap支付订单，得到跳转链接
//						String wapPayRequestUrl = payBusiness.getWapPayUrl(wapPayForm,
//								tCaterThirdmerchantinfo, thirdOrderId,
//								new BigDecimal(wapPayForm.getOrderAmount())
//										.multiply(new BigDecimal(100))
//										.intValue(), request, response,
//								wapPayForm.getClientType());
//
//						if (CommonHelper.isEmpty(wapPayRequestUrl)) {
//							respCode = GlobalConstants.COMMON_ERROR_CODE;
//							respMsg = "订单初始化失败，请您再次点击支付渠道继续支付";
//						} else {
//
//							jsonMap.put("wapPayRequestUrl", wapPayRequestUrl);
//						}
//					}
//
//				}
//
//			}
//
//		} catch (Exception ex) {
//			log.info("初始化订单接口异常：", ex);
//			respCode = GlobalConstants.COMMON_ERROR_CODE;
//			respMsg = "Error";
//		}
//
//		jsonMap.put("respCode", respCode);
//		jsonMap.put("respMsg", respMsg);
//
//		return jsonMap;
//	}
//	/**
//	 * wap初始化订单
//	 * 
//	 */
//	public Map<String, Object> nativePayInit(NativePayForm nativePayForm,
//			HttpServletRequest request, HttpServletResponse response) {
//
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String respCode = GlobalConstants.SUCCESS_CODE;
//		String respMsg = "Success";
//
//		try {
//
//			// 调用校验订单接口
//			String payInfo = caterDelegator
//					.checkOrder(
//							parameterDelegator
//									.getProperty("com.funguide.caterpaygateway.cater.checkorder"),
//									nativePayForm.getOrderNo(), nativePayForm
//									.getOrderAmount());
//			String payStoreId = payInfo.split(":")[0];
//			String payStoreName = payInfo.split(":")[1];
//			if (CommonHelper.isEmpty(payStoreId)) {
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "获取支付订单异常，请稍候重试，再次点击支付渠道继续支付！";
//
//			} else {
//
//				// 根据支付渠道查询渠道信息
//				TCaterPayChannel tCaterPayChannel = tCaterPayChannelService
//						.findPayChannel(nativePayForm.getChannelNo());
//
//				if (CommonHelper.isEmpty(tCaterPayChannel)) {
//
//					respCode = GlobalConstants.NO_INFO_CODE;
//					respMsg = "支付渠道不存在！";
//
//				} else {
//					log.info("pay Store Info："+payInfo);
//					TCaterThirdmerchantinfo tCaterThirdmerchantinfo = tCaterThirdmerchantinfoService
//							.queryTCaterThirdmerchantinfo(
//									tCaterPayChannel.getChannelNo(),
//									PayGatewayConstants.ORDER_THIRD_PAYTYPE_WAP,
//									payStoreId);
//					if (CommonHelper.isEmpty(tCaterThirdmerchantinfo)) {
//
//						respCode = GlobalConstants.NO_INFO_CODE;
//						respMsg = "支付订单初始化失败！";
//
//					} else {
//
//						List<TCaterThirdorderinfo> tCaterThirdorderinfoList = tCaterThirdorderinfoService
//								.queryTCaterThirdorderinfoListByOrderId(nativePayForm
//										.getOrderNo());
//
//						// 已存在订单
//						if (!tCaterThirdorderinfoList.isEmpty()) {
//							for (TCaterThirdorderinfo orderInfo : tCaterThirdorderinfoList) {
//								// 已支付订单
//								if (orderInfo
//										.getPayStatus()
//										.equals(PayGatewayConstants.ORDER_PAY_SUCCESS_STATUS)) {
//									respCode = GlobalConstants.COMMON_ERROR_CODE;
//									respMsg = "您的订单已支付！";
//
//									jsonMap.put("respCode", respCode);
//									jsonMap.put("respMsg", respMsg);
//
//									return jsonMap;
//								}
//							}
//
//						}
//
//						// 第三方支付订单
//						String thirdOrderId = nativePayForm.getOrderNo() + "-"
//								+ CommonHelper.generateRandomCode(6);
//
//						// 创建支付订单 start
//						TCaterThirdorderinfo tCaterThirdorderinfo = new TCaterThirdorderinfo();
//
//						tCaterThirdorderinfo
//								.setMerchantNo(tCaterThirdmerchantinfo
//										.getMerchantNo());
//						tCaterThirdorderinfo.setThirdOrderId(thirdOrderId);
//						tCaterThirdorderinfo.setAmount(new BigDecimal(
//								nativePayForm.getOrderAmount()));
//						tCaterThirdorderinfo.setChannelName(tCaterPayChannel
//								.getChannelName());
//						tCaterThirdorderinfo.setChannelNo(nativePayForm
//								.getChannelNo());
//						Timestamp curTime = new Timestamp(
//								System.currentTimeMillis());
//						tCaterThirdorderinfo.setCreatedAt(curTime);
//						tCaterThirdorderinfo.setUpdatedAt(curTime);
//						tCaterThirdorderinfo
//								.setPayStatus(PayGatewayConstants.ORDER_CREATE_SUCCESS_STATUS);
//						tCaterThirdorderinfo
//								.setOrderId(nativePayForm.getOrderNo());
//						tCaterThirdorderinfo.setPayStoreId(payStoreId);
//						tCaterThirdorderinfo.setPayStoreName(payStoreName);
//						tCaterThirdorderinfoService
//								.saveTCaterThirdorderinfo(tCaterThirdorderinfo);
//						// 创建支付订单 end
//
//						// 根据支付渠道编号得到支付渠道实现类
//						PayBusiness payBusiness = getPayBusinessImpl(tCaterPayChannel
//								.getInterface_());
//
//						// 初始化wap支付订单，得到跳转链接
//						Map<String,String> wapPayRequestMap = payBusiness.getNativePayUrl(
//								tCaterThirdmerchantinfo, thirdOrderId,
//								new BigDecimal(nativePayForm.getOrderAmount())
//										.multiply(new BigDecimal(100))
//										.intValue(), request, response);
//
//						if (CommonHelper.isEmpty(wapPayRequestMap)) {
//							respCode = GlobalConstants.COMMON_ERROR_CODE;
//							respMsg = "订单初始化失败，请您再次点击支付渠道继续支付";
//						} else {
//							jsonMap.putAll(wapPayRequestMap);
//						}
//					}
//
//				}
//
//			}
//
//		} catch (Exception ex) {
//			log.info("初始化订单接口异常：", ex);
//			respCode = GlobalConstants.COMMON_ERROR_CODE;
//			respMsg = "Error";
//		}
//
//		jsonMap.put("respCode", respCode);
//		jsonMap.put("respMsg", respMsg);
//
//		return jsonMap;
//	}
//
//	/**
//	 * 网站版支付地址
//	 * 
//	 * @param webPayForm
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public Map<String, Object> webPayInit(WebPayForm webPayForm,
//			HttpServletRequest request, HttpServletResponse response) {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String respCode = GlobalConstants.SUCCESS_CODE;
//		String respMsg = "Success";
//
//		try {
//
//			// 调用校验订单接口,获取支付门店号
//			String payInfo = caterDelegator
//					.checkOrder(
//							parameterDelegator
//									.getProperty("com.funguide.caterpaygateway.cater.checkorder"),
//							webPayForm.getOrderNo(), webPayForm
//									.getOrderAmount());
//			String payStoreId = payInfo.split(":")[0];
//			String payStoreName = payInfo.split(":")[1];
//			if (null == payStoreId || "".equals(payStoreId)) {
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "获取支付订单异常，请稍候重试，再次点击支付渠道继续支付！";
//
//			} else {
//				log.info("pay Store Info："+payInfo);
//				// 根据支付渠道查询渠道信息
//				TCaterPayChannel tCaterPayChannel = tCaterPayChannelService
//						.findPayChannel(webPayForm.getChannelNo());
//				if (CommonHelper.isEmpty(tCaterPayChannel)) {
//
//					respCode = GlobalConstants.NO_INFO_CODE;
//					respMsg = "支付渠道不存在！";
//
//				} else {
//
//					TCaterThirdmerchantinfo tCaterThirdmerchantinfo = tCaterThirdmerchantinfoService
//							.queryTCaterThirdmerchantinfo(
//									tCaterPayChannel.getChannelNo(),
//									PayGatewayConstants.ORDER_THIRD_PAYTYPE_WEB,
//									payStoreId);
//					if (CommonHelper.isEmpty(tCaterThirdmerchantinfo)) {
//						respCode = GlobalConstants.NO_INFO_CODE;
//						respMsg = "支付订单初始化失败！";
//
//					} else {
//
//						List<TCaterThirdorderinfo> tCaterThirdorderinfoList = tCaterThirdorderinfoService
//								.queryTCaterThirdorderinfoListByOrderId(webPayForm
//										.getOrderNo());
//
//						// 已存在订单
//						if (!tCaterThirdorderinfoList.isEmpty()) {
//							for (TCaterThirdorderinfo orderInfo : tCaterThirdorderinfoList) {
//								// 已支付订单
//								if (orderInfo
//										.getPayStatus()
//										.equals(PayGatewayConstants.ORDER_PAY_SUCCESS_STATUS)) {
//									respCode = GlobalConstants.COMMON_ERROR_CODE;
//									respMsg = "您的订单已支付！";
//
//									jsonMap.put("respCode", respCode);
//									jsonMap.put("respMsg", respMsg);
//
//									return jsonMap;
//								}
//							}
//
//						}
//
//						// 第三方支付订单
//						String thirdOrderId = webPayForm.getOrderNo() + "-"
//								+ CommonHelper.generateRandomCode(6);
//
//						// 创建支付订单 start
//						TCaterThirdorderinfo tCaterThirdorderinfo = new TCaterThirdorderinfo();
//
//						tCaterThirdorderinfo
//								.setMerchantNo(tCaterThirdmerchantinfo
//										.getMerchantNo());
//						tCaterThirdorderinfo.setThirdOrderId(thirdOrderId);
//						tCaterThirdorderinfo.setAmount(new BigDecimal(
//								webPayForm.getOrderAmount()));
//						tCaterThirdorderinfo.setChannelName(tCaterPayChannel
//								.getChannelName());
//						tCaterThirdorderinfo.setChannelNo(webPayForm
//								.getChannelNo());
//						Timestamp curTime = new Timestamp(
//								System.currentTimeMillis());
//						tCaterThirdorderinfo.setCreatedAt(curTime);
//						tCaterThirdorderinfo.setUpdatedAt(curTime);
//						tCaterThirdorderinfo
//								.setPayStatus(PayGatewayConstants.ORDER_CREATE_SUCCESS_STATUS);
//						tCaterThirdorderinfo
//								.setOrderId(webPayForm.getOrderNo());
//						tCaterThirdorderinfo.setPayStoreId(payStoreId);
//						tCaterThirdorderinfo.setPayStoreName(payStoreName);
//
//						tCaterThirdorderinfoService
//								.saveTCaterThirdorderinfo(tCaterThirdorderinfo);
//						// 创建支付订单 end
//
//						// 根据支付渠道编号得到支付渠道实现类
//						PayBusiness payBusiness = getPayBusinessImpl(tCaterPayChannel
//								.getInterface_());
//
//						// 初始化wep支付订单，得到跳转链接
//						String webPayRequestUrl = payBusiness.getWebPayUrl(
//								tCaterThirdmerchantinfo, thirdOrderId,
//								new BigDecimal(webPayForm.getOrderAmount())
//										.multiply(new BigDecimal(100))
//										.intValue(), request, response);
//
//						if (CommonHelper.isEmpty(webPayRequestUrl)) {
//							respCode = GlobalConstants.COMMON_ERROR_CODE;
//							respMsg = "订单初始化失败，请您再次点击支付渠道继续支付";
//						} else {
//
//							jsonMap.put("webPayRequestUrl", webPayRequestUrl);
//						}
//
//					}
//				}
//
//			}
//
//		} catch (Exception ex) {
//			log.info("初始化订单接口异常：", ex);
//			respCode = GlobalConstants.COMMON_ERROR_CODE;
//			respMsg = "Error";
//		}
//
//		jsonMap.put("respCode", respCode);
//		jsonMap.put("respMsg", respMsg);
//
//		return jsonMap;
//	}
//
//	/**
//	 * 支付渠道实现类
//	 * 
//	 * @param className
//	 * @return
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 */
//	private PayBusiness getPayBusinessImpl(String className)
//			throws InstantiationException, IllegalAccessException,
//			ClassNotFoundException {
//
//		// 这里存放的是Bean名称
//		PayBusiness payBusiness = (PayBusiness) springBeanUtil
//				.getBean(className);
//
//		return payBusiness;
//	}
//
//	@Override
//	public BigDecimal findPrePayPriceByThridOrder(String thirdno) {
//		// TODO Auto-generated method stub
//		TCaterThirdorderinfo caterThirdorderinfo = tCaterThirdorderinfoService.queryTCaterThirdorderinfoByThirdOrderId(thirdno);
//		return caterThirdorderinfo.getAmount();
//	}
//	
//	@Override
//	public String findTenkey(String thirdno) {
//		// TODO Auto-generated method stub
//		TCaterThirdorderinfo caterThirdorderinfo = tCaterThirdorderinfoService.queryTCaterThirdorderinfoByThirdOrderId(thirdno);
//		String tenkey = "";
//		if(caterThirdorderinfo!=null){
//			String merchantNo = caterThirdorderinfo.getMerchantNo();
//			TCaterThirdmerchantinfo caterThirdmerchantinfo = tCaterThirdmerchantinfoService.queryTCaterThirdmerchantinfoByMerchanNo(merchantNo);
//			if(caterThirdmerchantinfo!=null){
//				tenkey =  caterThirdmerchantinfo.getMerchantKey();
//			}
//		}
//		if(null == tenkey){
//			return "";
//		}else{
//			return tenkey;
//		}
//	}
//	
//	@Override
//	public Map<String, Object> submitRefund(RefundForm refundForm) {
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String respCode = GlobalConstants.SUCCESS_CODE;
//		String respMsg = "Success";
//		
//		try {
//			// 查询订单
//			List<TCaterThirdorderinfo> thirdOrderList = tCaterThirdorderinfoService
//					.queryPaySuccessThirdorderinfoListByOrderId(refundForm.getOrderNo());
//			if(thirdOrderList ==null || thirdOrderList.size()==0){
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "订单号不存在";
//			}else {
//				TCaterThirdorderinfo tCaterThirdorderinfo = thirdOrderList.get(0);
//				if (!(PayGatewayConstants.ORDER_PAY_SUCCESS_STATUS
//							.equals(tCaterThirdorderinfo.getPayStatus()) || PayGatewayConstants.ORDER_REFUND_UNKNOWN_STATUS
//							.equals(tCaterThirdorderinfo.getPayStatus()))) { // 不是支付成功,未知状态都不能退款
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "订单不是支付成功,未知状态都不能退款";
//			} else {
//				// 根据支付渠道查询渠道信息
//				TCaterPayChannel tCaterPayChannel = tCaterPayChannelService
//						.findPayChannel(tCaterThirdorderinfo.getChannelNo());
//				// 根据支付渠道编号得到支付渠道实现类
//				PayBusiness payBusiness = getPayBusinessImpl(tCaterPayChannel
//						.getInterface_());
//
//				String result = payBusiness.getRefund(tCaterPayChannel,
//						tCaterThirdorderinfo);
//
//				if (CommonHelper.isEmpty(result)) {
//					respCode = GlobalConstants.COMMON_ERROR_CODE;
//					respMsg = "Error";
//				}
//
//				jsonMap.put("result", result);
//			}
//			}
//
//		} catch (Exception ex) {
//			respCode = GlobalConstants.COMMON_ERROR_CODE;
//			respMsg = "Error";
//			log.debug("refund exception", ex);
//		}
//
//		jsonMap.put("respCode", respCode);
//		jsonMap.put("respMsg", respMsg);
//
//		return jsonMap;
//	}
//	/**
//	 * wap初始化订单
//	 * 
//	 */
//	public Map<String, Object> unionwapPayInit(WapPayForm wapPayForm,
//			HttpServletRequest request, HttpServletResponse response) {
//
//		Map<String, Object> jsonMap = new HashMap<String, Object>();
//		String respCode = GlobalConstants.SUCCESS_CODE;
//		String respMsg = "Success";
//
//		try {
//
//			// 调用校验订单接口
//			String payInfo = caterDelegator
//					.checkOrder(
//							parameterDelegator
//									.getProperty("com.funguide.caterpaygateway.cater.checkorder"),
//							wapPayForm.getOrderNo(), wapPayForm
//									.getOrderAmount());
//			String payStoreId = payInfo.split(":")[0];
//			String payStoreName = payInfo.split(":")[1];
//			if (CommonHelper.isEmpty(payStoreId)) {
//				respCode = GlobalConstants.COMMON_ERROR_CODE;
//				respMsg = "获取支付订单异常，请稍候重试，再次点击支付渠道继续支付！";
//
//			} else {
//
//				// 根据支付渠道查询渠道信息
//				TCaterPayChannel tCaterPayChannel = tCaterPayChannelService
//						.findPayChannel(wapPayForm.getChannelNo());
//
//				if (CommonHelper.isEmpty(tCaterPayChannel)) {
//
//					respCode = GlobalConstants.NO_INFO_CODE;
//					respMsg = "支付渠道不存在！";
//
//				} else {
//					log.info("pay Store Info："+payInfo);
//					TCaterThirdmerchantinfo tCaterThirdmerchantinfo = tCaterThirdmerchantinfoService
//							.queryTCaterThirdmerchantinfo(
//									tCaterPayChannel.getChannelNo(),
//									PayGatewayConstants.ORDER_THIRD_PAYTYPE_WAP,
//									payStoreId);
//					if (CommonHelper.isEmpty(tCaterThirdmerchantinfo)) {
//
//						respCode = GlobalConstants.NO_INFO_CODE;
//						respMsg = "支付订单初始化失败！";
//
//					} else {
//
//						List<TCaterThirdorderinfo> tCaterThirdorderinfoList = tCaterThirdorderinfoService
//								.queryTCaterThirdorderinfoListByOrderId(wapPayForm
//										.getOrderNo());
//
//						// 已存在订单
//						if (!tCaterThirdorderinfoList.isEmpty()) {
//							for (TCaterThirdorderinfo orderInfo : tCaterThirdorderinfoList) {
//								// 已支付订单
//								if (orderInfo
//										.getPayStatus()
//										.equals(PayGatewayConstants.ORDER_PAY_SUCCESS_STATUS)) {
//									respCode = GlobalConstants.COMMON_ERROR_CODE;
//									respMsg = "您的订单已支付！";
//
//									jsonMap.put("respCode", respCode);
//									jsonMap.put("respMsg", respMsg);
//
//									return jsonMap;
//								}
//							}
//
//						}
//
//						// 第三方支付订单
//						String thirdOrderId = wapPayForm.getOrderNo()+CommonHelper.generateRandomCode(3);
//
//						// 创建支付订单 start
//						TCaterThirdorderinfo tCaterThirdorderinfo = new TCaterThirdorderinfo();
//
//						tCaterThirdorderinfo
//								.setMerchantNo(tCaterThirdmerchantinfo
//										.getMerchantNo());
//						tCaterThirdorderinfo.setThirdOrderId(thirdOrderId);
//						tCaterThirdorderinfo.setAmount(new BigDecimal(
//								wapPayForm.getOrderAmount()));
//						tCaterThirdorderinfo.setChannelName(tCaterPayChannel
//								.getChannelName());
//						tCaterThirdorderinfo.setChannelNo(wapPayForm
//								.getChannelNo());
//						Timestamp curTime = new Timestamp(
//								System.currentTimeMillis());
//						tCaterThirdorderinfo.setCreatedAt(curTime);
//						tCaterThirdorderinfo.setUpdatedAt(curTime);
//						tCaterThirdorderinfo
//								.setPayStatus(PayGatewayConstants.ORDER_CREATE_SUCCESS_STATUS);
//						tCaterThirdorderinfo
//								.setOrderId(wapPayForm.getOrderNo());
//						tCaterThirdorderinfo.setPayStoreId(payStoreId);
//						tCaterThirdorderinfo.setPayStoreName(payStoreName);
//						tCaterThirdorderinfoService
//								.saveTCaterThirdorderinfo(tCaterThirdorderinfo);
//						// 创建支付订单 end
//
//						// 根据支付渠道编号得到支付渠道实现类
//						PayBusiness payBusiness = getPayBusinessImpl(tCaterPayChannel
//								.getInterface_());
//
//						// 初始化wap支付订单，得到跳转链接
//						String wapPayRequestUrl = payBusiness.getWapPayUrl(wapPayForm,
//								tCaterThirdmerchantinfo, thirdOrderId,
//								new BigDecimal(wapPayForm.getOrderAmount())
//										.multiply(new BigDecimal(100))
//										.intValue(), request, response,
//								wapPayForm.getClientType());
//
//						if (CommonHelper.isEmpty(wapPayRequestUrl)) {
//							respCode = GlobalConstants.COMMON_ERROR_CODE;
//							respMsg = "订单初始化失败，请您再次点击支付渠道继续支付";
//						} else {
//
//							jsonMap.put("wapPayRequestUrl", wapPayRequestUrl);
//						}
//					}
//
//				}
//
//			}
//
//		} catch (Exception ex) {
//			log.info("初始化订单接口异常：", ex);
//			respCode = GlobalConstants.COMMON_ERROR_CODE;
//			respMsg = "Error";
//		}
//
//		jsonMap.put("respCode", respCode);
//		jsonMap.put("respMsg", respMsg);
//
//		return jsonMap;
//	}	
}
