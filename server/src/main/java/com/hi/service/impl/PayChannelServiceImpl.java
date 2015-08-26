package com.hi.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.PayChannelDao;
import com.hi.model.PayChannel;
import com.hi.service.PayChannelService;

@Service("payChannelService")
@Transactional
public class PayChannelServiceImpl implements PayChannelService {

	private static final Log log = LogFactory.getLog(PayChannelServiceImpl.class);

	@Autowired
	private PayChannelDao dao;

	@Override
	public List<PayChannel> getPayChannels() {
		log.debug("==> payChannelService.getPayChannels");
		return dao.getPayChannels();
	}
}
