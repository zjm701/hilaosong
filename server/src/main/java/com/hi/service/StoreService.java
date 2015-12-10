package com.hi.service;

import java.util.Date;
import java.util.List;

import com.hi.model.Store;

public interface StoreService {
	/**
	 * 
	 * @param provinceId	省ID
	 * @param cityId		城市ID
	 * @param orderType		类型
	 * @return
	 */
	List<Store> getStores(String provinceId, String cityId, String orderType);

	/**
	 * 
	 * @param provinceId	省ID
	 * @param cityId		城市ID
	 * @param orderType		类型
	 * @param cuspoint		坐标
	 * @return
	 */
	List<Store> getStores(String provinceId, String cityId, String orderType, String cuspoint);

	/**
	 * 查询城市默认的门店
	 * @param cityId
	 * @return
	 */
	Store getDefaultStore(String cityId);
	/**
	 * 根据门ID查出所属地区
	 * @param storeId
	 * @return
	 */
	Store getAreaStore(String storeId);
	/**
	 * 根据门店ID和坐标查出的门店
	 * @param storeId
	 * @return
	 */
	Store getStore(String storeId);
	/**
	 * 根据门店ID和坐标查出最近的门店
	 * @param storeId
	 * @param cuspoint
	 * @return
	 */
	Store getStore(String storeId, String cuspoint);
	/**
	 * 查询的时间，是否关闭
	 * @param storeId
	 * @param date
	 * @return
	 */
	boolean isClose(String storeId, Date date);

	/**
	 * 门店是否停业
	 * @param storeId
	 * @return
	 */
	@Deprecated
	boolean isShutdown(String storeId);

	@Deprecated
	String getShutdownNotice(String storeId);
	
	/**
	 * 门店繁忙时间段
	 * @param date
	 * @param storeId
	 * @return
	 */
	String getDinningTimeType(String date,String storeId);
	
}
