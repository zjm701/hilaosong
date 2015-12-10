package com.hi.dao;

import java.math.BigDecimal;
import java.util.List;

import com.hi.model.HistoryUserInfo;

public interface HistoryUserInfoDao {
	public boolean save(HistoryUserInfo info);
	
	public List<HistoryUserInfo> list(String userId);
	
	public boolean delete(String id);

	public HistoryUserInfo get(String userId, String info,Integer type);

	public List<HistoryUserInfo> list(String userId, BigDecimal bigDecimal);
}
