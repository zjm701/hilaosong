package com.hi.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hi.dao.AbstractDao;
import com.hi.dao.HistoryUserInfoDao;
import com.hi.model.HistoryUserInfo;

@Repository("historyUserInfoDao")
public class HistoryUserInfoDaoImpl extends AbstractDao implements HistoryUserInfoDao {
	public boolean save(HistoryUserInfo info){
		StringBuilder sb = new StringBuilder();
		sb.append("insert into T_CATER_HISTORYUSERINFO (id, info, types, user_id) values (T_CATER_HISTORYUSERINFO_SEQ.nextval, '")
				.append(info.getInfo()).append("', '")
				.append(info.getTypes()).append("', '")
				.append(info.getUserId()).append("') ");
		return (this.executiveSql(sb.toString(), null) == 1);
	}

	@Override
	public List<HistoryUserInfo> list(String userId) {
		String sql = "select id as \"id\",types as \"types\",user_id as \"userId\",info as \"info\""
				+ " from T_CATER_HISTORYUSERINFO a "
				+ " where a.user_id = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.getBeansBySql(HistoryUserInfo.class, sql, params);
	}

	@Override
	public List<HistoryUserInfo> list(String userId, BigDecimal type) {
		String sql = "select id as \"id\",types as \"types\",user_id as \"userId\",info as \"info\""
				+ " from T_CATER_HISTORYUSERINFO a "
				+ " where a.user_id = :userId and types=:type";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("type", type);
		return this.getBeansBySql(HistoryUserInfo.class, sql, params);
	}

	@Override
	public boolean delete(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete T_CATER_HISTORYUSERINFO where id = :id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return (this.executiveSql(sb.toString(), params) == 1);
	}

	@Override
	public HistoryUserInfo get(String userId, String info,Integer type) {
		String sql = "select id as \"id\",types as \"types\",user_id as \"userId\",info as \"info\""
				+ " from T_CATER_HISTORYUSERINFO a "
				+ " where a.user_id = :userId and info = :info and types=:type";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("info", info);
		params.put("type", type);
		return this.getUniqueBeanBySql(HistoryUserInfo.class, sql, params);//.getBeansBySql(HistoryUserInfo.class, sql, params);
	}
}
