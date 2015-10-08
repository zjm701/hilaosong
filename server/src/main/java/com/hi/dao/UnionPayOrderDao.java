package com.hi.dao;

import com.hi.model.UnionPayOrder;

/**
 * 银联支付回调记录订单DAO
 * @author 蒋先彪
 *
 */
public interface UnionPayOrderDao {

	/**
	 * 添加订单银联支付记录
	 * @param unionPayOrder
	 * @return
	 */
	boolean createUnionPayOrder(UnionPayOrder unionPayOrder);
}
