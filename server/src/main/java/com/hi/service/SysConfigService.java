package com.hi.service;

import java.util.List;

import com.hi.model.SysConfig;

public interface SysConfigService {
	/**
	 * 查询数据字典
	 * @param paramType
	 * @param paramCode
	 * @param paramSrc
	 * @return
	 */
	List<SysConfig> getSysConfigs(String paramType, String paramCode, String paramSrc);
}
