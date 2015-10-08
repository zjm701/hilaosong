package com.hi.dao.impl;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.UnionPayOrderDao;
import com.hi.model.UnionPayOrder;

/**
 * 银联支付回调记录订单DAO Impl
 * @author 蒋先彪
 *
 */
@Repository
public class UnionPayOrderDaoImpl extends AbstractDao implements UnionPayOrderDao {

	/**
	 * 添加订单银联支付记录
	 * @param unionPayOrder
	 * @return
	 */
	@Override
	public boolean createUnionPayOrder(UnionPayOrder unionPayOrder) {
		StringBuffer sb = new StringBuffer();
		sb.append("insert into T_CATER_UNION_PAY_ORDERINFO ")
		.append("(id, MERID, ORDERID, QUERYID, PAYAMT, ACCOUNTNO, RESPCODE, RESPMSG,PAYDATE) values (")
		.append("SEQ_CATER_UNION_PAY_ORDERINFO.nextval").append(", '").append(unionPayOrder.getMerId()).append("', '").append(unionPayOrder.getOrderId())
		.append("', '").append(unionPayOrder.getQueryId()).append("', ").append(unionPayOrder.getPayAmt()).append(", '")
		.append(unionPayOrder.getAccountNo()).append("', '").append(unionPayOrder.getRespCode()).append("', '")
		.append(unionPayOrder.getRespMsg()).append("',").append("sysdate)");
		return (this.executiveSql(sb.toString(), null) == 1);
	}

}
