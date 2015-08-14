package com.hi.pay;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.funguide.caterpaygateway.forms.NativePayForm;
//import com.funguide.caterpaygateway.forms.RefundForm;
//import com.funguide.caterpaygateway.forms.WapPayForm;
//import com.funguide.caterpaygateway.forms.WebPayForm;

/**
 * @author linjian
 *
 * 2013-8-22
 */
public interface PayClientBusiness {
	
//	/**
//	 * wap支付初始化
//	 * @param wapPayForm
//	 * @return
//	 */
//	public Map<String, Object> wapPayInit(WapPayForm wapPayForm, HttpServletRequest request,
//			HttpServletResponse response);
//	
//	/**
//	 * 网站版支付地址
//	 * @param webPayForm
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public Map<String, Object> webPayInit(WebPayForm webPayForm, HttpServletRequest request,
//			HttpServletResponse response);
//	/**
//	 * app native支付地址
//	 * @param webPayForm
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public Map<String, Object> nativePayInit(NativePayForm nativePayForm, HttpServletRequest request,
//			HttpServletResponse response);
//	/**
//	 * 根据第三方订单号查询预付金额
//	 * @param thirdno
//	 * @return
//	 */
//	public BigDecimal findPrePayPriceByThridOrder(String thirdno);
//	
//	/**
//	 * 根据第三方订单号查询key
//	 * @param thirdno
//	 * @return
//	 */
//	public String findTenkey(String thirdno);
//	
//	/**
//	 * 申请退款
//	 * @param refundForm
//	 * @return
//	 */
//	public Map<String,Object> submitRefund(RefundForm refundForm);
//	
//	/**
//	 * 银联wap支付直接跳转支付页面
//	 * @param refundForm
//	 * @return
//	 */	
//	public Map<String, Object> unionwapPayInit(WapPayForm wapPayForm,
//			HttpServletRequest request, HttpServletResponse response);	
}
