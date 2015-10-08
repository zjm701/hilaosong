package com.hi.service.impl;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hi.dao.UnionPayOrderDao;
import com.hi.model.UnionPayOrder;
import com.hi.service.OrderService;
import com.hi.service.UnionPayOrderService;

/**
 * 银联支付回调记录订单 Service Impl
 * @author 蒋先彪
 *
 */
@Service
@Transactional
public class UnionPayOrderServiceImpl implements UnionPayOrderService {

	/**
	 * 银联支付回调记录订单 DAO
	 */
	@Autowired
	private UnionPayOrderDao unionPayOrderDao;
	
	/**
	 * 订单 Service
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 添加订单银联支付记录
	 * @param unionPayOrder
	 * @return
	 */
	@Override
	public boolean createUnionPayOrder(UnionPayOrder unionPayOrder) {
		if(null == unionPayOrder){
			return false;
		}
		return unionPayOrderDao.createUnionPayOrder(unionPayOrder);
	}
	
	
	/**
	 * 银联支付回调操作
	 * @param params
	 * @return
	 */
	@Override
	public boolean unionPayBackHandle(Map<String, String> params){
		try {
			if(null == params){
				return false;
			}
			String orderId = params.get("orderId").toString();
			String respCode = params.get("respCode").toString();
			int payStatus = 0;
			if("00".equals(respCode)){
				payStatus = 1;
			}else{
				payStatus = 2;
			}
			//处理订单支付状态(根据银联支付返回的支付结果)
			orderService.unionBackUpdateOrderPayStatus(orderId,payStatus);
			//记录银联支付返回信息
			UnionPayOrder payOrder = new UnionPayOrder();
			payOrder.setMerId(params.get("merId").toString());
			payOrder.setOrderId(orderId);
			payOrder.setQueryId(params.get("queryId").toString());
			payOrder.setPayAmt(params.get("txnAmt")==null ? 0 : Double.parseDouble(params.get("txnAmt").toString()));
			payOrder.setAccountNo(params.get("accNo")==null ? "缺省" : params.get("accNo").toString());
			payOrder.setRespCode(params.get("respCode").toString());
			payOrder.setRespMsg(params.get("respMsg").toString());
			this.createUnionPayOrder(payOrder);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 处理银联支付小概率事件
	 * @param params
	 * @param isUpdatePayStatus
	 * @return
	 */
	@Override
	public boolean unionPaySmallHandle(Map<String, String> params,boolean isUpdatePayStatus){
		try {
			if(null == params){
				return false;
			}
			String orderId = params.get("orderId").toString();
			//处理订单支付状态(根据银联支付返回的支付结果)
			if(isUpdatePayStatus){//将订单改为已支付
				orderService.unionBackUpdateOrderPayStatus(orderId,1);
			}
			//记录银联支付返回信息
			String respCode = params.get("respCode").toString();
			UnionPayOrder payOrder = new UnionPayOrder();
			payOrder.setMerId(params.get("merId").toString());
			payOrder.setOrderId(orderId);
			payOrder.setQueryId(params.get("queryId")==null ? "缺省" : params.get("queryId").toString());
			payOrder.setPayAmt(params.get("txnAmt")==null ? 0 : Double.parseDouble(params.get("txnAmt").toString()));
			payOrder.setAccountNo(params.get("accNo")==null ? "缺省" : params.get("accNo").toString());
			payOrder.setRespCode(respCode);
			String respMsg = (null==params.get("origRespCode") || !"00".equals(params.get("origRespCode").toString()))?"处理小概率:该订单未完成支付":"处理小概率:该订单完成支付，但没回调成功!";
			payOrder.setRespMsg(respMsg);
			this.createUnionPayOrder(payOrder);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
