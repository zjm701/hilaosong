package com.hi.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hi.dao.HistoryUserInfoDao;
import com.hi.model.HistoryUserInfo;
import com.hi.service.HistoryUserInfoService;

@Service("historyUserInfoService")
@Transactional
public class HistoryUserInfoServiceImpl implements HistoryUserInfoService {

	@Autowired
	private HistoryUserInfoDao historyUserInfoDao;
	
	@Override
	public boolean save(HistoryUserInfo info) {
		return historyUserInfoDao.save(info);
	}

	@Override
	public List<HistoryUserInfo> list(String userId) {
		return historyUserInfoDao.list(userId);
	}

	@Override
	public boolean delete(String id) {
		return historyUserInfoDao.delete(id);
	}

	@Override
	/**
	 * 
	 * 1:用户名 2:手机号 3:地址
	 */
	public void save(String name, String phone, String address,String userId) {
		save(name, 1, userId);
		save(phone, 2, userId);
		save(address,3, userId);
	}

	private void save(String info,Integer type,String userId){
		if(StringUtils.isNotEmpty(info)){
			HistoryUserInfo hinfo = historyUserInfoDao.get(userId,info,type);
			if(hinfo == null){
				hinfo = new HistoryUserInfo();
				hinfo.setInfo(info);
				hinfo.setTypes(new BigDecimal(type));
				hinfo.setUserId(userId);
				historyUserInfoDao.save(hinfo);
			}
		}
	}

	@Override
	public List<HistoryUserInfo> list(String userId, int type) {
		return historyUserInfoDao.list(userId,new BigDecimal(type));
	}

	@Override
	public void saveInvoice(String invoice, String userId) {

		save(invoice, 4, userId);
	}
}
