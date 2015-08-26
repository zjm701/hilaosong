package com.hi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.PayChannelDao;
import com.hi.model.PayChannel;

@Repository("payChannelDao")
public class PayChannelDaoImpl extends AbstractDao implements PayChannelDao {

	@Override
	public List<PayChannel> getPayChannels() {
		String sql = "select channel_no as \"channelNo\", channel_name as \"channelName\", merchant_no as \"merchantNo\", merchant_key as \"merchantKey\" from T_CATER_PAY_CHANNEL";
		return this.getBeansBySql(PayChannel.class, sql);
	}
}