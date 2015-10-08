package com.hi.service;

import java.util.Map;

import com.hi.model.UnionPayOrder;

/**
 * 银联支付回调记录订单 Service
 * @author 蒋先彪
 *
 */
public interface UnionPayOrderService {

	/**
	 * 添加订单银联支付记录
	 * @param unionPayOrder
	 * @return
	 */
	boolean createUnionPayOrder(UnionPayOrder unionPayOrder);
	
	/**
	 * 银联支付回调操作
	 * @param params
	 * @return
	 */
	boolean unionPayBackHandle(Map<String, String> params);
	
	/**
	 * 处理银联支付小概率事件
	 * @param params
	 * @param isUpdatePayStatus
	 * @return
	 */
	boolean unionPaySmallHandle(Map<String, String> params,boolean isUpdatePayStatus);
}
