package com.hi.service;

import java.util.List;

import com.hi.model.HistoryUserInfo;

public interface HistoryUserInfoService {

	/**
	 * 保存用户意见的历史信息
	 * @param info
	 * @return
	 */
	public boolean save(HistoryUserInfo info);
	
	/**
	 * 查询用户的历史信息
	 * @param userId
	 * @return
	 */
	public List<HistoryUserInfo> list(String userId);
	
	/**
	 * 删除用户的历史信息
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 保存用户历史信息
	 * @param name		用户名称
	 * @param phone		手机号
	 * @param address	地址
	 * @param userId
	 */
	public void save(String name, String phone, String address,String userId);

	public List<HistoryUserInfo> list(String userId, int type);

	/**
	 * 保存发票
	 * @param invoice	发飘信息
	 * @param userId	用户ID
	 */
	public void saveInvoice(String invoice, String userId);
}
