package com.hi.service;

import java.util.List;

import com.hi.model.PayChannel;

public interface PayChannelService {
	/**
	 * 支付渠道列表
	 * 
	 * @return
	 */
	public List<PayChannel> getPayChannels();
}
